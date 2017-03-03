package com.cybernetica.bj.client.services.impl;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Authentication service.
 * statefull singleton
 * @author dmitri
 *
 */
public class AuthServiceImpl implements AuthService{
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	private RestService restService;
	
	public AuthServiceImpl(){	
		restService=RestService.get();
	}
	
	public LoginResponseDTO login(String username, String password) throws ClientException{
		logger.trace("starting logging for "+username);
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);
		validate(dto);

		LoginResponseDTO resp = restService.post("/session/login", dto, LoginResponseDTO.class);
		return resp;
		
	}

	/**
	 * login dto validation
	 * @param dto
	 * @throws ValidationException
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
