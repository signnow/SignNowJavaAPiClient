package com.signnow.library.exceptions;

public abstract class SNException extends Exception {
    protected SNException() {}

    protected SNException(String message) {
        super(message);
    }

    protected SNException(String message, Throwable cause) {
        super(message, cause);
    }
}
