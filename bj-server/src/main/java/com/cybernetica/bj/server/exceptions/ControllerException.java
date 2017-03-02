package com.cybernetica.bj.server.exceptions;

import com.cybernetica.bj.common.exceptions.BaseException;

@SuppressWarnings("serial")
public class ControllerException extends BaseException{

	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
