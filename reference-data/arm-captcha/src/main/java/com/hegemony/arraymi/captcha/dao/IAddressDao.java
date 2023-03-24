package com.hegemony.arraymi.captcha.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.Address;

@Repository
public interface IAddressDao extends JpaRepository<Address, Long> {

	List<Address> findByUserId(long userId);

	Optional<Address> findByIdAndActive(long id, boolean active);

	Optional<Address> findByUserIdAndPermanentAndActive(long userId, boolean permanent, boolean active);

	List<Address> findByUserIdAndActive(long userId, boolean active);

}
