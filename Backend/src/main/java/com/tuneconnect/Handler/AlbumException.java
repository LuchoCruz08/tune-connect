package com.tuneconnect.Handler;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlbumException extends Exception{

    private final String message;
    private final HttpStatus httpStatus;

    public AlbumException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public AlbumException(String message) {
        super(message);
        this.message = message;
        this.httpStatus = null;
    }

}
