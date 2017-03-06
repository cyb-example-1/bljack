package com.cybernetica.bj.client.services.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.UserService;
import com.cybernetica.bj.common.dto.user.BalanceChangeDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

public class UserServiceImpl extends BaseRestServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO requestUserData() throws ClientException {
		logger.trace("retrieving user data");
		UserResponseDTO resp = getRestService().get("/user/get", UserResponseDTO.class);
		validate(resp);
		EventProducer.publishEvent(new UserDataEvent(resp));
		return resp;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO updateBalance(BigDecimal augment) throws ClientException {
		logger.info("updating user balance by {}",augment);
		BalanceChangeDTO requestDTO = new BalanceChangeDTO();
		requestDTO.setBalanceChange(augment);
		UserResponseDTO resp = getRestService().post("/user/balance", requestDTO,UserResponseDTO.class);
		validate(resp);
		EventProducer.publishEvent(new UserDataEvent(resp));
		return resp;
	}

	
	protected void validate(UserResponseDTO resp) throws ClientException {
		super.validate(resp);
		if(resp.getUser()==null)
			throw new ClientException("null response");
		if(resp.getUser().getBalance()==null)
			resp.getUser().setBalance(BigDecimal.ZERO);
	}



}
