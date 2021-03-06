package com.cybernetica.bj.server.exceptions;

import com.cybernetica.bj.common.exceptions.BaseException;

/**
 * Service layer generic exception
 * @author 37912300240
 *
 */
@SuppressWarnings("serial")
public class ServiceException  extends BaseException{
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ServiceException(DaoException e) {
		this(e.getMessage(),e);
	}
}
