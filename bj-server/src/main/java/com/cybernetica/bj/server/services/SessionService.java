package com.cybernetica.bj.server.services;

import java.util.List;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;

public interface SessionService {

	/**
	 * finds http session by username
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	public SpringSession findByUsername(String username) throws ServiceException;

	/**
	 * Returns all active sessions
	 * @return
	 * @throws ServiceException
	 */
	public List<SpringSession> getAllSessions() throws ServiceException;

	/**
	 * returns session by id
	 * @param sessionId
	 * @return
	 * @throws ServiceException
	 */
	public SpringSession findBySession(String sessionId) throws ServiceException;

	/**
	 * removes all sessions with username and/or sesisionId
	 * @param username
	 * @param sessionId
	 * @throws ServiceException
	 */
	public void deleteAllSessions(String username, String sessionId) throws ServiceException;
}
