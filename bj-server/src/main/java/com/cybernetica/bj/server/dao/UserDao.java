package com.cybernetica.bj.server.dao;

import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.User;

public interface UserDao extends BaseDao{

	User findByUsername(String username) throws DaoException;

}
