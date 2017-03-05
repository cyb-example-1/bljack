package com.cybernetica.bj.client.services.impl;

import java.util.ResourceBundle;

import com.cybernetica.bj.client.services.MessageService;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * Text translation service
 * @author dmitri
 *
 */
public class MessageServiceImpl implements MessageService,Singleton<MessageService>{
	private ResourceBundle translations;
	
	public MessageServiceImpl(){
		translations = ResourceBundle.getBundle("messages.en_US");
		
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String message(String messageCode,String defaultMsg){
		if(messageCode==null)
			return null;
		if(translations.containsKey(messageCode))
			return translations.getString(messageCode);
		return defaultMsg;
	}

}
