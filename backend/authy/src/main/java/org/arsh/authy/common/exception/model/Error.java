package org.arsh.authy.common.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Error(
        int status,
        String error,
        String message,
        List<ErrorDetail> errorDetails,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        ZonedDateTime timestamp
) {

    public Error(int status, String error, String message) {
        this(status, error, message, null, ZonedDateTime.now());
    }

    public Error(int status, String error, List<ErrorDetail> errorDetails) {
        this(status, error, null, errorDetails, ZonedDateTime.now());
    }

}