package com.cybernetica.bj.client.services.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.GameService;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameBetChangeDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

/**
 * Game services
 * @author dmitri
 *
 */
public class GameServiceImpl extends BaseRestServiceImpl  implements GameService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameResponseDTO gameStart() throws ClientException {
		logger.debug("Starting game for user: #{}",GameSession.get().getUser().getId());
		GameResponseDTO resp = getRestService().get("/game/start", GameResponseDTO.class);
		validate(resp);
		
		GameSession.get().getUser().setGame(resp.getObject());
		UserDataEvent data =  new UserDataEvent(GameSession.get().getUser());
		
		EventProducer.publishEvent(data);
		return resp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RestResponseDTO cancelBet(Long gameId) throws ClientException {
		logger.debug("Canceling game #{} for user: #{}",gameId,GameSession.get().getUser().getId());
		RestResponseDTO resp = getRestService().get("/game/cancel/"+gameId, RestResponseDTO.class);
		validate(resp);
		
		GameSession.get().getUser().setGame(null);
		UserDataEvent data =  new UserDataEvent(GameSession.get().getUser());
		
		EventProducer.publishEvent(data);
		return resp;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameResponseDTO betGame(Long gameId, BigDecimal bigDecimal) throws ClientException {
		logger.debug("Betting game #{} for user: #{} ",gameId,GameSession.get().getUser().getId());
		GameBetChangeDTO requestDTO = new GameBetChangeDTO();
		requestDTO.setGameId(gameId);
		requestDTO.setBet(bigDecimal);
		
		GameResponseDTO resp = getRestService().post("/game/bet",requestDTO, GameResponseDTO.class);
		validate(resp);
		
		GameSession.get().getUser().setGame(resp.getObject());
		UserDataEvent data =  new UserDataEvent(GameSession.get().getUser());
		
		EventProducer.publishEvent(data);
		return resp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO beginGame(Long gameId) throws ClientException {
		logger.debug("Playing game #{} for user: #{}",gameId,GameSession.get().getUser().getId());
		
		UserResponseDTO resp = getRestService().get("/game/begin/"+gameId, UserResponseDTO.class);
		validate(resp);
		
		EventProducer.publishEvent(new UserDataEvent(resp.getUser()));
		return resp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO quitGame(Long gameId) throws ClientException {
		logger.debug("Quiting game #{} for user: #{}",gameId,GameSession.get().getUser().getId());
		
		UserResponseDTO resp = getRestService().get("/game/quit/"+gameId, UserResponseDTO.class);
		validate(resp);
		
		EventProducer.publishEvent(new UserDataEvent(resp.getUser()));
		return resp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserResponseDTO takeCard(Long gameId) throws ClientException {
		logger.debug("Take card game #{} for user: #{}",gameId,GameSession.get().getUser().getId());
		
		UserResponseDTO resp = getRestService().get("/game/take/"+gameId, UserResponseDTO.class);
		validate(resp);
		
		EventProducer.publishEvent(new UserDataEvent(resp.getUser()));
		return resp;
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public UserResponseDTO finishGame(Long gameId) throws ClientException {
		logger.debug("Complete game #{} for user: #{}",gameId,GameSession.get().getUser().getId());
		
		UserResponseDTO resp = getRestService().get("/game/finish/"+gameId, UserResponseDTO.class);
		validate(resp);
		
		EventProducer.publishEvent(new UserDataEvent(resp.getUser()));
		return resp;
	}

}
