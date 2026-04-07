package no.ntnu.exception;

/**
 * Base exception thrown when a requested resource is not found.
 * Entity-specific not-found exceptions should extend this class.
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
