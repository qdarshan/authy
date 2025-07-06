package org.arsh.authy.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(
        String status,
        String message,
        T data,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        ZonedDateTime timestamp
) {
    public static <T> Response<T> success(T data, String message) {
        return new Response<>("success", message, data, ZonedDateTime.now());
    }

    public static Response<?> success(String message) {
        return new Response<>("success", message, null, ZonedDateTime.now());
    }

    public static <T> Response<T> error(T data, String message) {
        return new Response<>("error", message, data, ZonedDateTime.now());
    }

    public static Response<?> error(String message) {
        return new Response<>("error", message, null, ZonedDateTime.now());
    }
}
