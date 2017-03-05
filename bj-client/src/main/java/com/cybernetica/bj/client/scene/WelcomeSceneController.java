package com.cybernetica.bj.client.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameSession;
import com.cybernetica.bj.client.services.AuthService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomeSceneController extends BaseSceneController<WelcomeSceneController> {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeSceneController.class);
	
	@Override
	protected void postBuild() {
		Button btnLogout = (Button) getElementById("logout");
		Label textLabel = (Label) getElementById("msgText");
		
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



	@Override
	protected void postActivate() {
		setElementTextById("username",GameSession.get().getUser().getUsername());
		}
	
	

}
