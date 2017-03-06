package com.cybernetica.bj.server.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cybernetica.bj.server.dao.BaseDao;
import com.cybernetica.bj.server.exceptions.DaoException;

/**
 * Base DAO class for hibernate
 * @author dmitri
 *
 */
public abstract class BaseDaoImpl implements BaseDao {
	protected static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;


	/**
	 * Returns sessionfactory object
	 * @return
	 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Returns current session
	 * @return
	 */
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Serializable> T save(T entity) throws DaoException {
		Session session = getSession();
		session.save(entity);
		return entity;
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public <T> T get(Class<T> entityCls,Serializable id) throws DaoException {
		Session session = getSession();
		return session.get(entityCls, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Serializable> T persist(T entity) throws DaoException {
		Session session = getSession();
		session.persist(entity);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Serializable> T update(T entity) throws DaoException {
		Session session = getSession();
		session.update(entity);
		return entity;
	}

	@Override
	public <T> void delete(T entity) throws DaoException {
		Session session = getSession();
		session.delete(entity);		
	}
	
}
