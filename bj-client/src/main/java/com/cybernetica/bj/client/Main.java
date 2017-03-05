package com.cybernetica.bj.client;
	
import com.cybernetica.bj.client.game.GameCoordinator;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.utils.Manager;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	

	
	@Override
	public void start(Stage primaryStage) {
		GameCoordinator coordinator = GameCoordinator.get();
		
		try {
			coordinator.init(primaryStage);
			Manager.switchTo(LoginSceneController.class);		
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		
		
		primaryStage.setTitle("Blackjack");
		primaryStage.show();
	}



	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
