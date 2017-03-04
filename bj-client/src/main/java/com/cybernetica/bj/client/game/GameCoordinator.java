package com.cybernetica.bj.client.game;

import com.cybernetica.bj.client.context.EventProducer;
import com.cybernetica.bj.client.events.LoginEvent;
import com.cybernetica.bj.client.interfaces.EventListener;


/**
 * coordinates game life cycle
 * @author dmitri
 *
 */
public class GameCoordinator implements EventListener<LoginEvent> {

	
	public void init() {
		EventProducer.addListener(this);
		
	}
	@Override
	public void onEvent(LoginEvent event) {
		System.out.println("");
		
	}



}
