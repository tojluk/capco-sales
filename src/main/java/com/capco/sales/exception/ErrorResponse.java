package com.capco.sales.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response format for API errors.
 *
 * @param status the HTTP status code
 * @param message the error message
 * @param errors detailed field-level errors (for validation failures)
 * @param timestamp when the error occurred
 */
public record ErrorResponse(
    int status,
    String message,
    Map<String, String> errors,
    LocalDateTime timestamp
) {
}
