package com.cybernetica.bj.client.scene;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

import javafx.stage.Stage;

public class LoginSceneTest  extends ApplicationTest {
	
	private static RestService origRestService;
	private RestService restService;

	@Override
	public void start(Stage stage) throws Exception {
		LoginScene.create(stage, LoginScene.class);
		LoginScene.get().replaceSceneContent();
        //Scene scene = new Scene(new DesktopPane(), 800, 600);
        //stage.setScene(scene);
        stage.show();
    }
	@BeforeClass
	public static void prepare(){
		GameCoordinator coordinator = new GameCoordinator();
		coordinator.init();
		origRestService = RestService.get();
	}
	
	@Before
	public void setup() throws Exception{
		restService = mock(RestService.class);
		setRestService(restService);
	}
	
	@After
	public void finish() throws Exception{
		setRestService(origRestService);
	}
	
	private void setRestService(RestService restService) throws Exception{
		MethodUtils.invokeMethod(AuthService.get(), true,"setRestService", restService);
		
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
        //rightClickOn("#desktop").moveTo("New").clickOn("Text Document");
        //write("myTextfile.txt").push(ENTER);

        // when:
        //drag(".file").dropTo("#trash-can");

        // then:
        //verifyThat("#desktop", hasChildren(0, ".file"));
    }
}
