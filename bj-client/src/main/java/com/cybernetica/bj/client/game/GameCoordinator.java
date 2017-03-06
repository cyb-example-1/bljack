package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.scene.BetSceneController;
import com.cybernetica.bj.client.scene.BlackjackSceneController;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.scene.WelcomeSceneController;
import com.cybernetica.bj.common.interfaces.Singleton;

import javafx.stage.Stage;


/**
 * coordinates game life cycle
 * @author dmitri
 *
 */
public class GameCoordinator implements Singleton<GameCoordinator> {
	private static final Logger logger = LoggerFactory.getLogger(GameCoordinator.class);
	
	/**
	 * Dispatches events
	 */
	private GameEventAdapter eventDispatcher;

	public static GameCoordinator get(){
		return Singleton.getSingleton(GameCoordinator.class);
	}
	/**
	 * initializes application and beans
	 * @param primaryStage
	 * @throws ClientException
	 */
	public void init(Stage primaryStage) throws ClientException {
		EventProducer.removeAllListeners();
		
		setEventDispatcher(new GameEventDispatcher());
		EventProducer.addListener(eventDispatcher);
		
		if(primaryStage!=null){
			initScenes(primaryStage);
		}
		
	}
	
	
	
	/**
	 * load scenes
	 * @param stage
	 * @throws ClientException
	 */
	private void initScenes(Stage stage) throws ClientException {
		logger.info("Initializing stages");
		LoginSceneController.create(stage,LoginSceneController.class);
		WelcomeSceneController.create(stage,WelcomeSceneController.class);
		BetSceneController.create(stage,BetSceneController.class);
		BlackjackSceneController.create(stage,BlackjackSceneController.class);
		
	}
	
	
	public GameEventAdapter getEventDispatcher() {
		return eventDispatcher;
	}
	
	
	public void setEventDispatcher(GameEventAdapter eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

}
