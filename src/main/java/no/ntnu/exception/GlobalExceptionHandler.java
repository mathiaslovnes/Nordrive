package no.ntnu.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Catches exceptions thrown by services and controllers and returns
 * structured {@link ErrorResponse} JSON responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger =
      LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles validation exceptions from {@code @Valid} annotations.
   * Returns a 400 Bad Request response with field-level error messages.
   *
   * @param ex the validation exception
   * @return ResponseEntity with status 400 and error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    logger.error("Validation error: ", ex);
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .reduce((msg1, msg2) -> msg1 + ", " + msg2)
        .orElse("Validation error occurred");
    return buildResponse(HttpStatus.BAD_REQUEST, errorMessage);
  }

  /**
   * Handles bad request exceptions from business logic validation.
   * Returns a 400 Bad Request response with the exception message.
   *
   * @param ex the bad request exception
   * @return ResponseEntity with status 400 and error message
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    logger.error("{}", ex.getMessage());
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  /**
   * Handles all not-found exceptions.
   * Returns a 404 Not Found response with the exception message.
   *
   * @param ex the not-found exception
   * @return ResponseEntity with status 404 and error message
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    logger.error("{}", ex.getMessage());
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  /**
   * Handles data integrity violations (e.g. unique constraint failures).
   * Returns a 409 Conflict response.
   *
   * @param ex the data integrity violation exception
   * @return ResponseEntity with status 409 and error message
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    logger.error("Data integrity violation: ", ex);
    return buildResponse(HttpStatus.CONFLICT, "Data integrity violation occurred");
  }

  /**
   * Handles general database access errors.
   * Returns a 500 Internal Server Error response.
   *
   * @param ex the data access exception
   * @return ResponseEntity with status 500 and error message
   */
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
    logger.error("Data access error: ", ex);
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Data access error occurred");
  }

  /**
   * Catches any unhandled exceptions as a fallback.
   * Returns a 500 Internal Server Error response.
   *
   * @param ex the unexpected exception
   * @return ResponseEntity with status 500 and generic error message
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    logger.error("Unexpected error: ", ex);
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
  }

  /**
   * Builds a structured {@link ErrorResponse} with the given status and message.
   *
   * @param status  the HTTP status
   * @param message the error message
   * @return ResponseEntity containing the error response
   */
  private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
    ErrorResponse error = new ErrorResponse(
        status.value(),
        status.getReasonPhrase(),
        message,
        LocalDateTime.now()
    );
    return ResponseEntity.status(status).body(error);
  }
}
