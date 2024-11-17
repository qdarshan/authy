package org.arsh.backend.exception.model;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorMessage(
        int status,
        String code,
        String error,
        Map<String, String> message,
        LocalDateTime timestamp
) {
    // Validation in the compact constructor
    public ErrorMessage {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Error code cannot be null or blank");
        }
        if (message == null) {
            throw new IllegalArgumentException("Error message cannot be null");
        }
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String code;
        private String error;
        private Map<String, String> message;
        private LocalDateTime timestamp = LocalDateTime.now(); // Default value

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }


        public Builder message(Map<String, String> message) {
            this.message = message;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorMessage build() {
            return new ErrorMessage(status, code, error, message, timestamp);
        }
    }
}