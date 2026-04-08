package no.ntnu.exception;

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
 * appropriate HTTP responses.
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
  public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
    logger.error("Validation error: ", ex);
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .reduce((msg1, msg2) -> msg1 + ", " + msg2)
        .orElse("Validation error occurred");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  /**
   * Handles all not-found exceptions.
   * Returns a 404 Not Found response with the exception message.
   *
   * @param ex the not-found exception
   * @return ResponseEntity with status 404 and error message
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    logger.error("Resource not found: ", ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handles data integrity violations (e.g. unique constraint failures).
   * Returns a 409 Conflict response.
   *
   * @param ex the data integrity violation exception
   * @return ResponseEntity with status 409 and error message
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    logger.error("Data integrity violation: ", ex);
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation occurred");
  }

  /**
   * Handles general database access errors.
   * Returns a 500 Internal Server Error response.
   *
   * @param ex the data access exception
   * @return ResponseEntity with status 500 and error message
   */
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
    logger.error("Data access error: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Data access error occurred");
  }

  /**
   * Catches any unhandled exceptions as a fallback.
   * Returns a 500 Internal Server Error response.
   *
   * @param ex the unexpected exception
   * @return ResponseEntity with status 500 and generic error message
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    logger.error("Unexpected error: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An unexpected error occurred");
  }
}
