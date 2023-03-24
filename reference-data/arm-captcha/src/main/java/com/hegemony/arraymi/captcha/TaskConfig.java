
package com.hegemony.arraymi.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.hegemony.arraymi.captcha.service.ICaptchaService;
import com.hegemony.arraymi.captcha.service.IWalletService;

@Configuration
public class TaskConfig {

	@Autowired
	private IWalletService walletCreditServices;

	@Autowired
	private ICaptchaService captchaService;

	// Schedule job Every 12 noon
	@Scheduled(cron = "0 0 0 * * *")
	public void addMoneyForBirthDay() {
		walletCreditServices.creditGiftToBirthdayPeople();
	}

	// Schedule job Every Christmas
	@Scheduled(cron = "0 0 0 25 12 ?")
	public void addMoneyForChristmas() {
		walletCreditServices.creditGiftAsSanta();
	}

	// Generate Captcha for today
	@Scheduled(cron = "0 0 3 * * *")
	public void generateCaptchaForTomorrow() {
		captchaService.generateCaptchaForEachUser();
	}

	// Schedule job Every 12:10 AM
	@Scheduled(cron = "0 10 0 * * *")
	public void sendAlertForNewCaptcha() {
		captchaService.sendNewCaptchaAlertToAllUsers();
	}
}
