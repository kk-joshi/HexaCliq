package com.hegemony.arraymi.captcha.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.CustomerDetails;

@Repository
public interface ICustomerDetailsDao extends JpaRepository<CustomerDetails, Long> {

	List<CustomerDetails> findByUserId(long userId);

	boolean existsByUserIdAndActive(long customerId, boolean active);

	Optional<CustomerDetails> findByUserIdAndActive(long customerId, boolean active);

}
