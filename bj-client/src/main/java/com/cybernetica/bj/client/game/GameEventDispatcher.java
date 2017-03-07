package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.LogoutEvent;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.scene.BetSceneController;
import com.cybernetica.bj.client.scene.BlackjackSceneController;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.scene.WelcomeSceneController;
import com.cybernetica.bj.client.services.UserService;
import com.cybernetica.bj.client.utils.Manager;
import com.cybernetica.bj.common.dto.user.UserDTO;
import com.cybernetica.bj.common.enums.GameStatus;

/**
 * Dispatches game events
 * 
 * @author dmitri
 *
 */
public class GameEventDispatcher extends GameEventAdapter {
	private static final Logger logger = LoggerFactory.getLogger(GameEventAdapter.class);

	@Override
	public void onUserData(UserDataEvent event) throws ClientException {
		logger.trace("Received user-data event");
		UserDTO user = event.getResponse().getUser();
		GameSession.get().setUser(user);
		if(user.getGame()!=null) {
			if(user.getGame().getStatus()!=GameStatus.BETTING)
				Manager.switchTo(BlackjackSceneController.class);
			else
				Manager.switchTo(BetSceneController.class);	
		}
		else
			Manager.switchTo(WelcomeSceneController.class);
	}

	@Override
	public void onLogin(LoginEvent event) throws ClientException {
		// re-aquire user
		UserService.get().requestUserData();
		//Manager.switchTo(WelcomeSceneController.class);
	}

	@Override
	public void onLogout(LogoutEvent event) throws ClientException {
		GameSession.get().setUser(null);
		Manager.switchTo(LoginSceneController.class);
	}
}
