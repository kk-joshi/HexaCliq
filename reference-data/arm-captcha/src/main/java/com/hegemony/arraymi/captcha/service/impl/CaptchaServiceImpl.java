package com.hegemony.arraymi.captcha.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.dao.ICaptchaQuestionDao;
import com.hegemony.arraymi.captcha.dao.ICustomerDetailsDao;
import com.hegemony.arraymi.captcha.dao.ITransactionDao;
import com.hegemony.arraymi.captcha.dao.IWalletDao;
import com.hegemony.arraymi.captcha.form.AnswerDetail;
import com.hegemony.arraymi.captcha.jwt.JwtTokenUtil;
import com.hegemony.arraymi.captcha.model.CaptchaQuestion;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;
import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.TransactionType;
import com.hegemony.arraymi.captcha.model.Wallet;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.ICaptchaService;
import com.hegemony.arraymi.captcha.service.ICustomerService;
import com.hegemony.arraymi.captcha.util.CaptchaGeneratorUtil;
import com.hegemony.arraymi.captcha.util.CountryUtil;
import com.hegemony.arraymi.captcha.util.DateUtil;

@Service
public class CaptchaServiceImpl extends BaseService implements ICaptchaService {

	public static final int MAX_QUESTIONS = 10;

	@Autowired
	private ICaptchaQuestionDao captchaDao;

	@Autowired
	private ITransactionDao transactionDao;

	@Autowired
	private IWalletDao walletDao;

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private ICustomerDetailsDao customerDetailsDao;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public void generateCaptchaForEachUser() {
		List<Customer> findUsers = customerDao.findAll();
		String dateTomorrowDateStr = DateUtil.getDateTomorrowDateStr();
		findUsers.forEach(user -> {
			// start new thread for each user
			new Thread(() -> {
				getAllCaptchaIds(user.getId(), dateTomorrowDateStr);
			}).start();
			// no need to send alerts
		});
	}

	@Override
	public void sendNewCaptchaAlertToAllUsers() {
		List<Customer> findUsers = customerDao.findAll();
		findUsers.forEach(user -> {
			emailService.sendAlertForNewCaptcha(user);
		});

	}

	@Override
	public List<Long> getAllCaptchaIds() {
		Customer loggedInUser = getLoggedInUser();
		return getAllCaptchaIds(loggedInUser.getId(), DateUtil.getDate(new Date()));
	}

	private List<Long> getAllCaptchaIds(long userId, String date) {
		if (!customerDao.existsById(userId)) {
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		// TODO generate 2 different list for correct answer info
		boolean existsById = captchaDao.existsByUserIdAndCreatedOn(userId, date);
		if (existsById) {
			List<CaptchaQuestion> todaysCaptcha = captchaDao.findByUserIdAndCreatedOn(userId, date);
			if (todaysCaptcha != null && !todaysCaptcha.isEmpty()) {
				return todaysCaptcha.stream().map(qa -> qa.getId()).collect(Collectors.toList());
			}
		}
		List<CaptchaQuestion> questionAndAnswers = new ArrayList<>();
		for (int i = 0; i < MAX_QUESTIONS; i++) {
			CaptchaQuestion questionAndAnswer = CaptchaGeneratorUtil.getQuestionAndAnswer(userId, date);
			questionAndAnswers.add(questionAndAnswer);
		}
		captchaDao.saveAll(questionAndAnswers);
		return questionAndAnswers.stream().map(qa -> qa.getId()).collect(Collectors.toList());
	}

	@Override
	public byte[] getCaptcha(long questionId, String token) {
		if (Strings.isBlank(token) || jwtTokenUtil.isTokenExpired(token)) {
			throw new AppException(HttpStatus.UNAUTHORIZED, "Token expired!!");
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Customer> findFirst = customerDao.findByUsernameOrEmailId(username, username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}
		Customer user = findFirst.get();
		Optional<CaptchaQuestion> captcha = captchaDao.findById(questionId);
		if (!captcha.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Image does not exists!!");
		}
		CaptchaQuestion captchaQuestion = captcha.get();
		if (captchaQuestion.getUserId() != user.getId()) {
			throw new AppException(HttpStatus.UNAUTHORIZED, "You are not authorized!!");
		}
		return CaptchaGeneratorUtil.getCaptcha(captchaQuestion.getQuestion());
	}

	@Override
	public boolean checkAnswer(AnswerDetail answer) {
		Customer loggedInUser = getLoggedInUser();
		Optional<CaptchaQuestion> captchaQuestion = captchaDao.findById(answer.getQuestionId());
		if (!captchaQuestion.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Captcha does not exists!!");
		}
		CaptchaQuestion captcha = captchaQuestion.get();
		if (captcha.getUserId() != loggedInUser.getId()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are not authorized to answer this question!!");
		}
		if (captcha.isAnsweredCorrect()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Already answered this question correctly!!");
		}
		double ans = captcha.getAnswer();
		boolean answeredCorrect = ans == answer.getAnswer();
		captcha.setAnsweredCorrect(answeredCorrect);
		captcha.incrementAttempt();
		captcha = captchaDao.save(captcha);
		if (answeredCorrect) {
			addMoney(captcha.getAttempt(), answer, loggedInUser);
		}
		return answeredCorrect;
	}

	private void addMoney(int attempt, AnswerDetail answer, Customer user) {
		Optional<CustomerDetails> oldDetails = customerDetailsDao.findByUserIdAndActive(user.getId(), true);
		if (!oldDetails.isPresent()) {
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Something went wrong please contect customer care");
		}
		Optional<Wallet> wallet = walletDao.findByUserId(user.getId());
		Wallet userWallet;
		if (!wallet.isPresent()) {
			userWallet = customerService.createWallet(user.getId(), oldDetails.get().getCountry());
		} else {
			userWallet = wallet.get();
		}
		double creditAmount = userWallet.getCreditAmount(attempt, oldDetails.get().getCountry());
		Transaction transaction = new Transaction(user.getId(), answer.getQuestionId(), creditAmount,
				userWallet.getCurrentBalance(), TransactionType.CREDIT_CAPTCHA);
		transactionDao.save(transaction);
		userWallet.addAmount(creditAmount);
		walletDao.save(userWallet);
		emailService.sendTransactionAlert(CountryUtil.getCurrency(oldDetails.get().getCountry()), transaction, user);
	}

}
