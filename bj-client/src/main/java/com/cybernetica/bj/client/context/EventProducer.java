package com.cybernetica.bj.client.context;

import java.util.ArrayList;
import java.util.List;

import com.cybernetica.bj.client.events.BaseEvent;
import com.cybernetica.bj.client.interfaces.EventListener;

/**
 * main event producer. supports all events
 * @author dmitri
 *
 */
public class EventProducer {
	private static List<EventListener<?>> listeners =  new ArrayList<>();
	
	public static void addListener(EventListener<?> listener){
		listeners.add(listener);
	}
	
	public static void removeListener(EventListener<?> listener){
		listeners.remove(listener);
	}
	
	public static <T extends BaseEvent> void publishEvent(T event){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<EventListener<T>> lst =(List)listeners;
		for(EventListener<T> listener:lst)
			listener.onEvent(event);
	}

	public static void removeAllListeners() {
		listeners.clear();		
	}

}
