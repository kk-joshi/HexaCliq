package com.hegemony.arraymi.captcha.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hegemony.arraymi.captcha.dao.ICustomerDetailsDao;
import com.hegemony.arraymi.captcha.dao.IResourceDao;
import com.hegemony.arraymi.captcha.jwt.JwtTokenUtil;
import com.hegemony.arraymi.captcha.model.ContentType;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;
import com.hegemony.arraymi.captcha.model.Resource;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.IResourceService;

@Service
public class ResourceServiceImpl extends BaseService implements IResourceService {

	@Autowired
	private IResourceDao resourceDao;

	@Autowired
	private ICustomerDetailsDao customerDetailsDao;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Resource getResource(long resourceId, String token) {
		if (Strings.isBlank(token) || jwtTokenUtil.isTokenExpired(token)) {
			throw new AppException(HttpStatus.UNAUTHORIZED, "Token expired!!");
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Customer> findFirst = customerDao.findByUsernameOrEmailId(username, username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}
		Customer user = findFirst.get();
		Optional<Resource> findById = resourceDao.findById(resourceId);
		if (!findById.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Image does not exists!!");
		}
		Resource image = findById.get();
		if (image == null || image.getUserId() != user.getId()) {
			throw new AppException(HttpStatus.UNAUTHORIZED, "You are not authorized!!");
		}
		return image;
	}

	@Override
	public long uploadResource(MultipartFile file, String type) {
		Customer loggedInUser = getLoggedInUser();
		Resource img;
		try {
			img = new Resource(loggedInUser.getId(), file.getBytes(), file.getContentType(),
					ContentType.getContentType(type));
		} catch (IOException e) {
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process given image");
		}
		img = resourceDao.save(img);
		Optional<CustomerDetails> customerDetails = customerDetailsDao.findByUserIdAndActive(loggedInUser.getId(),
				true);
		if (!customerDetails.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "User does not exists!!");
		}
		CustomerDetails customerDetail = customerDetails.get();
		customerDetail.setProfilePicResorceId(img.getId());
		customerDetailsDao.save(customerDetail);
		return img.getId();
	}
}
