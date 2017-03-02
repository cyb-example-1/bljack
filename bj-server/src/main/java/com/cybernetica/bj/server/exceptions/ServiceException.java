package com.cybernetica.bj.server.exceptions;

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
