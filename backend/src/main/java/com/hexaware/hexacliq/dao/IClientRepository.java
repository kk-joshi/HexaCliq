package com.hexaware.hexacliq.dao;

import com.hexaware.hexacliq.dto.Client;
import com.hexaware.hexacliq.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {
}
