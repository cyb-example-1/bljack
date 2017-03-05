package com.cybernetica.bj.client.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.client.services.UserService;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private RestService restService;
	
	public UserServiceImpl(){	
		setRestService(RestService.get());
	}
	
	protected void setRestService(RestService restService) {
		this.restService = restService;
	} 	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO requestUserData() throws ClientException {
		logger.trace("retrieving user data");
		UserResponseDTO resp = restService.get("/user/get", UserResponseDTO.class);
		validate(resp);
		EventProducer.publishEvent(new UserDataEvent(resp));
		return resp;
	}

	
	protected void validate(UserResponseDTO resp) throws ClientException {
		super.validate(resp);
		if(resp.getUser()==null)
			throw new ClientException("null response");
	}

}
