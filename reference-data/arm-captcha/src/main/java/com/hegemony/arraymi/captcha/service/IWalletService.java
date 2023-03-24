package com.hegemony.arraymi.captcha.service;

import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.TransactionType;

public interface IWalletService {

	void addMoneyToWallet(Customer user, double birthdayGift, TransactionType creditBday);

	void creditGiftToBirthdayPeople();

	void creditGiftAsSanta();

	long withrawRequest(double amount);
}
