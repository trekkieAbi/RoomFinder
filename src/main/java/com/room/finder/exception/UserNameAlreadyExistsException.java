package com.room.finder.exception;

public class UserNameAlreadyExistsException extends RuntimeException{
    private String message;

    public UserNameAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
