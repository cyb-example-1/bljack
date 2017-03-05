package com.cybernetica.bj.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.BaseEvent;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.interfaces.EventListener;
import com.cybernetica.bj.client.scene.LoginSceneController;
import com.cybernetica.bj.client.scene.WelcomeSceneController;
import com.cybernetica.bj.client.utils.Manager;
import com.cybernetica.bj.common.dto.user.UserResponseDTO;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * coordinates game life cycle
 * @author dmitri
 *
 */
public class GameCoordinator implements EventListener<BaseEvent> {
	private static final Logger logger = LoggerFactory.getLogger(GameCoordinator.class);

	
	/**
	 * initializes application and beans
	 * @param primaryStage
	 * @throws ClientException
	 */
	public void init(Stage primaryStage) throws ClientException {
		EventProducer.removeAllListeners();
		EventProducer.addListener(this);
		
		if(primaryStage!=null){
			initScenes(primaryStage);
			
			primaryStage.addEventHandler(WindowEvent.ANY, new  EventHandler<WindowEvent>()
		    {
		        @Override
		        public void handle(WindowEvent window)
		        {
		            System.out.println("");
		        }
		    });
		}
		
	}
	
	/**
	 * load scenes
	 * @param stage
	 * @throws ClientException
	 */
	private void initScenes(Stage stage) throws ClientException {
		LoginSceneController.create(stage,LoginSceneController.class);
		WelcomeSceneController.create(stage,WelcomeSceneController.class);
		
	}
	
	/**
	 * handles onLogin event
	 */
	@Override
	public void onEvent(BaseEvent event) {
		logger.trace("got {} event", event);
		GameSession.get().setUser(new UserResponseDTO());
		try {
			Manager.switchTo(WelcomeSceneController.class);
		} catch (Exception e) {
			logger.error("error on event "+event.toString(),e);
			return;
		}
		
	}



}
