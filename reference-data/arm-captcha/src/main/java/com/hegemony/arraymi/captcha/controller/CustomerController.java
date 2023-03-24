package com.hegemony.arraymi.captcha.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hegemony.arraymi.captcha.form.ChangePasswordForm;
import com.hegemony.arraymi.captcha.form.CustomerProfile;
import com.hegemony.arraymi.captcha.form.ForgetPasswordForm;
import com.hegemony.arraymi.captcha.form.RegistrationForm;
import com.hegemony.arraymi.captcha.form.Response;
import com.hegemony.arraymi.captcha.model.Address;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.ICustomerService;

@RestController
@CrossOrigin(origins = "${allowed.origins}")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@PostMapping("${app.register.uri}")
	public ResponseEntity<?> registerUser(@RequestBody RegistrationForm customerDetails) {
		if (null != customerDetails && !customerDetails.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide mandatory details!!");
		}
		customerService.regsiterUser(customerDetails);
		return ResponseEntity.ok(new Response("User Registered!"));
	}

	@GetMapping("/verify-email/{userId}/{verificationCode}")
	public ResponseEntity<?> verifyUserEmail(@PathVariable long userId, @PathVariable String verificationCode) {
		if (userId == 0 || Strings.isBlank(verificationCode)) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid verification code!!");
		}
		boolean isVerified = customerService.verifyEmailID(userId, verificationCode);
		return ResponseEntity.ok(new Response(isVerified? "Email verified successfully!": "Unable to verify!"));
	}

	@GetMapping("/get-user-profile")
	public ResponseEntity<?> getUserProfile() {
		return ResponseEntity.ok(customerService.getUserDetails());
	}

	@PostMapping("/update-user-profile")
	public ResponseEntity<?> updateUserProfile(@RequestBody CustomerProfile profileDetails) {
		if (null != profileDetails && !profileDetails.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid details!!");
		}
		customerService.updateUserDetails(profileDetails);
		return ResponseEntity.ok(new Response("Profile updated successfully!"));
	}

	@PostMapping("/add-new-address")
	public ResponseEntity<?> addNewAddress(@RequestBody Address address) {
		if (null != address && !address.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid details!!");
		}
		customerService.addAddress(address);
		return ResponseEntity.ok(new Response("Address added successfully!"));
	}

	@DeleteMapping("/remove-address")
	public ResponseEntity<?> removeAddress(@RequestParam long addressId) {
		if (0 == addressId) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid addressId!!");
		}
		customerService.removeAddress(addressId);
		return ResponseEntity.ok(new Response("Address removed successfully!"));
	}

	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm passwordForm) {
		if (null == passwordForm || !passwordForm.isValid(true)) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid password!!");
		}
		customerService.changePassword(passwordForm);
		return ResponseEntity.ok(new Response("Password changed successfully!"));
	}

	@PostMapping("${forget.password.uri}")
	public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordForm passwordForm) {
		if (null == passwordForm || !passwordForm.isValid()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid details!!");
		}
		customerService.forgetPassword(passwordForm);
		return ResponseEntity.ok(new Response("New password sent to registered email address successfully!"));
	}

}
