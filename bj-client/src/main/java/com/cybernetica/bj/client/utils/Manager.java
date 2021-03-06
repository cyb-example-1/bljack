package com.cybernetica.bj.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.scene.BaseSceneController;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Manages scene controllers
 * @author dmitri
 *
 */
public class Manager {
	private static final Logger logger = LoggerFactory.getLogger(Manager.class);
	
	private static Object current;
	
	
	public static <T extends BaseSceneController<T>> T get(Class<T> cls){
		return Singleton.getSingleton(cls);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends BaseSceneController<T>> T current(){
		return (T) current;
	}	

	public static <T extends BaseSceneController<T>> void switchTo(Class<T> cls) throws ClientException {
		T controller=get(cls);
		if(current!=null){
			if(current.getClass().equals(cls))
				return;
		}
		try {
			controller.replaceSceneContent();
		} catch (Exception e) {
			logger.error("scene switch failed",e);
			throw new ClientException("scene switch failed",e);
		}	
		current=controller;
	}

}
