package cellsociety.exceptions;

/**
 * Exception specifically if a requested neighborhood type is not found.
 *
 * @author tmh85
 */
public class NeighborhoodNotFoundException extends Exception {

    /**
     * Have an exception with no error message.
     */
    public NeighborhoodNotFoundException() {
        super();
    }

    /**
     * Have an exception with an error message
     *
     * @param errorMessage: Some string that'll contain details of the error.
     */
    public NeighborhoodNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
