package com.hegemony.arraymi.captcha.form;

import org.apache.logging.log4j.util.Strings;

import com.hegemony.arraymi.captcha.util.DateUtil;

public class ForgetPasswordForm {

	private String usernameOrEmail;
	private String dob;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "ForgetPasswordForm [usernameOrEmail=" + usernameOrEmail + ", dob=" + dob + "]";
	}

	public boolean isValid() {
		return Strings.isNotBlank(usernameOrEmail) && Strings.isNotBlank(dob) && null != DateUtil.getDate(dob);
	}

}
