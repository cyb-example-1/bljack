package com.cybernetica.bj.server.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.security.SecurityUtils;
import com.cybernetica.bj.server.services.UserService;

/**
 * Base REST controller class
 * 
 * @author dmitri
 *
 */
public abstract class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private UserService userService;

	@ExceptionHandler
	public RestResponseDTO serviceException(ServiceException serexc, HttpServletResponse response) {
		try {
			response.sendError(400, serexc.getMessage());
		} catch (IOException e) {

		}
		RestResponseDTO responseDTO = new RestResponseDTO();
		responseDTO.addError(serexc.getMessage());
		return responseDTO;
	}
	
	
	public Long getLoggedUserId() throws ServiceException {
		String userName = SecurityUtils.getLoggedUserName();
		if(userName==null)
			return null;
		User user = userService.findByUsername(userName);
		if(user!=null)
			return user.getId();
		return null;
	}

}
