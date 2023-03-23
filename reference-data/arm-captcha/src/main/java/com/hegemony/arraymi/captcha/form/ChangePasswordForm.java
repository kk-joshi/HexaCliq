package com.hegemony.arraymi.captcha.form;

import org.apache.logging.log4j.util.Strings;

public class ChangePasswordForm {

	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordForm [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}

	public boolean isValid(boolean checkOldPassword) {
		boolean isValid;
		if (checkOldPassword) {
			isValid = Strings.isNotBlank(oldPassword) && Strings.isNotBlank(newPassword)
					&& Strings.isNotBlank(confirmPassword);
		} else {
			isValid = Strings.isNotBlank(newPassword) && Strings.isNotBlank(confirmPassword);
		}
		return isValid;
	}

}
