package com.cybernetica.bj.client.game;

import com.cybernetica.bj.common.dto.login.LoginResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

public class GameSession  implements Singleton<GameSession>{
	
	private LoginResponseDTO user;
	
	/**
	 * Singleton initializer
	 * @return
	 */
	static GameSession get() {
		return Singleton.getSingleton(GameSession.class);
	}

	public LoginResponseDTO getUser() {
		return user;
	}

	public void setUser(LoginResponseDTO user) {
		this.user = user;
	}
	
	

}
