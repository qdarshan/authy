package org.arsh.auth.core.common.exception.model;

public record ErrorDetail(
        String field,
        String description
) {
}
