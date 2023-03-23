package com.hegemony.arraymi.captcha.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.dao.ICustomerDao;
import com.hegemony.arraymi.captcha.model.Customer;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private ICustomerDao customerDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> findFirst = customerDao.findByUsernameOrEmailId(username, username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return new JwtUserDetails(findFirst.get());
	}

}
