package com.cybernetica.bj.client.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.common.dto.RestResponseDTO;
import com.cybernetica.bj.common.dto.RestObjectReponseDTO;

public class BaseServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	protected void validate(RestResponseDTO resp) throws ClientException {
		if(resp==null)
			throw new ClientException("null response");
		if(resp.getErrors()!=null && resp.getErrors().size()>0) {
			for(String error:resp.getErrors())
				logger.debug("received {}",error);
			throw new ClientException(resp.getErrors().get(0));
		}			
	}
	
	protected <T> void validate(RestObjectReponseDTO<T> resp) throws ClientException {
		if(resp==null)
			throw new ClientException("null response");
		if(resp.getErrors()!=null && resp.getErrors().size()>0) {
			for(String error:resp.getErrors())
				logger.debug("received {}",error);
			throw new ClientException(resp.getErrors().get(0));
		}	
		if(resp.getObject()==null)
			throw new ClientException("object null response");
	}	
}
