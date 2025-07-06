package org.arsh.authy.common.exception.handler;

import org.arsh.authy.common.exception.model.Error;
import org.arsh.authy.common.exception.model.ErrorDetail;
import org.arsh.authy.common.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Error>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return new ErrorDetail(fieldError.getField(), error.getDefaultMessage());
                }).toList();

        return ResponseEntity.badRequest()
                .body(Response.error(new Error(errorDetails), "Validation failed"));
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<Response<Error>> handleAuthException(
            Exception ex
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.error(new Error(ex.getMessage()), "Authentication failed"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<?>> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        return ResponseEntity.badRequest().body(Response.error( "Invalid request"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleGenericException(
            Exception ex
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error("An unexpected error occurred"));
    }
}