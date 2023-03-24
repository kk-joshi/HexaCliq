package com.hegemony.arraymi.captcha.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.dao.ICustomerDetailsDao;
import com.hegemony.arraymi.captcha.dao.ITransactionDao;
import com.hegemony.arraymi.captcha.dao.IWalletDao;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;
import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.TransactionType;
import com.hegemony.arraymi.captcha.model.Wallet;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.IWalletService;
import com.hegemony.arraymi.captcha.util.AppConstants;
import com.hegemony.arraymi.captcha.util.CountryUtil;

@Service
public class WalletCreditServiceImpl extends BaseService implements IWalletService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ITransactionDao transactionDao;

	@Autowired
	private IWalletDao walletDao;

	@Autowired
	private ICustomerDetailsDao customerDetailsDao;

	@Override
	public void addMoneyToWallet(Customer user, double amount, TransactionType transType) {
		Optional<Wallet> wallet = walletDao.findByUserId(user.getId());
		if (!wallet.isPresent()) {
			logger.error("Unable to add balance For {} to {}", transType, user);
			return;
		}
		Optional<CustomerDetails> oldDetails = customerDetailsDao.findByUserIdAndActive(user.getId(), true);
		if (!oldDetails.isPresent()) {
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Something went wrong please contect customer care");
		}
		amount = CountryUtil.getAmount(amount, oldDetails.get().getCountry());
		Wallet userWallet = wallet.get();
		Transaction transaction = new Transaction(userWallet.getUserId(), 0, amount, userWallet.getCurrentBalance(),
				transType);
		transactionDao.save(transaction);
		userWallet.addAmount(amount);
		walletDao.save(userWallet);
		emailService.sendTransactionAlert(CountryUtil.getCurrency(oldDetails.get().getCountry()), transaction, user);
	}

	@Override
	public void creditGiftToBirthdayPeople() {
		List<Customer> todaysBirthday = customerDao.findAllByDob();
		todaysBirthday.forEach(user -> {
			addMoneyToWallet(user, AppConstants.BIRTHDAY_GIFT, TransactionType.CREDIT_BDAY);
		});
	}

	@Override
	public void creditGiftAsSanta() {
		List<Customer> todaysBirthday = customerDao.findAll();
		todaysBirthday.forEach(user -> {
			addMoneyToWallet(user, AppConstants.CHRISTMAS_GIFT, TransactionType.CREDIT_CHRISTMAS);
		});
	}

	@Override
	public long withrawRequest(double amount) {
		Customer loggedInUser = getLoggedInUser();
		Optional<CustomerDetails> userDetails = customerDetailsDao.findByUserIdAndActive(loggedInUser.getId(), true);
		if (!userDetails.isPresent() || !userDetails.get().isEmailVerified()) {
			logger.error("Unable to process withdraw request For {} ", loggedInUser);
			throw new AppException(HttpStatus.BAD_REQUEST,
					"Please verify email id before raising withdrawal request!!");
		}
		if (userDetails.isPresent() && !userDetails.get().isProfileVerified()) {
			logger.error("Unable to process withdraw request For {} ", loggedInUser);
			throw new AppException(HttpStatus.BAD_REQUEST,
					"Your profile is not verified please provide verification documents!!");
		}
		Optional<Wallet> wallet = walletDao.findByUserId(loggedInUser.getId());
		if (!wallet.isPresent()) {
			logger.error("Unable to process withdraw request For {} ", loggedInUser);
			throw new AppException(HttpStatus.BAD_REQUEST, "Insufficient funds!!");
		}
		Wallet userWallet = wallet.get();
		if (!userWallet.isWithdrawnAllowed(amount)) {
			logger.error("Insufficient funds in wallet {} of {} ", userWallet, loggedInUser);
			throw new AppException(HttpStatus.BAD_REQUEST, "Insufficient funds!!");
		}
		Transaction transaction = new Transaction(userWallet.getUserId(), 0, amount, userWallet.getCurrentBalance(),
				TransactionType.WITHDRAWAL);
		transaction = transactionDao.save(transaction);
		userWallet.withdrawAmountRequest(amount);
		walletDao.save(userWallet);
		return transaction.getId();
	}

}
