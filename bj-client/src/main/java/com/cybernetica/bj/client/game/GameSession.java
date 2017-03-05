package com.cybernetica.bj.client.game;

import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * user's session object
 * @author dmitri
 *
 */
public class GameSession  implements Singleton<GameSession>{
	
	private UserDTO user;
	
	/**
	 * Singleton initializer
	 * @return
	 */
	public static GameSession get() {
		return Singleton.getSingleton(GameSession.class);
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	

}
