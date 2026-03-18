package com.ncba.countryinfo.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Standard error response format for API errors.
 * Includes statusCode for consistency with HTTP status; clients can use either.
 */
@Schema(description = "Error response")
public record ErrorResponse(
        @Schema(description = "HTTP status code")
        int statusCode,
        @Schema(description = "Error category or code")
        String error,
        @Schema(description = "Human-readable error detail")
        String detail,
        @Schema(description = "Timestamp (ISO-8601)")
        String timestamp,
        @Schema(description = "Field-level validation errors (if any)")
        List<FieldError> fieldErrors
) {
    public ErrorResponse(int statusCode, String error, String detail) {
        this(statusCode, error, detail, Instant.now().toString(), null);
    }

    public ErrorResponse(int statusCode, String error, String detail, List<FieldError> fieldErrors) {
        this(statusCode, error, detail, Instant.now().toString(), fieldErrors);
    }

    @Schema(description = "Validation error for a specific field")
    public record FieldError(
            @Schema(description = "Field name")
            String field,
            @Schema(description = "Error message")
            String message
    ) {
    }
}
