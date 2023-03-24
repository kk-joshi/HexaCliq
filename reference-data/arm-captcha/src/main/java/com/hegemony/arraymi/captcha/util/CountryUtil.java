package com.hegemony.arraymi.captcha.util;

import java.util.ArrayList;
import java.util.List;

public class CountryUtil {

	public static List<CountryDetails> getAllCountry() {
		List<CountryDetails> asd = new ArrayList<>();
		asd.add(new CountryDetails("BD", "BANGLADESH", "৳", "Taka", 18));
		asd.add(new CountryDetails("KH", "CAMBODIA", "៛", "Riel", 790));
		asd.add(new CountryDetails("ID", "INDONESIA", "Rp", "Rp", 300));
		asd.add(new CountryDetails("IN", "INDIA", "₹", "Rs", 15));
		asd.add(new CountryDetails("PH", "PHILIPPINES", "₱", "Pesos", 10));
		asd.add(new CountryDetails("SL", "SRI LANKA", "රු", "LKR", 38));
		asd.add(new CountryDetails("TW", "TAIWAN", "NT$", "NT$", 475));
		return asd;
	}

	public static String getCurrency(String country) {
		return getAllCountry().stream().filter(item -> item.getShortName().equals(country)).findFirst().get()
				.getCurrencyName();
	}

	public static double getAmount(double amount, String country) {
		return (amount * getAllCountry().stream().filter(item -> item.getShortName().equals(country)).findFirst().get()
				.getValuation());
	}
}
