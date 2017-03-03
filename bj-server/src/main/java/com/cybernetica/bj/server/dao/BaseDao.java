package com.cybernetica.bj.server.dao;

import java.io.Serializable;

import com.cybernetica.bj.server.exceptions.DaoException;

public interface BaseDao {
	
	/**
	 * save entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	<T extends Serializable> T save(T entity) throws DaoException;

}
