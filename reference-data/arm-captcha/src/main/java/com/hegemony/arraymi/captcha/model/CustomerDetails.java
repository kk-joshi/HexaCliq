package com.hegemony.arraymi.captcha.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hegemony.arraymi.captcha.form.CustomerProfile;

@Entity
public class CustomerDetails extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 31249476187311L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private long userId;

	@Column(nullable = false)
	private boolean emailVerified;

	@Column(nullable = false)
	private boolean profileVerified;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = true)
	private Long profilePicResorceId;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private String mobileNo;

	@Column(nullable = true)
	private String occupation;

	@Column
	private double income;

	@Column(nullable = true)
	private String payPalAccountId;

	@Column
	private Date updatedDate;

	@Column
	private Date updatedBy;

	// for hibernate
	public CustomerDetails() {
		super();
	}

	public CustomerDetails(CustomerProfile customerDetails) {
		this();
		setActive(true);
		setUserId(customerDetails.getId());
		updateDetails(customerDetails);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getProfilePicResorceId() {
		return profilePicResorceId;
	}

	public void setProfilePicResorceId(Long profilePicResorceId) {
		this.profilePicResorceId = profilePicResorceId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getPayPalAccountId() {
		return payPalAccountId;
	}

	public void setPayPalAccountId(String payPalAccountId) {
		this.payPalAccountId = payPalAccountId;
	}

	public boolean isProfileVerified() {
		return profileVerified;
	}

	public void setProfileVerified(boolean profileVerified) {
		this.profileVerified = profileVerified;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Date updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void updateDetails(CustomerProfile customerDetails) {
		if (null != customerDetails) {
			setCountry(customerDetails.getCountry());
			setMobileNo(customerDetails.getMobileNo());
			setPayPalAccountId(customerDetails.getPayPalAccountId());
			setOccupation(customerDetails.getOccupation());
			setIncome(customerDetails.getIncome());
		}
	}

}
