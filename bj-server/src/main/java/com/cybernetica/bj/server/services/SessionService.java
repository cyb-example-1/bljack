package com.cybernetica.bj.server.services;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;

public interface SessionService {

	
	public SpringSession findByUsername(String username) throws ServiceException;
}
