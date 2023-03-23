package com.hegemony.arraymi.captcha.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hegemony.arraymi.captcha.form.AnswerDetail;
import com.hegemony.arraymi.captcha.form.Response;
import com.hegemony.arraymi.captcha.form.WithdrawRequest;
import com.hegemony.arraymi.captcha.model.QueryRequest;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.IAppService;
import com.hegemony.arraymi.captcha.service.ICaptchaService;
import com.hegemony.arraymi.captcha.service.IWalletService;

@RestController
@CrossOrigin(origins = "${allowed.origins}")
public class AppController {

	@Value("${application.name}")
	private String appName;

	@Autowired
	private ICaptchaService captchaService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IAppService appService;

	@PostMapping("/send-query")
	public ResponseEntity<?> sendQuery(@RequestBody QueryRequest queryRequest) {
		if (null == queryRequest || !queryRequest.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid query details!!");
		}
		queryRequest = appService.customerQuery(queryRequest);
		return ResponseEntity.ok(new Response("Your query is successfully submitted. Your referrence id is "
				+ queryRequest.getReferenceNumber() + "!!"));
	}

	@GetMapping("/get-app-name")
	public String getAppName() {
		return appName;
	}

	@GetMapping("/generate-all-captcha")
	public ResponseEntity<?> getAllQuestion(HttpServletResponse response) {
		return ResponseEntity.ok(captchaService.getAllCaptchaIds());
	}

	@GetMapping("/get-captcha/{questionId}")
	public void getQuestion(@PathVariable long questionId, @RequestParam String token, HttpServletResponse response) {
		if (0 == questionId) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid userId!!");
		}
		byte[] captcha = captchaService.getCaptcha(questionId, token);
		if (null != captcha) {
			try {
				response.setContentLength(captcha.length);
				response.setContentType("");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(captcha);
				outputStream.flush();
			} catch (IOException e) {
				throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
			}
		}
	}

	@PostMapping("/answer-captcha")
	public ResponseEntity<?> checkAnswer(@RequestBody AnswerDetail answer) {
		if (null == answer || !answer.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid details!!");
		}
		if (!captchaService.checkAnswer(answer)) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Wrong answer, try again!");
		}
		return ResponseEntity.ok(new Response("Correct Answered!"));
	}

	@PostMapping("/withdraw-money")
	public ResponseEntity<?> withdrawMoney(@RequestBody WithdrawRequest withdrawRequest) {
		if (null == withdrawRequest || !withdrawRequest.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid withdrawal amount!!");
		}
		long transactionId = walletService.withrawRequest(withdrawRequest.getAmount());
		return ResponseEntity.ok(new Response("New request [" + transactionId + "] for withdrawal raised!!"));
	}

}
