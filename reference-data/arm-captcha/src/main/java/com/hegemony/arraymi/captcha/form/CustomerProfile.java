package com.hegemony.arraymi.captcha.form;

import java.util.List;

import org.apache.logging.log4j.util.Strings;

import com.hegemony.arraymi.captcha.util.DateUtil;
import com.hegemony.arraymi.captcha.model.Address;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;

public class CustomerProfile extends RegistrationForm {

	private long id;
	private boolean emailVerified;
	private boolean profileVerified;
	private double income;
	private long profilePicResorceId;
	private String occupation;
	private String payPalAccountId;
	private List<Address> addresses;

	// for getting input from UI
	private CustomerProfile() {
		super();
	}

	public CustomerProfile(long id) {
		this();
		this.id = id;
	}

	public CustomerProfile(Customer customer) {
		this(customer.getId());
		setFullName(customer.getFullName());
		setEmail(customer.getEmailId());
		setDob(DateUtil.getDate(customer.getDob()));
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		if (null != customerDetails) {
			setEmailVerified(customerDetails.isEmailVerified());
			setProfileVerified(customerDetails.isProfileVerified());
			setCountry(customerDetails.getCountry());
			setMobileNo(customerDetails.getMobileNo());
			setPayPalAccountId(customerDetails.getPayPalAccountId());
			setOccupation(customerDetails.getOccupation());
			setIncome(customerDetails.getIncome());
			if (customerDetails.getProfilePicResorceId() != null) {
				setProfilePicResorceId(customerDetails.getProfilePicResorceId());
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isProfileVerified() {
		return profileVerified;
	}

	public void setProfileVerified(boolean profileVerified) {
		this.profileVerified = profileVerified;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public long getProfilePicResorceId() {
		return profilePicResorceId;
	}

	public void setProfilePicResorceId(long profilePicResorceId) {
		this.profilePicResorceId = profilePicResorceId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPayPalAccountId() {
		return payPalAccountId;
	}

	public void setPayPalAccountId(String payPalAccountId) {
		this.payPalAccountId = payPalAccountId;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public boolean isValid() {
		return Strings.isNotBlank(occupation) && Strings.isNotBlank(payPalAccountId) && isValidMobileNo();
	}

}
