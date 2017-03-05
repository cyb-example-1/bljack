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

import javafx.stage.Stage;

public abstract class BaseSceneTest extends ApplicationTest {

	private static RestService origRestService;
	protected RestService restService;

	@Override
	public void start(Stage stage) throws Exception {
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
	
	protected abstract void initScene(Stage stage) throws Exception;
	
}
