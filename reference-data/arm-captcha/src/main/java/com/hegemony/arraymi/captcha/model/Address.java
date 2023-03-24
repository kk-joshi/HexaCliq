package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

@Entity
public class Address extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491738000099917884L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private long userId;

	@Column
	private boolean active;

	@Column
	private boolean permanent;

	@Column(nullable = false)
	private String addressLine1;

	@Column(nullable = false)
	private String addressLine2;

	@Column(nullable = false)
	private String addressCity;

	@Column(nullable = false)
	private String addressBarangay;

	@Column(nullable = false)
	private String addressProvince;

	// for hibernate
	private Address() {
		super();
	}

	public Address(long userId, String addressLine1, String addressLine2, String addressCity, String addressBarangay,
			String addressProvince) {
		this();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressCity = addressCity;
		this.addressBarangay = addressBarangay;
		this.addressProvince = addressProvince;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressBarangay() {
		return addressBarangay;
	}

	public void setAddressBarangay(String addressBarangay) {
		this.addressBarangay = addressBarangay;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", userId=" + userId + ", active=" + active + ", isPermanent=" + permanent
				+ ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressCity=" + addressCity
				+ ", addressBarangay=" + addressBarangay + ", addressProvince=" + addressProvince + "]";
	}

	public boolean isValid() {
		return !(StringUtils.isEmpty(addressLine1) || StringUtils.isEmpty(addressLine2)
				|| StringUtils.isEmpty(addressCity) || StringUtils.isEmpty(addressBarangay)
				|| StringUtils.isEmpty(addressProvince));
	}

}
