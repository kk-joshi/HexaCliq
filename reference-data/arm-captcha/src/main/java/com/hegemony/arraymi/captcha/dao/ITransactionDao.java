package com.hegemony.arraymi.captcha.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.Transaction;

@Repository
public interface ITransactionDao extends JpaRepository<Transaction, Long> {

	List<Transaction> findByUserId(long userId);

}
