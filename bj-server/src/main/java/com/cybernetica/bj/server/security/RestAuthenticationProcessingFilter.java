package com.cybernetica.bj.server.security;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationProcessingFilter.class);
	
	private static ObjectMapper mapper = new ObjectMapper();


	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	public RestAuthenticationProcessingFilter() {
		super("/session/login");
	}
	

	@Override
	public void afterPropertiesSet() {
		setAuthenticationManager(authenticationManager);
		ProviderManager manager = (ProviderManager) authenticationManager;
		DaoAuthenticationProvider authenticationProvider = (DaoAuthenticationProvider) manager.getProviders().get(0);
		authenticationProvider.setHideUserNotFoundExceptions(false);
		setAuthenticationSuccessHandler(authenticationSuccessHandler);
		super.afterPropertiesSet();
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		String body;
		try {
			body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			logger.error("error.request.read",e);
			throw new AuthenticationServiceException(
					"Authentication failed: " + request.getMethod(),e);
		}
		
		logger.trace("Authenticating with {}",body);
		LoginRequestDTO data=mapper.readValue(body, LoginRequestDTO.class);

		String username = data.getUsername();
		String password = data.getPassword();

		
		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
		try {
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch(UsernameNotFoundException notFoundEx){
			try {
				userService.create(username, password);
			} catch (ServiceException e) {
				logger.error("error.user.create.failed {}",username);
				throw new AuthenticationServiceException(
						"Authentication failed: " + request.getMethod(),e);
			}
			
			
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}


	private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		
	}
}
