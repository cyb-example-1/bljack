package com.cybernetica.bj.client.scene;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.test.BaseSceneTest;
import com.cybernetica.bj.client.utils.Manager;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

import javafx.stage.Stage;

public class LoginSceneTest  extends BaseSceneTest{
	
	@Override
	protected void initScene(Stage stage) throws Exception {
		Manager.switchTo(LoginSceneController.class);	
	}

    @Test
    public void testLogin() throws ClientException {
    	LoginResponseDTO dto = new LoginResponseDTO();
    	when(restService.post(eq("/session/login"),anyObject(),anyObject())).thenReturn(dto);
        // given:
    	clickOn("#username");
    	write("test");
    	clickOn("#password");
    	write("test");
    	clickOn("#signin");
    	
    	assertNotNull(Manager.current().getClass().equals(WelcomeSceneController.class));
    }


}
