package org.touchinghand.exception;

public class RegistrationException extends Exception{

	public RegistrationException(Throwable cause) {
		super("Some error occurred in registration process", cause);
	}
	
}
