package com.hegemony.arraymi.captcha.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.QueryRequest;
import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.TransactionType;
import com.hegemony.arraymi.captcha.service.IEmailService;
import com.hegemony.arraymi.captcha.util.AppConstants;
import com.hegemony.arraymi.captcha.util.CaptchaGeneratorUtil;
import com.hegemony.arraymi.captcha.util.EmailTemplates;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${application.name}")
	private String appName;

	@Value("${ui.application.url}")
	private String appUrl;

	@Value("${mail.bcc}")
	private String bccEmail;

	@Override
	public void sendForgetPasswordEmail(String newPassword, Customer user) {
		String bodyStr = EmailTemplates.FORGET_PASSWORD_TEMPLATE.replaceAll(EmailTemplates.APP_NAME, appName);
		bodyStr = bodyStr.replaceAll(EmailTemplates.APP_URL, appUrl);
		bodyStr = bodyStr.replaceAll(EmailTemplates.NAME, user.getFullName());
		bodyStr = bodyStr.replaceAll(EmailTemplates.PROFILE_ID, user.getUsername());
		bodyStr = bodyStr.replaceAll(EmailTemplates.EMAIL_ID, user.getEmailId());
		bodyStr = bodyStr.replaceAll(EmailTemplates.PASSWORD, newPassword);
		bodyStr = bodyStr.replaceAll(EmailTemplates.SUPPORT_MAIL, fromEmail);
//		sendHtmlMessage(user.getEmailId(), AppConstants.FORGET_PASSWORD_SUBJECT, bodyStr);
		sendHtmlMessage("thakurpratik301@gmail.com", AppConstants.FORGET_PASSWORD_SUBJECT, bodyStr);
	}

	@Override
	public void sendVerificationEmail(Customer user) {
		String bodyStr = EmailTemplates.EMAIL_VERIFICATION_TEMPLATE.replaceAll(EmailTemplates.APP_NAME, appName);
		bodyStr = bodyStr.replaceAll(EmailTemplates.APP_URL, appUrl);
		bodyStr = bodyStr.replaceAll(EmailTemplates.NAME, user.getFullName());
		bodyStr = bodyStr.replaceAll(EmailTemplates.PROFILE_ID, user.getUsername());
		bodyStr = bodyStr.replaceAll(EmailTemplates.EMAIL_ID, user.getEmailId());
		bodyStr = bodyStr.replaceAll(EmailTemplates.VERIFY_EMAIL_URL,
				appUrl + "/verification?activationCode=" + user.getEmailVerificationCode() + "&userId=" + user.getId());
		bodyStr = bodyStr.replaceAll(EmailTemplates.SUPPORT_MAIL, fromEmail);
//		sendHtmlMessage(user.getEmailId(), AppConstants.EMAIL_VERIFICATION_SUBJECT, bodyStr);
		sendHtmlMessage("thakurpratik301@gmail.com", AppConstants.EMAIL_VERIFICATION_SUBJECT, bodyStr);

	}

	@Override
	public void sendAlertForNewCaptcha(Customer user) {
		String bodyStr = EmailTemplates.NEW_CAPTCHA_TEMPLATE.replaceAll(EmailTemplates.APP_NAME, appName);
		bodyStr = bodyStr.replaceAll(EmailTemplates.APP_URL, appUrl);
		bodyStr = bodyStr.replaceAll(EmailTemplates.NAME, user.getFullName());
		bodyStr = bodyStr.replaceAll(EmailTemplates.PROFILE_ID, user.getUsername());
		bodyStr = bodyStr.replaceAll(EmailTemplates.EMAIL_ID, user.getEmailId());
		bodyStr = bodyStr.replaceAll(EmailTemplates.CAPTCHA_COUNT, CaptchaServiceImpl.MAX_QUESTIONS + "");
		bodyStr = bodyStr.replaceAll(EmailTemplates.SUPPORT_MAIL, fromEmail);
//		sendHtmlMessage(user.getEmailId(), AppConstants.NEW_CAPTCHA_SUBJECT, bodyStr);
		sendHtmlMessage("thakurpratik301@gmail.com", AppConstants.NEW_CAPTCHA_SUBJECT, bodyStr);
	}

	@Override
	public void sendQueryConfirmationEmail(QueryRequest queryRequest) {
		String bodyStr = EmailTemplates.CUSTOMER_QUERY_CONFIRMATION_TEMPLATE.replaceAll(EmailTemplates.APP_NAME,
				appName);
		bodyStr = bodyStr.replaceAll(EmailTemplates.APP_URL, appUrl);
		bodyStr = bodyStr.replaceAll(EmailTemplates.NAME, queryRequest.getFullname());
		bodyStr = bodyStr.replaceAll(EmailTemplates.EMAIL_ID, queryRequest.getEmail());
		bodyStr = bodyStr.replaceAll(EmailTemplates.TRAN_REF, queryRequest.getReferenceNumber());
		bodyStr = bodyStr.replaceAll(EmailTemplates.SUPPORT_MAIL, fromEmail);
//		sendHtmlMessage(user.getEmailId(), AppConstants.CUSTOMER_QUERY_SUBJECT, bodyStr);
		sendHtmlMessage("thakurpratik301@gmail.com", AppConstants.CUSTOMER_QUERY_SUBJECT, bodyStr);
	}

	@Override
	public void sendTransactionAlert(String currency, Transaction transaction, Customer user) {
		String bodyStr = getTemplate(transaction.getType()).replaceAll(EmailTemplates.APP_NAME, appName);
		bodyStr = bodyStr.replaceAll(EmailTemplates.APP_URL, appUrl);
		bodyStr = bodyStr.replaceAll(EmailTemplates.NAME, user.getFullName());
		bodyStr = bodyStr.replaceAll(EmailTemplates.PROFILE_ID, user.getUsername());
		bodyStr = bodyStr.replaceAll(EmailTemplates.EMAIL_ID, user.getEmailId());
		bodyStr = bodyStr.replaceAll(EmailTemplates.CURRENCY, currency);
		bodyStr = bodyStr.replaceAll(EmailTemplates.AMOUNT, Double.toString(transaction.getAmount()));
		bodyStr = bodyStr.replaceAll(EmailTemplates.FINAL_AMOUNT, Double.toString(transaction.getFinalWalletBalance()));
		bodyStr = bodyStr.replaceAll(EmailTemplates.SUPPORT_MAIL, fromEmail);
//		sendHtmlMessage(user.getEmailId(), AppConstants.NEW_CAPTCHA_SUBJECT, bodyStr);
		sendHtmlMessage("thakurpratik301@gmail.com", AppConstants.WALLET_TRANSACTION_SUBJECT, bodyStr);
	}

	private String getTemplate(TransactionType transType) {
		String templateName = EmailTemplates.CAPTCHA_SOLVED_TEMPLATE;
		switch (transType) {
		case CREDIT_BDAY:
			templateName = EmailTemplates.BIRTHDAY_TEMPLATE;
			break;

		case CREDIT_CHRISTMAS:
			templateName = EmailTemplates.CHRISTMAS_TEMPLATE;
			break;

		case CREDIT_WELCOME_BONUS:
			templateName = EmailTemplates.WELCOME_TEMPLATE;
			break;

		case WITHDRAWAL:
			templateName = EmailTemplates.WITHDRAWAL_TEMPLATE;
			break;

		case CREDIT_CAPTCHA:
		default:
			templateName = EmailTemplates.CAPTCHA_SOLVED_TEMPLATE;
			break;
		}
		return templateName;
	}

	private void sendHtmlMessage(String to, String subject, String body) {
		new Thread(() -> {
			try {
				MimeMessage message = emailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(message, true);

				helper.setFrom(fromEmail);
				helper.setTo(to);
				if (Strings.isNotBlank(bccEmail)) {
					helper.setBcc(bccEmail);
				}
				// TODO remove random text
				helper.setSubject("[" + appName + "]" + subject + CaptchaGeneratorUtil.generateNewPassword());
				message.setContent(body, "text/html");
				emailSender.send(message);
			} catch (MessagingException e) {

			}
		}).start();
	}

}
