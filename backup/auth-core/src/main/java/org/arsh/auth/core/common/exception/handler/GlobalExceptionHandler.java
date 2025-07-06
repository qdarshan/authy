package org.arsh.auth.core.common.exception.handler;

import org.arsh.auth.core.common.exception.model.Error;
import org.arsh.auth.core.common.exception.UnAuthorizedException;
import org.arsh.auth.core.common.exception.model.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return new ErrorDetail(fieldError.getField(), error.getDefaultMessage());
                }).toList();

        Error error = new Error(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.copyOf(errorDetails)
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({UnAuthorizedException.class, UsernameNotFoundException.class})
    public ResponseEntity<Error> handleAuthException(Exception ex) {
        Error error = new Error(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
