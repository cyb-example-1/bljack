package com.cybernetica.bj.client.services;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Authentication service.
 * statefull singleton
 * @author dmitri
 *
 */
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	private static AuthService instance;
	
	private RestService restService;
	
	private AuthService(){	
		restService=RestService.get();
	}
	
	/**
	 * gets singleton
	 * @return
	 */
	public static AuthService get(){
		if(instance!=null)
			return instance;
		instance=new AuthService();
		return instance;	
	}

	public LoginResponseDTO login(String username, String password) throws ValidationException{
		logger.trace("starting logging for "+username);
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);
		validate(dto);
		// TODO Auto-generated method stub'
		
		return null;
		
	}

	/**
	 * login dto validation
	 * @param dto
	 * @throws ValidationException
	 */
	private void validate(LoginRequestDTO dto) throws ValidationException{
		if(dto.getUsername()==null || dto.getPassword()==null)
			throw new ValidationException("error.login-dto.invalid");
		if(dto.getUsername().isEmpty() || dto.getUsername().length()>20)
			throw new ValidationException("error.login-dto.invalid");
		if(dto.getPassword().isEmpty() || dto.getPassword().length()>20)
			throw new ValidationException("error.login-dto.invalid");
	}
}
