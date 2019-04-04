package com.foolday.common.exception;

public class PlatformException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PlatformException() {
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }
}
