package com.cybernetica.bj.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.BaseRestResponseDTO;

public final class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		logger.info("Authentication failed with message: {}", e.getMessage());
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication failed.");
		BaseRestResponseDTO dto=new BaseRestResponseDTO();
		dto.addError("error.login.failed");
		response.getWriter().println(JsonUtils.toString(dto));
		
	}
}
