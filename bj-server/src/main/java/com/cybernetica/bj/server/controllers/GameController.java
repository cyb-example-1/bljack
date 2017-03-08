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
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.enums.GameStatus;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.models.User;
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
	
	@RequestMapping(value="/begin/{id}",produces = "application/json")
	@ResponseBody
	public UserResponseDTO gameBegin(@PathVariable(name="id") Long id) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("Game #{} beginning for User #{}",id,userId);
		User user = gameService.beginGame(userId,id);
		return new UserResponseDTO(UserController.map(user));
	}
	
	@RequestMapping(value="/quit/{id}",produces = "application/json")
	@ResponseBody
	public UserResponseDTO gameQuit(@PathVariable(name="id") Long id) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("Game #{} quit for User #{}",id,userId);
		User user = gameService.quitGame(userId,id);
		return new UserResponseDTO(UserController.map(user));
	}	
	
	
	@RequestMapping(value="/take/{id}",produces = "application/json")
	@ResponseBody
	public UserResponseDTO takeCard(@PathVariable(name="id") Long id) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("Game #{} for User #{} take card",id,userId);
		User user = gameService.takeCard(userId,id);
		return new UserResponseDTO(UserController.map(user));
	}
	
	@RequestMapping(value="/finish/{id}",produces = "application/json")
	@ResponseBody
	public UserResponseDTO finishGame(@PathVariable(name="id") Long id) throws ServiceException {
		Long userId = getLoggedUserId();
		logger.trace("Game #{} for User #{} finish",id,userId);
		User user = gameService.finishGame(userId,id);
		return new UserResponseDTO(UserController.map(user));
	}	
	
	
	static GameDTO map(Game game) {
		GameDTO ret=new GameDTO();
		ret.setStatus(GameStatus.valueOf(game.getStatus()));
		ret.setCurrentBet(game.getCurrentBet());
		ret.setId(game.getId());
		ret.setUserCards(game.getUserCards());
		if(game.getStatus()==GameStatus.GAME_OVER.getValue()) {//show all dealer cards
			ret.setDealerCards(game.getDealerCardOpened()|game.getDealerCardClosed());
			if(game.getWinType()==null)
				ret.setWinType(1);
			else
				ret.setWinType(game.getWinType());
		}
		else
			ret.setDealerCards(game.getDealerCardOpened());
		
		return ret;
	}

}
