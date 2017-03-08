package br.com.jorgeacetozi.ebookChat.authentication.domain.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}

}
