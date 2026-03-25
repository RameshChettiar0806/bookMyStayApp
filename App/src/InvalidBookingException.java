/**
 * CLASS - InvalidBookingException
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Custom exception for invalid booking scenarios.
 *
 * @version 9.8
 */
public class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}