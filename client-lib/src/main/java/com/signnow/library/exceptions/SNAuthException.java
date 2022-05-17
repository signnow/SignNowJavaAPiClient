package com.signnow.library.exceptions;

import com.signnow.library.dto.AuthError;

public class SNAuthException extends SNException {
    private final AuthError.Type authError;

    public SNAuthException(AuthError.Type error) {
        this.authError = error;
    }

    public SNAuthException(String message) {
        super(message);
        this.authError = AuthError.Type.UNKNOWN;
    }

    public SNAuthException(String message, Throwable cause) {
        super(message, cause);
        this.authError = AuthError.Type.UNKNOWN;
    }

    public AuthError.Type getAuthError() {
        return authError;
    }
}
