package com.hegemony.arraymi.captcha.service;

import com.hegemony.arraymi.captcha.model.QueryRequest;

public interface IAppService {

	QueryRequest customerQuery(QueryRequest queryRequest);
	
}
