package br.com.jproject.sgu.core.exceptions.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public UserNotFoundException() {

    }
}
