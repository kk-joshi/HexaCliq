package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Transaction extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3413711829329820018L;

	@Id
	private long id;

	@Column
	private long userId;

	@Column
	private long questionId;

	@Column
	private double amount;

	@Column
	private double initalWalletBalance;

	@Column
	private double finalWalletBalance;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	// for hibernate
	//do not change it to any other scope as id generation in manual
	private Transaction() {
		super();
	}

	public Transaction(long userId, long questionId, double amount, double initalWalletBalance, TransactionType type) {
		this();
		this.id= System.currentTimeMillis(); 
		this.userId = userId;
		this.questionId = questionId;
		this.amount = amount;
		this.initalWalletBalance = initalWalletBalance;
		if (type == TransactionType.WITHDRAWAL) {
			this.finalWalletBalance = initalWalletBalance - amount;
		} else {
			this.finalWalletBalance = initalWalletBalance + amount;			
		}
		this.type = type;
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

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInitalWalletBalance() {
		return initalWalletBalance;
	}

	public void setInitalWalletBalance(double initalWalletBalance) {
		this.initalWalletBalance = initalWalletBalance;
	}

	public double getFinalWalletBalance() {
		return finalWalletBalance;
	}

	public void setFinalWalletBalance(double finalWalletBalance) {
		this.finalWalletBalance = finalWalletBalance;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

}
