package com.cybernetica.bj.client.exceptions;

import com.cybernetica.bj.common.exceptions.BaseException;

@SuppressWarnings("serial")
public class ClientException extends BaseException {

	public ClientException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
