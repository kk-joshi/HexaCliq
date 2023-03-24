package com.hegemony.arraymi.captcha.service;

import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.QueryRequest;
import com.hegemony.arraymi.captcha.model.Transaction;

public interface IEmailService {

	void sendForgetPasswordEmail(String newPassword, Customer customerDetails);

	void sendVerificationEmail(Customer customerDetails);

	void sendAlertForNewCaptcha(Customer customerDetails);

	void sendTransactionAlert(String currency, Transaction transaction, Customer user);

	void sendQueryConfirmationEmail(QueryRequest queryRequest);

}
