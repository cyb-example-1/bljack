package com.cybernetica.bj.client.services;

import java.math.BigDecimal;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

public interface GameService extends Singleton<UserService> {

	/**
	 * Singleton initializer
	 * 
	 * @return
	 */
	public static GameService get() {
		return Singleton.getSingleton(GameService.class);
	}

	/**
	 * Gate start action
	 * 
	 * @return
	 * @throws ClientException
	 */
	public GameResponseDTO gameStart() throws ClientException;

	/**
	 * Cancel game while betting
	 */
	public RestResponseDTO cancelBet(Long gameId) throws ClientException;

	/**
	 * Updates bet with provided augment
	 * 
	 * @param gameId
	 * @param bigDecimal
	 * @return
	 * @throws ClientException
	 */
	public GameResponseDTO betGame(Long gameId, BigDecimal bigDecimal) throws ClientException;

	/**
	 * Begins game
	 * 
	 * @param gameId
	 * @return
	 * @throws ClientException
	 */
	public UserResponseDTO beginGame(Long gameId) throws ClientException;

	/**
	 * Quits game, losing money
	 * 
	 * @param gameId
	 * @return
	 * @throws ClientException
	 */
	public UserResponseDTO quitGame(Long gameId) throws ClientException;

	/**
	 * Take card
	 * @param gameId
	 * @return
	 * @throws ClientException
	 */
	public UserResponseDTO takeCard(Long gameId) throws ClientException;

	/**
	 * Completes the game
	 * @param gameId
	 * @return
	 * @throws ClientException
	 */
	public UserResponseDTO finishGame(Long gameId) throws ClientException;

}
