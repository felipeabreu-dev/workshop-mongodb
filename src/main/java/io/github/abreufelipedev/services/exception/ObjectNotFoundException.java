package io.github.abreufelipedev.services.exception;

public class ObjectNotFoundException extends RuntimeException  {
    private static final Long serialVersionUID = 1L;

    public ObjectNotFoundException(String message){
        super(message);
    }
}
