package com.cybernetica.bj.client;
	
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.scene.LoginScene;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	

	
	@Override
	public void start(Stage primaryStage) {
		GameCoordinator coordinator = new GameCoordinator();
		coordinator.init();
		try {
			initScenes(primaryStage);
			 LoginScene.get().replaceSceneContent();
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		primaryStage.setTitle("Blackjack");
		primaryStage.show();
	}


	private void initScenes(Stage stage) throws ClientException {
		LoginScene.create(stage,LoginScene.class);
		
	}


	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println();
		}
	}
	

}
