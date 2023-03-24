package com.hegemony.arraymi.captcha.form;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.hegemony.arraymi.captcha.util.DateUtil;

public class RegistrationForm {

	private String password;
	protected String fullName;
	protected String mobileNo;
	protected String email;
	protected String dob;
	protected String country;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "RegistrationForm [password=" + password + ", firstName=" + fullName + ", mobileNo=" + mobileNo
				+ ", emailId=" + email + ", dob=" + dob + ", country=" + country + "]";
	}

	public Date getFomattedDob() {
		return DateUtil.getDate(dob);
	}

	public boolean isNotEmpty() {
		return !(StringUtils.isEmpty(password) || StringUtils.isEmpty(fullName) || StringUtils.isEmpty(mobileNo)
				|| StringUtils.isEmpty(email) || StringUtils.isEmpty(dob) || StringUtils.isEmpty(country));
	}

	protected boolean isValidEmail() {
		return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}

	protected boolean isValidMobileNo() {
		return mobileNo.matches("[0-9]{10,}");
	}

	protected boolean isValidDob() {
		return DateUtil.isValidUser(dob);
	}

	protected boolean isValidData() {
		return isValidEmail() && isValidMobileNo() && isValidDob();
	}

	public boolean isValid() {
		return isNotEmpty() && isValidData();
	}

}
