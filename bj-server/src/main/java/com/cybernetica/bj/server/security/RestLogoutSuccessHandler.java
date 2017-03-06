package com.cybernetica.bj.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.cybernetica.bj.common.JsonUtils;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.LogoutRequestDTO;
import com.cybernetica.bj.server.controllers.SessionController;

public final class RestLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestLogoutSuccessHandler.class);
	private SessionController sessionController;

    public void setSessionController(SessionController controller){
    	this.sessionController=controller;
    }

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		LogoutRequestDTO dto = new LogoutRequestDTO();
		if(authentication!=null)
			dto.setUsername(authentication.getName());
		else
			dto.setSessionId(RestAuthenticationProcessingFilter.getAuthToken(request, response));
		logger.debug("Logging out {}",dto);
		
		RestResponseDTO responseDTO = sessionController.logout(dto);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().print(JsonUtils.toString(responseDTO));
		
	}
}
