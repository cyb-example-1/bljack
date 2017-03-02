package com.cybernetica.bj.server.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base DAO class for hibernate
 * @author dmitri
 *
 */
public abstract class BaseDaoImpl {
	protected static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
}
