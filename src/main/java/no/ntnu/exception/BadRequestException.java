package no.ntnu.exception;

/**
 * Exception thrown when a request contains invalid data or fails a business rule.
 */
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
