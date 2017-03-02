package com.cybernetica.bj.client.services;

import java.util.ResourceBundle;

/**
 * Text translation service
 * @author dmitri
 *
 */
public class MessageService {
	private static MessageService instance;
	
	private ResourceBundle translations;
	
	private MessageService(){
		translations = ResourceBundle.getBundle("messages.en_US");
		
	}
	
	/**
	 * gets singleton
	 * @return
	 */
	public static MessageService get(){
		if(instance!=null)
			return instance;
		instance=new MessageService();
		return instance;		
		
	}
	
	/**
	 * Gets message by its code or code if not found
	 * @param messageCode
	 * @return
	 */
	public static String message(String messageCode){
		return MessageService.get().message(messageCode, messageCode);
	}
	/**
	 * Gets message by its code or <code>defaultMsg</code> if not found
	 * @param messageCode
	 * @return
	 */	
	public String message(String messageCode,String defaultMsg){
		if(translations.containsKey(messageCode))
			return translations.getString(messageCode);
		return defaultMsg;
	}

}
