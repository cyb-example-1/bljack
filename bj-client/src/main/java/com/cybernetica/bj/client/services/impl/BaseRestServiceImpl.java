package com.cybernetica.bj.client.services.impl;

import com.cybernetica.bj.client.services.RestService;

public class BaseRestServiceImpl extends BaseServiceImpl {

	private RestService restService;
	
	public BaseRestServiceImpl(){	
		setRestService(RestService.get());
	}
	
	public void setRestService(RestService restService) {
		this.restService = restService;
	}

	public RestService getRestService() {
		return restService;
	} 
	
	
}
