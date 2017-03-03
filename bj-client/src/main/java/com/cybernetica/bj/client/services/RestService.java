package com.cybernetica.bj.client.services;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.dto.BaseRestResponseDTO;

/**
 * provides rest services.
 * statefull singleton.
 * @author dmitri
 *
 */
public class RestService {
	private static final Logger logger = LoggerFactory.getLogger(RestService.class);
	private static RestService instance;
	
	private HttpClient client;
	
	private RestService(){	
		client = buildClient();
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
	
	private HttpClient buildClient(){
		return HttpClientBuilder.create().build();
	}
	
	/**
	 * Resets REST state
	 */
	public void clean(){
		client=null;
		client=buildClient();
	}
	
	/**
	 * GET request
	 * @param uri
	 * @param respClass
	 * @return
	 * @throws ClientException
	 */
	public <T extends BaseRestResponseDTO> T get(String uri,Class<T> respClass) throws ClientException{
		logger.debug("calling {}",uri);
		return null;
	}
	
	/**
	 * PUT request
	 * @param uri
	 * @param content
	 * @param respClass
	 * @return
	 * @throws ClientException
	 */
	public <T extends BaseRestResponseDTO,Y extends BaseDTO> T put(String uri,Y content,Class<T> respClass) throws ClientException{
		logger.debug("calling {} with {}",uri,content);
		return null;
	}
	
	/**
	 * POST request
	 * @param uri
	 * @param content
	 * @param respClass
	 * @return
	 * @throws ClientException
	 */
	public <T extends BaseRestResponseDTO,Y extends BaseDTO> T post(String uri,Y content,Class<T> respClass) throws ClientException{
		logger.debug("calling {} with {}",uri,content);
		return null;
	}
	
	/**
	 * DELETE
	 * @param uri
	 * @param respClass
	 * @return
	 * @throws ClientException
	 */
	public <T extends BaseRestResponseDTO> T delete(String uri,Class<T> respClass) throws ClientException{
		logger.debug("calling {}",uri);
		return null;
	}
	
}
