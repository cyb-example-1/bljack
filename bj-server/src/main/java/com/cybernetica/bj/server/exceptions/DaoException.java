package com.cybernetica.bj.server.exceptions;

@SuppressWarnings("serial")
public class DaoException  extends BaseException{
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
