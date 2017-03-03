package com.cybernetica.bj.client.services;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Authentication service.
 * statefull singleton
 * @author dmitri
 *
 */
public interface AuthService  extends Singleton<AuthService>{

	LoginResponseDTO login(String string, String string2) throws ClientException;

	/**
	 * Singleton initializer
	 * @return
	 */
	static AuthService get() {
		return Singleton.getSingleton(AuthService.class);
	}

}
