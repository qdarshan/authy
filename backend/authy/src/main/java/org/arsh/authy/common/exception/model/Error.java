package org.arsh.authy.common.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Error(
        String error,
        List<ErrorDetail> errorDetails
) {
    public Error(String error) {
        this(error, null);
    }

    public Error(List<ErrorDetail> errorDetails) {
        this(null, errorDetails);
    }
}