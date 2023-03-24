package com.hexaware.hexacliq.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.hexacliq.dao.IUserRepository;
import com.hexaware.hexacliq.dto.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> findFirst = userRepository.findByUserName(username);

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER NOT FOUND '%s'.", username));
		}

		return new JwtUserDetails(findFirst.get());
	}

}
