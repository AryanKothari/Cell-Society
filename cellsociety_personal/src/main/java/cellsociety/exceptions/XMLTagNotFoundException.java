package cellsociety.exceptions;

/**
 * Exception that will occur when a requested XML tag is not found.
 *
 * @author tmh85
 */
public class XMLTagNotFoundException extends Exception {

    /**
     * Have an exception with no error message.
     */
    public XMLTagNotFoundException() {
        super();
    }

    /**
     * Have an exception with an error message
     *
     * @param errorMessage: Some string that'll contain details of the error.
     */
    public XMLTagNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
