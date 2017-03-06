package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.events.BaseEvent;
import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.events.LogoutEvent;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.interfaces.EventListener;
import com.cybernetica.bj.client.interfaces.IDataListener;

/**
 * event adapter
 * @author dmitri
 *
 */
public class GameEventAdapter implements EventListener<BaseEvent>,IDataListener{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(GameEventAdapter.class);

	/**
	 * handles onLogin event
	 */
	@Override
	public void onEvent(BaseEvent event) throws ClientException {
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

	public void onUserData(UserDataEvent event) throws ClientException {		
	}

	public void onLogin(LoginEvent event) throws ClientException{
	}
	
	public void onLogout(LogoutEvent event) throws ClientException{
	}

}
