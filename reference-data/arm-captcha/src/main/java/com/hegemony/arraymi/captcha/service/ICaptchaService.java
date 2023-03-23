package com.hegemony.arraymi.captcha.service;

import java.util.List;

import com.hegemony.arraymi.captcha.form.AnswerDetail;

public interface ICaptchaService {

	byte[] getCaptcha(long questionId, String token);

	boolean checkAnswer(AnswerDetail answer);

	void generateCaptchaForEachUser();

	void sendNewCaptchaAlertToAllUsers();

	List<Long> getAllCaptchaIds();

}
