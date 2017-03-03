package com.cybernetica.bj.client.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Authentication service.
 * statefull singleton
 * @author dmitri
 *
 */
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	private RestService restService;
	
	public AuthServiceImpl(){	
		setRestService(RestService.get());
	}
	
	protected void setRestService(RestService restService) {
		this.restService = restService;
	} 
	
	public LoginResponseDTO login(String username, String password) throws ClientException{
		logger.trace("starting logging for "+username);
		LoginRequestDTO dto = new LoginRequestDTO();
		dto.setPassword(password);
		dto.setUsername(username);
		validate(dto);

		LoginResponseDTO resp = restService.post("/session/login", dto, LoginResponseDTO.class);
		validate(resp);
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
	
	private void validate(LoginResponseDTO resp) throws ClientException {
		if(resp==null)
			throw new ClientException("null response");
		if(resp.getErrors()!=null || resp.getErrors().size()>0) {
			for(String error:resp.getErrors())
				logger.debug("received {}",error);
			throw new ClientException(resp.getErrors().get(0));
		}
			
	}
}
