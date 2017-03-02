package com.cybernetica.bj.common.exceptions;

/**
 * Base exception class. for all layers.
 * @author dmitri
 *
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BaseException(String message){
		super(message);
	}
	
	public BaseException(String message,Throwable throwable){
		super(message, throwable);
	}

}
