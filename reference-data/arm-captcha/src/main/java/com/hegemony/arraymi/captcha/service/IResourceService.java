package com.hegemony.arraymi.captcha.service;

import org.springframework.web.multipart.MultipartFile;

import com.hegemony.arraymi.captcha.model.Resource;

public interface IResourceService {

	Resource getResource(long resourceId, String token);

	long uploadResource(MultipartFile file, String type);

}
