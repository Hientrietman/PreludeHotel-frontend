package com.prelude.social_app.exception.customError;

public class AccountRegistrationErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public AccountRegistrationErrorException(String message) {
        super(message);
    }
}
