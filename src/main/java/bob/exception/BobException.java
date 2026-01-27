package bob.exception;

/**
 * Custom exception class for the Bob application.
 * Thrown when an error occurs during command parsing, execution, or data processing.
 */
public class BobException extends Exception {
    /**
     * Constructs a new BobException with the specified error message.
     * @param message the detail message describing the error
     */
    public BobException(String message) {
        super(message);
    }
}
