package no.ntnu.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Structured error response returned by the
 * {@link GlobalExceptionHandler global exception handler}.
 */
@Schema(description = "Error response returned when a request fails")
public record ErrorResponse(

    @Schema(description = "HTTP status code", example = "404")
    int status,

    @Schema(description = "Error category", example = "Not Found")
    String error,

    @Schema(description = "Human-readable error message", example = "Extra feature with ID 5 not found")
    String message,

    @Schema(description = "Timestamp of the error", example = "2026-04-07T14:30:00")
    LocalDateTime timestamp

) {}
