package com.cybernetica.bj.server.dao;

import java.util.List;

import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.SpringSession;

/**
 * Session Dao
 * @author dmitri
 *
 */
public interface SessionDao extends BaseDao {

	/**
	 * finds by username
	 * @param username
	 * @return
	 * @throws DaoException
	 */
	SpringSession findByUsername(String username) throws DaoException;

	/**
	 * returns all sessions
	 * @return
	 * @throws DaoException
	 */
	List<SpringSession> getAllSessions() throws DaoException;
	
	/**
	 * finds by session id
	 * @param sessionId
	 * @return
	 * @throws DaoException
	 */
	SpringSession findBySession(String sessionId) throws DaoException;

	/**
	 * romves all session related to providede data
	 * @param username
	 * @param sessionId
	 * @throws DaoException
	 */
	void deleteAllSessions(String username, String sessionId) throws DaoException;

}
