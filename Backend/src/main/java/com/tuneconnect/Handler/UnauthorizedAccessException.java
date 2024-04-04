package com.tuneconnect.Handler;

public class UnauthorizedAccessException extends RuntimeException{

    public UnauthorizedAccessException() {
        super("Unauthorized access: The user does not have permission to perform this action.");
    }

}
