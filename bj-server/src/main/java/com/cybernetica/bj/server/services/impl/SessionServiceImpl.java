package com.cybernetica.bj.server.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybernetica.bj.server.dao.SessionDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.SpringSession;
import com.cybernetica.bj.server.services.SessionService;

@Transactional
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionDao sessionDao;

	@Override
	public SpringSession findByUsername(String username) throws ServiceException {
		try {
			return sessionDao.findByUsername(username);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<SpringSession> getAllSessions() throws ServiceException {
		try {
			return sessionDao.getAllSessions();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public SpringSession findBySession(String sessionId) throws ServiceException {

		try {
			return sessionDao.findBySession(sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteAllSessions(String username, String sessionId) throws ServiceException {
		try {
			sessionDao.deleteAllSessions(username,sessionId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}		
	}

}
