package com.cybernetica.bj.server.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameBetChangeDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.user.GameDTO;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.services.GameService;

/**
 * Session controller.
 * @author dmitri
 *
 */
@RestController
@RequestMapping("/game")
public class GameController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;

	
	@RequestMapping(value="/start",produces = "application/json")
	@ResponseBody
	public GameResponseDTO gameStart() throws ServiceException {
		logger.trace("Game start");
		Long userId = getLoggedUserId();
		Game game = gameService.createGame(userId);
		return new GameResponseDTO(map(game));
	}
	
	@RequestMapping(value="/cancel/{id}",produces = "application/json")
	@ResponseBody
	public RestResponseDTO gameCancel(@PathVariable(name="id") Long id) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("Game #{} cancelation for User #{}",id,userId);
		gameService.cancelGame(userId,id);
		return new RestResponseDTO();
	}
	
	@RequestMapping(value="/bet",produces = "application/json", consumes = "application/json")
	@ResponseBody
	public GameResponseDTO gameBet(@RequestBody GameBetChangeDTO betDTO) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("User #{} bets with {}",userId,betDTO);
		
		Game game = gameService.betGame(userId,betDTO.getGameId(),betDTO.getBet());
		return new GameResponseDTO(map(game));
	}		
	
	
	static GameDTO map(Game game) {
		GameDTO ret=new GameDTO();
		ret.setBetDone(game.isBetDone());
		ret.setCurrentBet(game.getCurrentBet());
		ret.setId(game.getId());
		return ret;
	}

}
