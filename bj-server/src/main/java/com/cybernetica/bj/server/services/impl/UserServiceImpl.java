package com.cybernetica.bj.server.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybernetica.bj.server.dao.UserDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.services.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User findByUsername(String username) throws ServiceException {
		try {
			return userDao.findByUsername(username);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User create(String username, String password) throws ServiceException {
		User user = findByUsername(username);
		if(user!=null)
			return user;
		user=new User();
		user.setPassword(password);
		user.setUsername(username);
		try {
			user=userDao.save(user);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return user;
	}



}
