package org.arsh.authy.common.exception.model;

public record ErrorDetail(
        String field,
        String description
) {
}
