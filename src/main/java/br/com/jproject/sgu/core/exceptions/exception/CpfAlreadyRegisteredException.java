package br.com.jproject.sgu.core.exceptions.exception;

public class CpfAlreadyRegisteredException extends RuntimeException{
    public CpfAlreadyRegisteredException(final String message) {
        super(message);
    }

    public CpfAlreadyRegisteredException(String message, Throwable cause) {
        super(message);
    }

    public CpfAlreadyRegisteredException() {
    }
}
