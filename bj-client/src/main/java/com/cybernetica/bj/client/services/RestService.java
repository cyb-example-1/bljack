package com.cybernetica.bj.client.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provides rest services.
 * statefull singleton.
 * @author dmitri
 *
 */
public class RestService {
	private static final Logger logger = LoggerFactory.getLogger(RestService.class);
	private static RestService instance;
	
	private RestService(){	
	}
	
	/**
	 * gets singleton
	 * @return
	 */
	public static RestService get(){
		if(instance!=null)
			return instance;
		instance=new RestService();
		return instance;	
	}
	
	
}
