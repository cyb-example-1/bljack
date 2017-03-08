package com.cybernetica.bj.server.dao;

import java.io.Serializable;

import com.cybernetica.bj.server.exceptions.DaoException;

/**
 * generic DAO service
 * @author dmitri
 *
 */
public interface BaseDao {
	
	/**
	 * save entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	<T extends Serializable> T save(T entity) throws DaoException;
	
	/**
	 * persist entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	<T extends Serializable> T persist(T entity) throws DaoException;	
	
	/**
	 * Updates entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	<T extends Serializable> T update(T entity) throws DaoException;		

	/**
	 * Hit db for entity
	 * @param entityCls
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	<T> T get(Class<T> entityCls, Serializable id) throws DaoException;
	
	/**
	 * removes entity
	 * @param entityCls
	 * @param id
	 * @throws DaoException
	 */
	<T> void delete(T entity) throws DaoException;
}
