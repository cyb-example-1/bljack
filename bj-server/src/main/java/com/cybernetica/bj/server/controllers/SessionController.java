package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Session controller.
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

}
