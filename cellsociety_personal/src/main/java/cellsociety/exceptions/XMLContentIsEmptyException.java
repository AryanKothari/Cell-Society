package cellsociety.exceptions;

/**
 * Exception that will occur when the contents of an XML tag is blank.
 *
 * @author tmh85
 */
public class XMLContentIsEmptyException extends Exception {

    /**
     * Have an exception with no error message.
     */
    public XMLContentIsEmptyException() {
        super();
    }

    /**
     * Have an exception with an error message
     *
     * @param errorMessage: Some string that'll contain details of the error.
     */
    public XMLContentIsEmptyException(String errorMessage) {
        super(errorMessage);
    }
}
