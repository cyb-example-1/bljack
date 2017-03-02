package com.cybernetica.bj.server.dao;

import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.SpringSession;

/**
 * Session Dao
 * @author dmitri
 *
 */
public interface SessionDao extends BaseDao {

	SpringSession findByUsername(String username) throws DaoException;

}
