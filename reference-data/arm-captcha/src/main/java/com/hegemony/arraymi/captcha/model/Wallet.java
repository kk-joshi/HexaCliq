package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hegemony.arraymi.captcha.util.AppConstants;
import com.hegemony.arraymi.captcha.util.CountryUtil;

@Entity
public class Wallet extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5552599973808950439L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private long userId;

	@Column
	private double currentBalance;

	@Column
	private double multiplierFactor;

	@Column
	private double totalWidrawnAmount;

	@Column
	private double totalWithdrawnRequestAmount;

	@Column
	private double minWidrawnAmount;

	// for hibernate
	private Wallet() {
		super();
	}

	public Wallet(long userId, double multiplierFactor, double minWidrawnAmount) {
		this();
		this.userId = userId;
		this.multiplierFactor = multiplierFactor;
		this.minWidrawnAmount = minWidrawnAmount;
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
	
	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public double getMultiplierFactor() {
		return multiplierFactor;
	}

	public void setMultiplierFactor(double multiplierFactor) {
		this.multiplierFactor = multiplierFactor;
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

	public double getMinWidrawnAmount() {
		return minWidrawnAmount;
	}

	public void setMinWidrawnAmount(double minWidrawnAmount) {
		this.minWidrawnAmount = minWidrawnAmount;
	}

	public double getCreditAmount(int attempt, String country) {
		return CountryUtil.getAmount(multiplierFactor * AppConstants.CORRECT_ANSWER_AMT, country);
	}

	public void addAmount(double creditAmount) {
		currentBalance += creditAmount;
	}

	public void withdrawAmountRequest(double withdrawnRequestAmount) {
		totalWithdrawnRequestAmount += withdrawnRequestAmount;
	}

	public void withdrawAmount(double withdrawnAmount) {
		totalWidrawnAmount += withdrawnAmount;
		totalWithdrawnRequestAmount -= withdrawnAmount;
		currentBalance -= withdrawnAmount;
	}

	public boolean isWithdrawnAllowed(double amount) {
		return amount >= minWidrawnAmount && ((currentBalance - totalWithdrawnRequestAmount) > amount);
	}
}
