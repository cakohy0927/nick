package com.orm.commons.exception;

@SuppressWarnings("serial")
public class AccountExistException extends RuntimeException {
	public AccountExistException() {
	}

	public AccountExistException(String message) {
		super(message);
	}

	public AccountExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountExistException(Throwable cause) {
		super(cause);
	}

	protected AccountExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
