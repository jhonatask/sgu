package br.com.jproject.sgu.infrastructure.exceptions.exception;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
