package com.cybernetica.bj.client.services;

import java.math.BigDecimal;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

public interface UserService    extends Singleton<UserService>{

	/**
	 * Singleton initializer
	 * @return
	 */	
	public static UserService get() {
		return Singleton.getSingleton(UserService.class);
	}

	/**
	 * Request user data
	 * @return
	 * @throws ClientException
	 */
	public UserResponseDTO requestUserData() throws ClientException;

	/**
	 * Update balance using provided augment
	 * @param augment
	 */
	public UserResponseDTO updateBalance(BigDecimal augment) throws ClientException;
	
}
