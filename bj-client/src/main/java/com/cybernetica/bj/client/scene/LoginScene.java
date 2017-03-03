package com.cybernetica.bj.client.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.AuthService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Login scene form
 * @author dmitri
 *
 */
public class LoginScene extends BaseScene<LoginScene> {
	private static final Logger logger = LoggerFactory.getLogger(LoginScene.class);

	@Override
	protected void postBuild() {
		
		setElementTextById("signin","form.button.signin");
		setElementTextById("welcomeText","form.label.welcome");
		
		Button button = (Button) getElementById("signin");
		Label textLabel = (Label) getElementById("msgText");
		
		button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	logger.trace("form.login.click");
            	setElementText(textLabel,"");
            	String username=getElementTextById("username");
            	String password=getElementTextById("password");
            	try {
					AuthService.get().login(username,password);
				} catch (ClientException e1) {
					logger.debug(e1.getMessage());
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
	}

}
