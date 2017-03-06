package com.cybernetica.bj.server.services;

import java.math.BigDecimal;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.Game;
import com.cybernetica.bj.server.models.User;

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


	/**
	 * Augments bet by specified amount
	 * @param userId
	 * @param gameId
	 * @param betAugment
	 * @return
	 * @throws ServiceException
	 */
	Game betGame(Long userId, Long gameId, BigDecimal betAugment) throws ServiceException;

	/**
	 * Finishes betting and begins blackjack
	 * @param userId
	 * @param id
	 * @return
	 */
	User beginGame(Long userId, Long gameId) throws ServiceException;

}
