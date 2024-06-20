package br.com.jproject.sgu.infrastructure.exceptions.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}
