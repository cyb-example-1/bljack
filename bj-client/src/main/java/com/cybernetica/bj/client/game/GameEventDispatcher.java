package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.LogoutEvent;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.scene.WelcomeSceneController;
import com.cybernetica.bj.client.services.UserService;
import com.cybernetica.bj.client.utils.Manager;


/**
 * Dispatches game events
 * @author dmitri
 *
 */
public class GameEventDispatcher extends GameEventAdapter {
	private static final Logger logger = LoggerFactory.getLogger(GameEventAdapter.class);


	@Override
	public void onUserData(UserDataEvent event) {
		//TODO check game presence
		GameSession.get().setUser(event.getResponse().getUser());
		
	}

	@Override
	public void onLogin(LoginEvent event){
		//re-aquire user
		try {
			UserService.get().requestUserData();
		} catch (ClientException e) {
			logger.error(e.getMessage());
			return;
		}
		try {
			Manager.switchTo(WelcomeSceneController.class);
		} catch (Exception e) {
			logger.error("error on event "+event.toString(),e);
			return;
		}
	}
	
	@Override
	public void onLogout(LogoutEvent event){
		GameSession.get().setUser(null);
		try {
			Manager.switchTo(LoginSceneController.class);
		} catch (Exception e) {
			logger.error("error on event "+event.toString(),e);
			return;
		}
	}
}
