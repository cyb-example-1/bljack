package com.cybernetica.bj.client.services;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.interfaces.Singleton;

/**
 * provides rest services.
 * statefull singleton.
 * @author dmitri
 *
 */
public interface RestService   extends Singleton<RestService>{

	/**
	 * Singleton initializer
	 * @return
	 */	
	public static RestService get() {
		return Singleton.getSingleton(RestService.class);
	}
	
	/**
	 * POST request
	 * @param uri
	 * @param content
	 * @param respClass
	 * @return
	 * @throws ClientException
	 */
	public <T extends RestResponseDTO,Y extends BaseDTO> T post(String uri,Y content,Class<T> respClass) throws ClientException;

	/**
	 * GET request
	 * @param uri
	 * @param respClass
	 * @return
	 */
	public <T extends RestResponseDTO> T get(String uri,Class<T> respClass) throws ClientException;
	
	
	/**
	 * PUT request
	 * @param uri
	 * @param respClass
	 * @return
	 */
	public <T extends RestResponseDTO> T put(String uri,Class<T> respClass) throws ClientException;	
	
	
	/**
	 * DELETE request
	 * @param uri
	 * @param respClass
	 * @return
	 */
	public <T extends RestResponseDTO> T delete(String uri,Class<T> respClass) throws ClientException;		


	
}
