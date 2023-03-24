package com.hegemony.arraymi.captcha.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hegemony.arraymi.captcha.form.RegistrationForm;

//@Table
@Entity
public class Customer extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1667131249476187311L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column
	private String role;

	@Column(nullable = false)
	private String fullName;

	@Column(nullable = false)
	private String emailId;

	@Column(nullable = true)
	private String emailVerificationCode;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dob;

	public Customer() {
		super();
	}

	public Customer(RegistrationForm customerDetails, String encryptedPassword, String emailVerificationCode) {
		this();
		setFullName(customerDetails.getFullName());
		setEmailId(customerDetails.getEmail());
		setUsername(customerDetails.getEmail());
		setPassword(encryptedPassword);
		setDob(customerDetails.getFomattedDob());
		setEmailVerificationCode(emailVerificationCode);
		setRole("CUSTOMER");
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}

	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}

}
