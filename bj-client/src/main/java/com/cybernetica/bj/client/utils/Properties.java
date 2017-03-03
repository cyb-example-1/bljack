package com.cybernetica.bj.client.utils;

import java.util.ResourceBundle;

import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Application property helper
 * @author dmitri
 *
 */
public class Properties {
	private static ResourceBundle  properties;
	
	public Properties(){
		properties = ResourceBundle.getBundle("application");
		
	}
	
	public static Properties get() {
		return Singleton.getSingleton(Properties.class);
	}
	
	/**
	 * Return string value or null
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		get();
		if(!properties.containsKey(key))
			return null;
		return properties.getString(key);
	}


	
	
}
