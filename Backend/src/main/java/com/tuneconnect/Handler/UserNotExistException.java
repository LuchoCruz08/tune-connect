package com.tuneconnect.Handler;

public class UserNotExistException extends RuntimeException{

    public UserNotExistException() {
        super("User not found");
    }

}
