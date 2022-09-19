package com.blz.bookstoreuser.exception;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CustomNotFoundException extends RuntimeException{
    private int statusCode;
    private String statusMessage;
    public CustomNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}