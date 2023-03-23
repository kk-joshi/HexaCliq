package com.hegemony.arraymi.captcha.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hegemony.arraymi.captcha.dao.ICustomerDao;
import com.hegemony.arraymi.captcha.jwt.JwtUserDetails;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.IEmailService;

public class BaseService {

	@Autowired
	protected ICustomerDao customerDao;

	@Autowired
	protected IEmailService emailService;

	protected Customer getLoggedInUser() {
		Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
		JwtUserDetails details = (JwtUserDetails) authentication2.getPrincipal();
		Optional<Customer> user = customerDao.findById(details.getId());
		if (!user.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "You are not authorized, please login to proceed!!");
		}
		return user.get();
	}

}
