package com.cybernetica.bj.server.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cybernetica.bj.server.dao.SessionDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.SpringSession;

@Repository
public class SessionDaoImpl extends BaseDaoImpl implements SessionDao {

	@Override
	public SpringSession findByUsername(String username) throws DaoException {
		Session session = getSession();
		@SuppressWarnings("unchecked")
		Query<SpringSession> query = session.createQuery("from SpringSession where username=:username");
		query.setParameter("username", username);
		List<SpringSession> list = query.getResultList();
		if(list==null || list.size()<1)
			return null;
		return list.get(0);
	}

}
