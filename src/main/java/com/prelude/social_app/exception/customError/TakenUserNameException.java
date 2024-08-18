package com.prelude.social_app.exception.customError;

public class TakenUserNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public TakenUserNameException(String message) {
        super(message);
    }
}
