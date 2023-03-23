package com.hegemony.arraymi.captcha.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hegemony.arraymi.captcha.model.QueryRequest;

@Repository
public interface IQueryRequestDao extends JpaRepository<QueryRequest, Long> {

}
