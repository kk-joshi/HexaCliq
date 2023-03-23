package com.hegemony.arraymi.captcha.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum ContentType {

	PROFILE_PICTURE, ADDRESSPROOF, BANK_DETAILS, OTHER;

	public static ContentType getContentType(String type) {
		ContentType result = OTHER;
		Optional<ContentType> findFirst = Stream.of(values()).filter(c -> c.toString().equalsIgnoreCase(type))
				.findFirst();
		if (findFirst.isPresent()) {
			result = findFirst.get();
		}
		return result;
	}

}
