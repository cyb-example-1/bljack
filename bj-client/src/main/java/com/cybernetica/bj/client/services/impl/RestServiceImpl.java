package com.cybernetica.bj.client.services.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.RestService;
import com.cybernetica.bj.common.dto.BaseDTO;
import com.cybernetica.bj.common.dto.BaseRestResponseDTO;
import com.cybernetica.bj.common.dto.login.LoginRequestDTO;
import com.cybernetica.bj.common.dto.login.LoginResponseDTO;

/**
 * provides rest services.
 * statefull singleton.
 * @author dmitri
 *
 */
public class RestServiceImpl implements RestService{
	private static final Logger logger = LoggerFactory.getLogger(RestService.class);
	
	private CloseableHttpClient client;
	
	public RestServiceImpl(){	
		client = buildClient();
	}
	

	
	private CloseableHttpClient buildClient(){
		
		
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
		return doExecute("GET",uri,null,respClass);
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
		return doExecute("PUT",uri,content,respClass);
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
		return doExecute("POST",uri,content,respClass);
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
		return doExecute("DELETE",uri,null,respClass);
	}
	
	
	private <T extends BaseRestResponseDTO,Y extends BaseDTO> T doExecute(String method,String path,Y content,Class<T> respClass) throws ClientException {
		String uri="http://localhost:8080/"+path;
		logger.debug("calling {}",uri);
		RequestBuilder builder = RequestBuilder.create(method);
		builder.setUri(uri);
		
		if(content!=null) {
			String json="";
			if(logger.isTraceEnabled())
				logger.trace("content {}",json);
			
			StringEntity entity = new StringEntity(json,ContentType.APPLICATION_JSON);
			builder.setEntity(entity);
		}
		
		CloseableHttpResponse response1;
		try {
			response1 = client.execute(builder.build());
		} catch (IOException e1) {
			logger.error("execute error",e1);
			throw new ClientException("error.http.execute", e1);
		}
		String responseString;
		try {
			logger.debug("response status: {}",response1.getStatusLine());
		    HttpEntity entity1 = response1.getEntity();
		    responseString = EntityUtils.toString(entity1, "utf-8");
		    EntityUtils.consume(entity1);
		} 
		catch(Exception e) {
			logger.error("invalid response",e);
			throw new ClientException("error.http.read", e);
		}
		finally {
		    try {
				response1.close();
			} catch (IOException e) {
				logger.error("response close",e);
			}
		}

		return null;
	}
}
