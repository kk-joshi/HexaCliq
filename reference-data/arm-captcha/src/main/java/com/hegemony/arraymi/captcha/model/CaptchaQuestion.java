package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CaptchaQuestion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7080283769740012913L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private long userId;

	@Column
	private boolean answeredCorrect;

	@Column
	private int attempt;

	@Column(nullable = false)
	private String question;

	@Column
	private double answer;

	@Column(nullable = false)
	private String createdOn;

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

	public boolean isAnsweredCorrect() {
		return answeredCorrect;
	}

	public void setAnsweredCorrect(boolean answeredCorrect) {
		this.answeredCorrect = answeredCorrect;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public double getAnswer() {
		return answer;
	}

	public void setAnswer(double answer) {
		this.answer = answer;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public void incrementAttempt() {
		attempt++;
	}

}
