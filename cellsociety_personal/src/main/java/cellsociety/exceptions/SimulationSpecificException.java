package cellsociety.exceptions;

/**
 * Exception that will occur when simulation parameters are not respected.
 *
 * @author tmh85
 */
public class SimulationSpecificException extends Exception {

    /**
     * Have an exception with no error message.
     */
    public SimulationSpecificException() {
        super();
    }

    /**
     * Have an exception with an error message
     *
     * @param errorMessage: Some string that'll contain details of the error.
     */
    public SimulationSpecificException(String errorMessage) {
        super(errorMessage);
    }
}
