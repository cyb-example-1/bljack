package com.cybernetica.bj.server.services;



import java.math.BigDecimal;

import com.cybernetica.bj.server.exceptions.ServiceException;
import com.cybernetica.bj.server.models.User;

/**
 * User profile service
 * @author dmitri
 *
 */
public interface UserService {

	/**
	 * searches user by username
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	User findByUsername(String username) throws ServiceException;
	
	
	/**
	 * Searches user by username and pre-loads its game within object
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	User loadByUsername(String username) throws ServiceException;
	
	
	/**
	 * Creates user if not exists
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	User create(String username,String password) throws ServiceException;


	/**
	 * Balance change
	 * @param name
	 * @param balanceChange
	 * @return
	 * @throws ServiceException
	 */
	User updateBalance(String name, BigDecimal balanceChange) throws ServiceException;




}
