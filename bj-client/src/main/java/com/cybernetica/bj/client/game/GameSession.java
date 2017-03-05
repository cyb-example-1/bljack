package com.cybernetica.bj.client.game;

import com.cybernetica.bj.common.dto.user.UserResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * user's session object
 * @author dmitri
 *
 */
public class GameSession  implements Singleton<GameSession>{
	
	private UserResponseDTO user;
	
	/**
	 * Singleton initializer
	 * @return
	 */
	public static GameSession get() {
		return Singleton.getSingleton(GameSession.class);
	}

	public UserResponseDTO getUser() {
		return user;
	}

	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
	
	

}
