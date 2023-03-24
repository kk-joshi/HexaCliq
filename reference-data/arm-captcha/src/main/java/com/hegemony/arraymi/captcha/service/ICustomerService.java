package com.hegemony.arraymi.captcha.service;

import com.hegemony.arraymi.captcha.form.ChangePasswordForm;
import com.hegemony.arraymi.captcha.form.CustomerProfile;
import com.hegemony.arraymi.captcha.form.ForgetPasswordForm;
import com.hegemony.arraymi.captcha.form.RegistrationForm;
import com.hegemony.arraymi.captcha.model.Address;
import com.hegemony.arraymi.captcha.model.Wallet;

public interface ICustomerService {

	void regsiterUser(RegistrationForm customerDetails);

	CustomerProfile getUserDetails();

	void updateUserDetails(CustomerProfile profileDetails);

	void addAddress(Address address);

	void removeAddress(long addressId);

	Wallet createWallet(long userId, String country);

	void changePassword(ChangePasswordForm passwordForm);

	void forgetPassword(ForgetPasswordForm passwordForm);

	boolean verifyEmailID(long userId, String verificationCode);

}
