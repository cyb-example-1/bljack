package com.cybernetica.bj.client.services;

import javax.validation.ValidationException;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;

/**
 * Authentication service
 * @author dmitri
 *
 */
public class AuthService {
	private static AuthService instance;
	
	private AuthService(){	
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

	public void login(String username, String password) throws ValidationException{
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);
		validate(dto);
		// TODO Auto-generated method stub
		
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
