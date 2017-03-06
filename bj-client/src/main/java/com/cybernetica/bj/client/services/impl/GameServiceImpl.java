package com.cybernetica.bj.client.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.GameService;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;

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
		logger.debug("Starting game for user: {}",GameSession.get().getUser().getId());
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
		logger.debug("Canceling game {} for user: ",gameId,GameSession.get().getUser().getId());
		RestResponseDTO resp = getRestService().get("/game/cancel/"+gameId, RestResponseDTO.class);
		validate(resp);
		
		GameSession.get().getUser().setGame(null);
		UserDataEvent data =  new UserDataEvent(GameSession.get().getUser());
		
		EventProducer.publishEvent(data);
		return resp;
		
	}

}
