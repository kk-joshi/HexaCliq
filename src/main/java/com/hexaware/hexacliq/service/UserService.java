package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IUserRepository;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<UserDto> getUserByName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.map(this::toDto);
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    private User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(user, userDto);
        return user;
    }
}
