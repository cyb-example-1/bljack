package com.cybernetica.bj.client.scene;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.test.BaseSceneTest;
import com.cybernetica.bj.client.utils.Manager;
import com.cybernetica.bj.common.dto.LogoutResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

import javafx.stage.Stage;

public class WelcomeSceneTest  extends BaseSceneTest{

	@Override
	protected void initScene(Stage stage) throws Exception {
		GameCoordinator.get().getEventDispatcher().onEvent(new LoginEvent(new  LoginResponseDTO()));
		Manager.switchTo(WelcomeSceneController.class);	
	}
	
    @Test
    public void testLogout() throws ClientException {
    	assertEquals(WelcomeSceneController.class, Manager.current().getClass());
    	LogoutResponseDTO dto = new LogoutResponseDTO();
    	when(restService.post(eq("/session/logout"),anyObject(),anyObject())).thenReturn(dto);
        // given:
    	clickOn("#logout");
    	
    	assertEquals(LoginSceneController.class, Manager.current().getClass());
    }

}
