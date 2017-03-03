package com.cybernetica.bj.client.services;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.dto.BaseRestResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * provides rest services.
 * statefull singleton.
 * @author dmitri
 *
 */
public interface RestService   extends Singleton<RestService>{

	public <T extends BaseRestResponseDTO,Y extends BaseDTO> T post(String uri,Y content,Class<T> respClass) throws ClientException;

	/**
	 * Singleton initializer
	 * @return
	 */	
	public static RestService get() {
		return Singleton.getSingleton(RestService.class);
	}
	
}
