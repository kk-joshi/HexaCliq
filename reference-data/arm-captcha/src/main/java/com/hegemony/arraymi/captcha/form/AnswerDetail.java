package com.hegemony.arraymi.captcha.form;

public class AnswerDetail {

	private long questionId;
	private double answer;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public double getAnswer() {
		return answer;
	}

	public void setAnswer(double answer) {
		this.answer = answer;
	}

	public boolean isValid() {
		return questionId != 0;
	}

}
