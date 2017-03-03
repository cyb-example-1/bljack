package com.cybernetica.bj.server.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.server.config.Application;
import com.cybernetica.bj.server.controllers.SessionController;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;
import com.cybernetica.bj.server.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);
    private static ObjectMapper mapper = new ObjectMapper();
	private SessionController sessionController;

    public void setSessionController(SessionController controller){
    	this.sessionController=controller;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	logger.debug("Authentication was successful");
    	LoginRequestDTO loginDTO=new LoginRequestDTO();
    	loginDTO.setUsername(authentication.getName());
		LoginResponseDTO responseDTO = sessionController.login(loginDTO);
		
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
		response.getWriter().print(mapper.writeValueAsString(responseDTO));
		String sessionId=details.getSessionId();
		if(sessionId==null){
			//mock context. load from table
			SessionService sessionService = Application.getContext().getBean(SessionService.class);
			SpringSession session=null;
			try {
				session = sessionService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			} catch (ServiceException e) {
				logger.error("error.filter.security.find",e);
				throw new ServletException("error.filter.security.find");
			}
			sessionId=session.getId();
		}
		logger.debug("Setting X-Auth-Token to {}",sessionId);
		response.setHeader("X-Auth-Token", sessionId);
    }
}
