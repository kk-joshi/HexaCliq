package com.hegemony.arraymi.captcha.form;

public class WithdrawRequest {

	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isValid() {
		return amount != 0;
	}

}
