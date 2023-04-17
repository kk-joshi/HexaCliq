package com.hexaware.hexacliq.service;

import com.hexaware.hexacliq.dao.IUserRepository;
import com.hexaware.hexacliq.dao.IRoleRepository;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.dto.UserDto;
import com.hexaware.hexacliq.dto.UserRole;
import com.hexaware.hexacliq.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Transactional
    public User saveUser(UserDto user) {
        return userRepository.save(toEntity(user));
    }

    public Optional<UserDto> getUserByName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.map(this::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usr = userRepository.findByUserName(username);
        return usr.orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User creatUser(User user, Set<UserRole> userRoles) {

        userRoles.forEach(rl -> {
            roleRepository.save(rl.getRole());
        });

        user.getUserRoles().addAll(userRoles);

        return userRepository.save(user);
    }

    public boolean deleteUserByUserId(Integer userId) {
        userRepository.deleteById(userId);
        return true;
    }

    public User getUserByUserId(Integer userId) {
        return userRepository.findById(userId).get();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsersNormal() {
        List<User> list = userRepository.findAll().stream().filter(user -> user.getProfile().equalsIgnoreCase(Constants.NORMAL)).toList();
        list.forEach(u -> u.setImageData(null));
        return list;
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
