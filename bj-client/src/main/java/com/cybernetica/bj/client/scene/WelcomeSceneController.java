package com.cybernetica.bj.client.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.UserDataEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameEventAdapter;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.interfaces.IDataListener;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.UserService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomeSceneController extends BaseSceneController<WelcomeSceneController> implements IDataListener {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);
	
	private Label textLabel;
	
	@Override
	protected void postBuild() {
		Button btnLogout = (Button) getElementById("logout");
		textLabel = (Label) getElementById("msgText");
		
		btnLogout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.logout.click");
            	try {
					AuthService.get().logout();
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
		
		
	}

	public void onUserData(UserDataEvent event){
		setElementTextById("username",GameSession.get().getUser().getUsername());
	}


	@Override
	protected void postActivate() {
		EventProducer.addUserDataListener(this);
		//simulate event
		onUserData(new UserDataEvent(GameSession.get().getUser()));
	}
	
	

}
