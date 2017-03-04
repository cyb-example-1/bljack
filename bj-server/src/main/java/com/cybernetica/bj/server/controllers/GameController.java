package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.game.GameStartResponseDTO;

/**
 * Session controller.
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/game")
public class GameController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	
	@RequestMapping(value="/start",produces = "application/json", consumes="application/json")
	@ResponseBody
	public GameStartResponseDTO gameStart() {
		return null;
	}

}
