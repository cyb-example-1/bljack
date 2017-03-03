package com.cybernetica.bj.server.services;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;

public interface SessionService {

	/**
	 * hinds http session by username
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	public SpringSession findByUsername(String username) throws ServiceException;
}
