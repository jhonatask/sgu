package br.com.jproject.sgu.core.exceptions.exception;

import br.com.jproject.sgu.core.constants.ErrorMessages;

public class CpfAlreadyRegisteredException extends RuntimeException{
    
    public CpfAlreadyRegisteredException() {
        super(ErrorMessages.CPF_ALREADY_REGISTERED);
    }
    
    public CpfAlreadyRegisteredException(final String message) {
        super(message);
    }

    public CpfAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
