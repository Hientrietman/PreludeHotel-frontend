package com.prelude.social_app.exception;

import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.exception.customError.TakenEmailException;
import com.prelude.social_app.exception.customError.TakenUserNameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TakenEmailException.class)
    public ResponseEntity<ResponseApi<String>> handleTakenEmailException(TakenEmailException ex) {
        ResponseApi<String> response = new ResponseApi<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TakenUserNameException.class)
    public ResponseEntity<ResponseApi<String>> handleTakenUserNameException(TakenUserNameException ex) {
        ResponseApi<String> response = new ResponseApi<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<String>> handleGlobalException(Exception ex) {
        ResponseApi<String> response = new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", null, false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
