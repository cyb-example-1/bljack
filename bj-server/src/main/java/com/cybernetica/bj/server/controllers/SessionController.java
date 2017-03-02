package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * Session controller.
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	@RequestMapping(value="/login",produces = "application/json", consumes="application/json")
	@ResponseBody
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		return new LoginResponseDTO();
	}

}
