package com.hegemony.arraymi.captcha.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.dao.IAddressDao;
import com.hegemony.arraymi.captcha.dao.ICustomerDetailsDao;
import com.hegemony.arraymi.captcha.dao.ITransactionDao;
import com.hegemony.arraymi.captcha.dao.IWalletDao;
import com.hegemony.arraymi.captcha.form.ChangePasswordForm;
import com.hegemony.arraymi.captcha.form.CustomerProfile;
import com.hegemony.arraymi.captcha.form.ForgetPasswordForm;
import com.hegemony.arraymi.captcha.form.RegistrationForm;
import com.hegemony.arraymi.captcha.model.Address;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;
import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.TransactionType;
import com.hegemony.arraymi.captcha.model.Wallet;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.ICustomerService;
import com.hegemony.arraymi.captcha.util.AppConstants;
import com.hegemony.arraymi.captcha.util.CaptchaGeneratorUtil;
import com.hegemony.arraymi.captcha.util.CountryUtil;
import com.hegemony.arraymi.captcha.util.DateUtil;

@Service
public class CustomerServiceImpl extends BaseService implements ICustomerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${default.multiply.factor}")
	private double multiplierFactor;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private IWalletDao walletDao;

	@Autowired
	private ICustomerDetailsDao customerDetailsDao;

	@Autowired
	private IAddressDao addressDao;

	@Autowired
	private ITransactionDao transactionDao;

	@Override
	public Wallet createWallet(long userId, String country) {
		double multiplyFactor = multiplierFactor;
		if (multiplyFactor > 1) {
			String str = "" + multiplyFactor;
			multiplyFactor = Double.parseDouble(str.substring((str).indexOf(".")));
		}
		Wallet userWallet = new Wallet(userId, multiplyFactor,
				CountryUtil.getAmount(AppConstants.MINIMUM_WIDRAWAL_AMT, country));
		userWallet = walletDao.save(userWallet);
		return userWallet;
	}

	@Override
	public void regsiterUser(RegistrationForm customerDetails) {
		logger.warn("Registration Request For '{}'", customerDetails);
		if (customerDao.existsByUsernameOrEmailId(customerDetails.getEmail(), customerDetails.getEmail())) {
			logger.warn("User already registered with details {}", customerDetails);
			throw new AppException(HttpStatus.BAD_REQUEST, "User already exists!!");
		}
		Customer user = new Customer(customerDetails, bcryptEncoder.encode(customerDetails.getPassword()),
				CaptchaGeneratorUtil.generateNewPassword());
		user = customerDao.save(user);
		CustomerProfile profile = new CustomerProfile(user.getId());
		profile.setMobileNo(customerDetails.getMobileNo());
		profile.setCountry(customerDetails.getCountry());
		addNewProfileEntry(profile);
		// create wallet for user
		Wallet userWallet = createWallet(user.getId(), customerDetails.getCountry());
		// add welcome bonus amount
		addWelcomeAmount(customerDetails.getCountry(), userWallet, user);
		sendVerificationEmail(user);
	}

	private void addWelcomeAmount(String country, Wallet userWallet, Customer user) {
		double amount = CountryUtil.getAmount(AppConstants.WELCOME_BONUS, country);
		Transaction transaction = new Transaction(userWallet.getUserId(), 0, amount, userWallet.getCurrentBalance(),
				TransactionType.CREDIT_WELCOME_BONUS);
		transactionDao.save(transaction);
		userWallet.addAmount(amount);
		walletDao.save(userWallet);
		emailService.sendTransactionAlert(CountryUtil.getCurrency(country), transaction, user);
	}

	private void sendVerificationEmail(Customer customerDetails) {
		new Thread(() -> {
			try {
				// wait for 2 minutes
				Thread.sleep(2 * 60 * 1000);
			} catch (InterruptedException e) {
			}
			// send email verification mail
			emailService.sendVerificationEmail(customerDetails);
		}).start();
	}

	@Override
	public boolean verifyEmailID(long userId, String verificationCode) {
		Optional<Customer> customer = customerDao.findById(userId);
		if (!customer.isPresent()) {
			logger.warn("User does not exists '{}'", userId);
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		Customer user = customer.get();
		Optional<CustomerDetails> customerDetails = customerDetailsDao.findByUserIdAndActive(user.getId(), true);
		if (!customerDetails.isPresent()) {
			logger.warn("User details does not exists '{}'", userId);
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		CustomerDetails customerDetail = customerDetails.get();
		if (customerDetail.isEmailVerified() || Strings.isBlank(user.getEmailVerificationCode())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "User is already verified!!");
		}
		if (!verificationCode.equals(user.getEmailVerificationCode())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Verification link is expired!!");
		}
		user.setEmailVerificationCode(null);
		customerDao.save(user);
		customerDetail.setEmailVerified(true);
		customerDetailsDao.save(customerDetail);
		return true;
	}

	@Override
	public CustomerProfile getUserDetails() {
		Customer customer = getLoggedInUser();
		CustomerProfile profile = new CustomerProfile(customer);
		if (customerDetailsDao.existsByUserIdAndActive(customer.getId(), true)) {
			Optional<CustomerDetails> customerDetails = customerDetailsDao.findByUserIdAndActive(customer.getId(),
					true);
			profile.setCustomerDetails(customerDetails.get());
		}
		// marking older entry as obsolete / inactive
		List<Address> olderAddresses = addressDao.findByUserIdAndActive(customer.getId(), true);
		profile.setAddresses(olderAddresses);
		return profile;
	}

	@Override
	public void updateUserDetails(CustomerProfile profileDetails) {
		Customer loggedInUser = getLoggedInUser();
		if (loggedInUser.getId() != profileDetails.getId()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are not authorized!!");
		}
		addNewProfileEntry(profileDetails);
	}

	private void addNewProfileEntry(CustomerProfile profileDetails) {
		Optional<CustomerDetails> oldDetails = customerDetailsDao.findByUserIdAndActive(profileDetails.getId(), true);
		// marking all existing entries as obsolete / inactive
		List<CustomerDetails> olderDetails = customerDetailsDao.findByUserId(profileDetails.getId());
		if (null != olderDetails && !olderDetails.isEmpty()) {
			olderDetails.forEach(action -> {
				action.setActive(false);
			});
			customerDetailsDao.saveAll(olderDetails);
		}
		// creating new entry with active as true
		CustomerDetails customerDetails = new CustomerDetails(profileDetails);
		// transfer non changeable properties to new entry
		oldDetails.ifPresent(details -> {
			customerDetails.setProfilePicResorceId(details.getProfilePicResorceId());
			customerDetails.setEmailVerified(details.isEmailVerified());
			customerDetails.setProfilePicResorceId(details.getProfilePicResorceId());
			customerDetails.setProfileVerified(details.isProfileVerified());
		});
		customerDetailsDao.save(customerDetails);
	}

	@Override
	public void addAddress(Address address) {
		Customer loggedInUser = getLoggedInUser();
		if (loggedInUser.getId() != address.getUserId()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are not authorized!!");
		}
		if (address.isPermanent()) {
			Optional<Address> oldPermanentAddress = addressDao.findByUserIdAndPermanentAndActive(address.getUserId(),
					true, true);
			oldPermanentAddress.ifPresent(oldAddress -> {
				oldAddress.setPermanent(false);
				addressDao.save(oldAddress);
			});
		}
		address.setActive(true);
		addressDao.save(address);
	}

	@Override
	public void removeAddress(long addressId) {
		Customer loggedInUser = getLoggedInUser();
		Optional<Address> address = addressDao.findByIdAndActive(addressId, true);
		if (!address.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Address does not exists!!");
		}
		Address delAddress = address.get();
		if (loggedInUser.getId() != delAddress.getUserId()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are not authorized!!");
		}
		delAddress.setActive(false);
		addressDao.save(delAddress);
	}

	@Override
	public void changePassword(ChangePasswordForm passwordForm) {
		Customer loggedInUser = getLoggedInUser();
		logger.error("Password change {} request For '{}'", passwordForm, loggedInUser);
		if (!bcryptEncoder.matches(passwordForm.getOldPassword(), loggedInUser.getPassword())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Invalid Password!!");
		}
		if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "New password is different from Confirmation password!!");
		}
		if (passwordForm.getOldPassword().equals(passwordForm.getNewPassword())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Do not use same password!!");
		}
		loggedInUser.setPassword(bcryptEncoder.encode(passwordForm.getNewPassword()));
		customerDao.save(loggedInUser);
	}

	@Override
	public void forgetPassword(ForgetPasswordForm passwordForm) {
		Optional<Customer> customer = customerDao.findByUsernameOrEmailId(passwordForm.getUsernameOrEmail(),
				passwordForm.getUsernameOrEmail());
		if (!customer.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		Customer user = customer.get();
		if (!DateUtil.getDate(user.getDob()).equals(passwordForm.getDob())) {
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		generateUuidAndSendMail(user);
	}

	private void generateUuidAndSendMail(Customer user) {
		String randomPassword = CaptchaGeneratorUtil.generateNewPassword();
		user.setPassword(bcryptEncoder.encode(randomPassword));
		customerDao.save(user);
		emailService.sendForgetPasswordEmail(randomPassword, user);
	}

}
