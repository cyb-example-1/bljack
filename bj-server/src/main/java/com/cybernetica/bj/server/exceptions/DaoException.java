package com.cybernetica.bj.server.exceptions;

import com.cybernetica.bj.common.exceptions.BaseException;

/**
 * Dao level generic exception
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class DaoException  extends BaseException{
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
