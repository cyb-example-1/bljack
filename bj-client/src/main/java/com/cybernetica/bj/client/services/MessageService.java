package com.cybernetica.bj.client.services;

import com.cybernetica.bj.common.interfaces.Singleton;

public interface MessageService extends Singleton<MessageService> {

	
	/**
	 * Gets message by its code or code if not found
	 * @param messageCode
	 * @return
	 */
	public static String message(String messageCode){
		return MessageService.get().message(messageCode, messageCode);
	}

	/**
	 * Singleton initializer
	 * @return
	 */
	public static MessageService get() {
		return Singleton.getSingleton(MessageService.class);
	}
	
	/**
	 * Gets message by its code or <code>defaultMsg</code> if not found
	 * @param messageCode
	 * @return
	 */	
	public String message(String messageCode,String defaultMsg);
}
