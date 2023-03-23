package com.hegemony.arraymi.captcha.util;

public enum Operation {

	ADD(999), SUB(999), MULTIPLY(29), DIVIDE(20), EXP(15);

	private final int limit;

	private Operation(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

}
