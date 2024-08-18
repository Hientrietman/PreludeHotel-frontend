package com.prelude.social_app.exception.customError;

public class TakenEmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public TakenEmailException(String message) {
        super(message);
    }
}
