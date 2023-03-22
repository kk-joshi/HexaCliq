package com.hexaware.hexacliq.dao;

import com.hexaware.hexacliq.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
