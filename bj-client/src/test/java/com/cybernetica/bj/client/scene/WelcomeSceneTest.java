package com.cybernetica.bj.client.scene;

import com.cybernetica.bj.client.test.BaseSceneTest;
import com.cybernetica.bj.client.utils.Manager;

import javafx.stage.Stage;

public class WelcomeSceneTest  extends BaseSceneTest{

	@Override
	protected void initScene(Stage stage) throws Exception {
		Manager.switchTo(WelcomeSceneController.class);	
	}
	
	

}
