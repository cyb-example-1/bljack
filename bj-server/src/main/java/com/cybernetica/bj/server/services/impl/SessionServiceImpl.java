package com.cybernetica.bj.server.services.impl;

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

}
