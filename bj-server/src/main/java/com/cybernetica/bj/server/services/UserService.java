package com.cybernetica.bj.server.services;

import org.springframework.security.core.userdetails.User;

import com.cybernetica.bj.server.exceptions.ServiceException;

public interface UserService {

	User findByUsername(String username) throws ServiceException;

}
