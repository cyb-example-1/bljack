package com.cybernetica.bj.server.services;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;

public interface GameService {

	/**
	 * Registers new game
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	Game createGame(Long userId) throws ServiceException;

	/**
	 * Cancels current betting stage. No money loss
	 * @param userId
	 * @param gameId
	 * @throws ServiceException
	 */
	void cancelGame(Long userId, Long gameId) throws ServiceException;

}
