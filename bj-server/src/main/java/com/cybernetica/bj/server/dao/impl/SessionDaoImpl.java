package com.cybernetica.bj.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cybernetica.bj.server.dao.SessionDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.SpringSession;

/**
 * Game session DAO implementation
 * @author dmitri
 *
 */
@Repository
public class SessionDaoImpl extends BaseDaoImpl implements SessionDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpringSession findByUsername(String username) throws DaoException {
		Session session = getSession();
		Query<SpringSession> query = session.createQuery("from com.cybernetica.bj.server.models.SpringSession where username=:username",SpringSession.class);
		query.setParameter("username", username);
		List<SpringSession> list = query.getResultList();
		if(list==null || list.size()<1)
			return null;
		return list.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SpringSession> getAllSessions() throws DaoException {
		Session session = getSession();
		Query<SpringSession> query = session.createQuery("from com.cybernetica.bj.server.models.SpringSession",SpringSession.class);
		List<SpringSession> list = query.getResultList();
		if(list==null || list.size()<1)
			return new ArrayList<>();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpringSession findBySession(String sessionId) throws DaoException {
		Session session = getSession();
		Query<SpringSession> query = session.createQuery("from com.cybernetica.bj.server.models.SpringSession where id=:id",SpringSession.class);
		query.setParameter("id", sessionId);
		List<SpringSession> list = query.getResultList();
		if(list==null || list.size()<1)
			return null;
		return list.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAllSessions(String username, String sessionId) throws DaoException {
		Session session = getSession();
		if(username!=null){
			Query<?> query = session.createQuery("delete from com.cybernetica.bj.server.models.SpringSession where username=:username");
			query.setParameter("username", username);
			query.executeUpdate();
		}
		
		if(sessionId!=null){
			Query<?> query = session.createQuery("delete from com.cybernetica.bj.server.models.SpringSession where id=:id");
			query.setParameter("id", sessionId);
			query.executeUpdate();
		}
		
	}

}
