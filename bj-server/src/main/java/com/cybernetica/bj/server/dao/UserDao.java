package com.cybernetica.bj.server.dao;

import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.User;

/**
 * User profile DAO
 * @author dmitri
 *
 */
public interface UserDao extends BaseDao{

	User findByUsername(String username) throws DaoException;

	User get(Long id) throws DaoException;

}
