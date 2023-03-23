package com.hegemony.arraymi.captcha.util;

public class CountryDetails {
	private String shortName;
	private String fullName;
	private String currencySymbol;
	private String currencyName;
	private int valuation;

	public CountryDetails(String shortName, String fullName, String currencySymbol, String currencyName,
			int valuation) {
		super();
		this.shortName = shortName;
		this.fullName = fullName;
		this.currencyName = currencyName;
		this.currencySymbol = currencySymbol;
		this.valuation = valuation;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public int getValuation() {
		return valuation;
	}

	public void setValuation(int valuation) {
		this.valuation = valuation;
	}

}
