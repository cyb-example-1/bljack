package com.cybernetica.bj.client.scene;

import javax.validation.ValidationException;

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

	@Override
	protected void postBuild() {
		
		setElementTextById("signin","form.button.signin");
		setElementTextById("welcomeText","form.label.welcome");
		
		Button button = (Button) getElementById("signin");
		Label textLabel = (Label) getElementById("msgText");
		
		button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	String username=getElementTextById("username");
            	String password=getElementTextById("password");
            	try {
					AuthService.get().login(username,password);
				} catch (ValidationException e1) {
					setElementText(textLabel,e1.getMessage());
				}
            }
        });
	}

}
