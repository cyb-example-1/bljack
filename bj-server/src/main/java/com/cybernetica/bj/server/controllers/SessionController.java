package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.LogoutRequestDTO;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.services.SessionService;

/**
 * Session controller.
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="/login",produces = "application/json", consumes="application/json",method=RequestMethod.POST)
	@ResponseBody
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		return new LoginResponseDTO();
	}
	
	
	@RequestMapping(value="/status",produces = "application/json", consumes="application/json",method=RequestMethod.GET)
	@ResponseBody
	public String status() throws ServiceException {
		sessionService.findByUsername("asd");
		return "{\"version\":\"0.1.0\"}";
	}


	@RequestMapping(value="/logout",produces = "application/json", consumes="application/json",method=RequestMethod.POST)
	@ResponseBody
	public RestResponseDTO logout(@RequestBody LogoutRequestDTO dto) throws ServiceException {
		logger.trace("loggout action for  {]",dto);
		sessionService.deleteAllSessions(dto.getUsername(),dto.getSessionId());

		SecurityContextHolder.clearContext();
		return new RestResponseDTO();
	}
	

}
