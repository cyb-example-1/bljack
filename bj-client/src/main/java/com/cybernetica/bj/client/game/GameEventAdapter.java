package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.events.BaseEvent;
import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.LogoutEvent;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.interfaces.EventListener;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.scene.WelcomeSceneController;
import com.cybernetica.bj.client.utils.Manager;

public class GameEventAdapter implements EventListener<BaseEvent>{
	private static final Logger logger = LoggerFactory.getLogger(GameEventAdapter.class);

	/**
	 * handles onLogin event
	 */
	@Override
	public void onEvent(BaseEvent event) {
		logger.trace("got {} event", event);
		switch(event.getClass().getSimpleName()){
		case "LoginEvent":
			onLogin((LoginEvent) event);
			break;
		case "LogoutEvent":
			onLogout((LogoutEvent) event);
			break;
		case "UserDataEvent":
			onUserData((UserDataEvent)event);
			break;
		}

	}

	private void onUserData(UserDataEvent event) {
		//TODO check game presence
		GameSession.get().setUser(event.getResponse().getUser());
		
	}

	protected void onLogin(LoginEvent event){
		try {
			Manager.switchTo(WelcomeSceneController.class);
		} catch (Exception e) {
			logger.error("error on event "+event.toString(),e);
			return;
		}
	}
	
	protected void onLogout(LogoutEvent event){
		GameSession.get().setUser(null);
		try {
			Manager.switchTo(LoginSceneController.class);
		} catch (Exception e) {
			logger.error("error on event "+event.toString(),e);
			return;
		}
	}

}
