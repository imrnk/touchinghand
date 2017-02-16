package org.touchinghand.exception;

public class ClientCannotBeCreatedException extends Exception {

	public ClientCannotBeCreatedException(Throwable cause) {
		super("Client Cannot Be created", cause);
	}

	public ClientCannotBeCreatedException(String message) {
		super(message);
	}
	
}
