package com.hegemony.arraymi.captcha.form;

import java.util.List;

import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.Wallet;

public class WalletDetails {

	private int transactionCount;

	private double totalEarning;

	private double availableBalance;

	private double totalWidrawnAmount;

	private double totalWithdrawnRequestAmount;

	private double creditAmountPerQuestion;

	private double minWidrawnAmount;

	private List<Transaction> allTransactions;

	public WalletDetails(int transactionCount, String country, Wallet wallet, List<Transaction> allTransactions) {
		this.transactionCount = transactionCount;
		this.totalWidrawnAmount = wallet.getTotalWidrawnAmount();
		this.totalWithdrawnRequestAmount = wallet.getTotalWithdrawnRequestAmount();
		this.minWidrawnAmount = wallet.getMinWidrawnAmount();
		this.creditAmountPerQuestion = wallet.getCreditAmount(1, country);
		this.availableBalance = wallet.getCurrentBalance() - totalWithdrawnRequestAmount;
		this.totalEarning = availableBalance + totalWidrawnAmount;
		this.allTransactions = allTransactions;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}

	public double getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(double totalEarning) {
		this.totalEarning = totalEarning;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public double getTotalWidrawnAmount() {
		return totalWidrawnAmount;
	}

	public void setTotalWidrawnAmount(double totalWidrawnAmount) {
		this.totalWidrawnAmount = totalWidrawnAmount;
	}

	public double getTotalWithdrawnRequestAmount() {
		return totalWithdrawnRequestAmount;
	}

	public void setTotalWithdrawnRequestAmount(double totalWithdrawnRequestAmount) {
		this.totalWithdrawnRequestAmount = totalWithdrawnRequestAmount;
	}

	public double getCreditAmountPerQuestion() {
		return creditAmountPerQuestion;
	}

	public void setCreditAmountPerQuestion(double creditAmountPerQuestion) {
		this.creditAmountPerQuestion = creditAmountPerQuestion;
	}

	public double getMinWidrawnAmount() {
		return minWidrawnAmount;
	}

	public void setMinWidrawnAmount(double minWidrawnAmount) {
		this.minWidrawnAmount = minWidrawnAmount;
	}

	public List<Transaction> getAllTransactions() {
		return allTransactions;
	}

	public void setAllTransactions(List<Transaction> allTransactions) {
		this.allTransactions = allTransactions;
	}

}
