package br.com.jproject.sgu.core.exceptions.exception;

import br.com.jproject.sgu.core.constants.ErrorMessages;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND);
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
