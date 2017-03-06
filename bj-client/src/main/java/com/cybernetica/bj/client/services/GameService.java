package com.cybernetica.bj.client.services;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.game.GameResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

public interface GameService extends Singleton<UserService>{

	/**
	 * Singleton initializer
	 * @return
	 */	
	public static GameService get() {
		return Singleton.getSingleton(GameService.class);
	}

	/**
	 * Gate start action
	 * @return
	 * @throws ClientException
	 */
	public GameResponseDTO gameStart() throws ClientException;

	/**
	 * Cancel game while betting
	 */
	public RestResponseDTO cancelBet(Long gameId) throws ClientException;

}
	