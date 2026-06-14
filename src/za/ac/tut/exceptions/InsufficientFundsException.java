package za.ac.tut.exceptions;

/**
 * Exception thrown when a withdrawal or transaction
 * cannot be completed due to insufficient funds.
 *
 * @author bdndi
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
