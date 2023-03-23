package com.hegemony.arraymi.captcha.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.Wallet;

@Repository
public interface IWalletDao extends JpaRepository<Wallet, Long> {

	Optional<Wallet> findByUserId(long userId);

}
