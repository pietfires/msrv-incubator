package com.incubator.vrsa.exceptions;

public class MovieServiceException extends Exception {
    public MovieServiceException(String message) {
        super(message);
    }

    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
