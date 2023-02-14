package cellsociety.exceptions;

/**
 * Exception that will occur when an inputted number is out of bounds.
 *
 * @author tmh85
 */
public class InvalidNumberException extends Exception {

    /**
     * Have an exception with no error message.
     */
    public InvalidNumberException() {
        super();
    }

    /**
     * Have an exception with an error message
     *
     * @param errorMessage: Some string that'll contain details of the error.
     */
    public InvalidNumberException(String errorMessage) {
        super(errorMessage);
    }
}
