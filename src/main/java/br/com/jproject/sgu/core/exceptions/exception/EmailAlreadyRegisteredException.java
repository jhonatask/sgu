package br.com.jproject.sgu.core.exceptions.exception;

import br.com.jproject.sgu.core.constants.ErrorMessages;

public class EmailAlreadyRegisteredException extends RuntimeException {
    
    public EmailAlreadyRegisteredException() {
        super(ErrorMessages.EMAIL_ALREADY_REGISTERED);
    }
    
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
    
    public EmailAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
