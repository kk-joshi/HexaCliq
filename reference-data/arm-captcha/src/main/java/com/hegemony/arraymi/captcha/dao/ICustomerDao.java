package com.hegemony.arraymi.captcha.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.Customer;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long> {

	Optional<Customer> findByUsername(String username);

	boolean existsByEmailId(String emailId);

	boolean existsByUsernameOrEmailId(String username, String emailId);

	Optional<Customer> findByUsernameOrEmailId(String username, String emailId);

	@Query(value = "FROM Customer c where FORMATDATETIME(c.dob, '%M-%d') = FORMATDATETIME(NOW(), '%M-%d')")
	List<Customer> findAllByDob();

}
