package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.server.security.SecurityUtils;

/**
 * Manages user data
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/user")
@Secured("USER")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@RequestMapping(value="/get",produces = "application/json", consumes="application/json")
	@ResponseBody
	public UserResponseDTO get() {
		logger.trace("User read");
		SecurityUtils.getLoggedUserName();
		return new UserResponseDTO();
	}

}
