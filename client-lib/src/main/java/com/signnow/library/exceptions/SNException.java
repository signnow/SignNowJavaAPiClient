package com.signnow.library.exceptions;

public abstract class SNException extends Exception {
    public SNException() {}

    public SNException(String message) {
        super(message);
    }

    public SNException(String message, Throwable cause) {
        super(message, cause);
    }
}
