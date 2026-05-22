package com.ganesh.pms.exceptions;

public class NoSessionFoundException extends RuntimeException {
    public NoSessionFoundException(String message) {
        super(message);
    }
}
