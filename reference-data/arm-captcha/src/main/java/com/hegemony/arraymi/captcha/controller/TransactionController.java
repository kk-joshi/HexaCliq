package com.hegemony.arraymi.captcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hegemony.arraymi.captcha.service.ITransationService;

@RestController
@CrossOrigin(origins = "${allowed.origins}")
public class TransactionController {
	
	@Autowired
	private ITransationService transationService;

	@GetMapping("/get-wallet-details")
	public ResponseEntity<?> getWalletDetails() {
		return ResponseEntity.ok(transationService.getWalletDetails());
	}

}
