package com.cybernetica.bj.server.services.impl;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByUsername(String username) throws ServiceException {
		try {
			return userDao.findByUsername(username);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User updateBalance(String name, BigDecimal balanceChange) throws ServiceException {
		if(balanceChange==null)
			throw new ServiceException("error.user.balance-change.invalid");
		User user = findByUsername(name);
		BigDecimal balance = user.getBalance();
		if(balance==null)
			balance=BigDecimal.ZERO;
		user.setBalance(balance.add(balanceChange));
		try {
			return userDao.save(user);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User loadByUsername(String username) throws ServiceException {
		User user = findByUsername(username);
		if(user.getGame()!=null)
			Hibernate.initialize(user.getGame());
		return user;
	}

}
