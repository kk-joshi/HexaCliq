package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

@Entity
public class QueryRequest extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178302603681535429L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private String fullname;

	@Column
	private String country;

	@Column
	private String mobileNo;

	@Column
	private String email;

	@Column
	private String queryStr;

	@Column
	private String referenceNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public boolean isNotEmpty() {
		return !(StringUtils.isEmpty(fullname) || StringUtils.isEmpty(country) || StringUtils.isEmpty(mobileNo)
				|| StringUtils.isEmpty(email) || StringUtils.isEmpty(queryStr));
	}

	protected boolean isValidEmail() {
		return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}

	protected boolean isValidMobileNo() {
		return mobileNo.matches("[0-9]{10,}");
	}

	protected boolean isValidData() {
		return isValidEmail() && isValidMobileNo();
	}

	public boolean isValid() {
		return isNotEmpty() && isValidData();
	}

}
