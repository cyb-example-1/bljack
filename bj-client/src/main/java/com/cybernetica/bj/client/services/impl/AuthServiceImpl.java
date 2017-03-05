package com.cybernetica.bj.client.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.LogoutEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.common.dto.LogoutRequestDTO;
import com.cybernetica.bj.common.dto.LogoutResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Authentication service.
 * statefull singleton
 * @author dmitri
 *
 */
public class AuthServiceImpl extends BaseServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	private RestService restService;
	
	public AuthServiceImpl(){	
		setRestService(RestService.get());
	}
	
	protected void setRestService(RestService restService) {
		this.restService = restService;
	} 
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LoginResponseDTO login(String username, String password) throws ClientException{
		logger.trace("starting logging for "+username);
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);
		validate(dto);

		LoginResponseDTO resp = restService.post("/session/login", dto, LoginResponseDTO.class);
		validate(resp);
		EventProducer.publishEvent(new LoginEvent(resp));
		return resp;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogoutResponseDTO logout() throws ClientException {
		logger.trace("starting logging");
		LogoutRequestDTO dto = new LogoutRequestDTO();
//		validate(dto);
		LogoutResponseDTO resp = restService.post("/session/logout", dto, LogoutResponseDTO.class);
		validate(resp);
		EventProducer.publishEvent(new LogoutEvent(resp));
		return resp;
	}

	/**
	 * login request dto validation
	 * @param dto
	 * @throws ClientException
	 */
	private void validate(LoginRequestDTO dto) throws ClientException{
		if(dto.getUsername()==null || dto.getPassword()==null)
			throw new ClientException("error.login-dto.invalid");
		if(dto.getUsername().isEmpty() || dto.getUsername().length()>20)
			throw new ClientException("error.login-dto.invalid");
		if(dto.getPassword().isEmpty() || dto.getPassword().length()>20)
			throw new ClientException("error.login-dto.invalid");
	}
}
