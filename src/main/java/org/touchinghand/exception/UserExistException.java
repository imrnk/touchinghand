package org.touchinghand.exception;

public class UserExistException extends Exception {

	UserExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserExistException(Throwable cause) {
		super("This username is already registered", cause);
	}

}
