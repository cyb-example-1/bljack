package com.cybernetica.bj.client.utils;

import java.util.ResourceBundle;

/**
 * Application property helper
 * @author dmitri
 *
 */
public class Properties {
	private static Properties instance;
	private static ResourceBundle  properties;
	
	private Properties(){
		properties = ResourceBundle.getBundle("application");
		
	}
	
	public static Properties get() {
		if(instance!=null)
			return instance;
		instance = new Properties();
		return instance;
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
