package com.hegemony.arraymi.captcha.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;

import com.hegemony.arraymi.captcha.model.CaptchaQuestion;
import com.hegemony.arraymi.captcha.service.AppException;

public class CaptchaGeneratorUtil {

	private static final List<Operation> OPERATIONS = Collections.unmodifiableList(Arrays.asList(Operation.values()));
	private static final Random RANDOM = new Random();
	private static final int MIN_TEXT_SIZE = 6;
	private static final int DEFAULT_PASSWORD_LENGTH = 10;

	public static String generateNewPassword() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'

		String generatedString = RANDOM.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 64) && (i <= 90 || i >= 97)).limit(DEFAULT_PASSWORD_LENGTH)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	private static Operation getRandomOperation() {
		return OPERATIONS.get(RANDOM.nextInt(OPERATIONS.size()));
	}

	public static CaptchaQuestion getQuestionAndAnswer(long userId, String date) {
		Operation operation = getRandomOperation();
		int randomNumber = RANDOM.nextInt(operation.getLimit());
		int anotherNumber = RANDOM.nextInt(operation.getLimit());
		randomNumber++;
		anotherNumber++;
		CaptchaQuestion captchaQuestion = new CaptchaQuestion();
		captchaQuestion.setUserId(userId);
		captchaQuestion.setCreatedOn(date);
		switch (operation) {
		case ADD:
			captchaQuestion.setQuestion(randomNumber + " + " + anotherNumber);
			captchaQuestion.setAnswer(randomNumber + anotherNumber);
			break;
		case SUB:
			captchaQuestion.setQuestion(randomNumber + " - " + anotherNumber);
			captchaQuestion.setAnswer(randomNumber - anotherNumber);
			break;
		case MULTIPLY:
			captchaQuestion.setQuestion(randomNumber + " X " + anotherNumber);
			captchaQuestion.setAnswer(randomNumber * anotherNumber);
			break;
		case DIVIDE:
			captchaQuestion.setQuestion((randomNumber * anotherNumber) + " / " + anotherNumber);
			captchaQuestion.setAnswer(randomNumber);
			break;
		case EXP:
			anotherNumber = anotherNumber % 4;
			captchaQuestion.setQuestion(randomNumber + " ^ " + anotherNumber);
			captchaQuestion.setAnswer(Math.pow(randomNumber, anotherNumber));
			break;
		}
		return captchaQuestion;
	}

	public static byte[] getCaptcha(String question) {
		int width = 250;
		int height = 250;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();

		// fill all the image with white
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);

		// create a circle with black
		g2d.setColor(Color.black);
		g2d.fillOval(0, 0, width, height);

		// create a string with yellow
		g2d.setColor(Color.yellow);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g2d.drawString(question, question.length() > MIN_TEXT_SIZE ? 10 : 60, 150);

		// Disposes of this graphics context and releases any system resources that it
		// is using.
		g2d.dispose();

		// Save as PNG
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", byteOutputStream);
		} catch (IOException e) {
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while creating captcha!");
		}
		return byteOutputStream.toByteArray();
	}

}
