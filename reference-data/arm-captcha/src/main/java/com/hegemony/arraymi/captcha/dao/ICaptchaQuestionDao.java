package com.hegemony.arraymi.captcha.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.CaptchaQuestion;

@Repository
public interface ICaptchaQuestionDao extends JpaRepository<CaptchaQuestion, Long> {

	List<CaptchaQuestion> findByUserIdAndCreatedOn(long userId, String date);

	boolean existsByUserIdAndCreatedOn(long userId, String date);

}
