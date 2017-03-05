package com.cybernetica.bj.client.test;

import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.framework.junit.ApplicationTest;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.services.AuthService;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.client.services.UserService;

import javafx.stage.Stage;

public abstract class BaseSceneTest extends ApplicationTest {

	private static RestService origRestService;
	protected RestService restService;
	private boolean isRestMocked = false;

	@Override
	public void start(Stage stage) throws Exception {
		setup();
		GameCoordinator coordinator = GameCoordinator.get();
		coordinator.init(stage);

        initScene(stage);
        stage.show();
    }
	@BeforeClass
	public static void prepare() throws ClientException{

		origRestService = RestService.get();
	}
	
	@Before
	public void setup() throws Exception{
		if(isRestMocked==true)
			return;
		restService = mock(RestService.class);
		setRestService(restService);
		isRestMocked=true;
	}
	
	@After
	public void finish() throws Exception{
		setRestService(origRestService);
	}
	
	private void setRestService(RestService restService) throws Exception{
		MethodUtils.invokeMethod(AuthService.get(), true,"setRestService", restService);
		MethodUtils.invokeMethod(UserService.get(), true,"setRestService", restService);
		
	}
	
	protected abstract void initScene(Stage stage) throws Exception;
	
}
