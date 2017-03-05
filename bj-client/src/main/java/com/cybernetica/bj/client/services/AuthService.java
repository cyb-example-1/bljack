package com.cybernetica.bj.client.services;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.LogoutResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Authentication service. statefull singleton
 * 
 * @author dmitri
 *
 */
public interface AuthService extends Singleton<AuthService> {

	/**
	 * Singleton initializer
	 * 
	 * @return
	 */
	static AuthService get() {
		return Singleton.getSingleton(AuthService.class);
	}

	/**
	 * Login method
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ClientException
	 */
	LoginResponseDTO login(String username, String password) throws ClientException;

	/**
	 * Logout method
	 * 
	 * @return
	 * @throws ClientException
	 */
	LogoutResponseDTO logout() throws ClientException;

}
