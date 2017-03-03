package com.cybernetica.bj.server.services.impl;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.services.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public User findByUsername(String username) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
