package com.cybernetica.bj.server.exceptions;

/**
 * Controller layer exception
 * @author dmitri
 *
 */
@SuppressWarnings("serial")
public class ControllerException extends RuntimeException {
	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ControllerException(ServiceException e) {
		this(e.getMessage(),e);
	}

}
