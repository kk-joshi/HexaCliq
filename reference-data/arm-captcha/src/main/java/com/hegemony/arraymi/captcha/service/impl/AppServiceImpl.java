package com.hegemony.arraymi.captcha.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.dao.IQueryRequestDao;
import com.hegemony.arraymi.captcha.model.QueryRequest;
import com.hegemony.arraymi.captcha.service.IAppService;
import com.hegemony.arraymi.captcha.util.DateUtil;

@Service
public class AppServiceImpl extends BaseService implements IAppService {

	@Autowired
	private IQueryRequestDao queryDao;

	@Override
	public QueryRequest customerQuery(QueryRequest queryRequest) {
		queryRequest.setReferenceNumber(DateUtil.getDate(new Date()) + "-" + System.currentTimeMillis());
		queryRequest = queryDao.save(queryRequest);
		emailService.sendQueryConfirmationEmail(queryRequest);
		return queryRequest;
	}

}
