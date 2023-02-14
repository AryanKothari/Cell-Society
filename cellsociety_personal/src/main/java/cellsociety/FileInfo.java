package cellsociety;

import java.util.ResourceBundle;

/**
 * FileInfo classes will read a file for the required information and then hold all the data inside
 * the class. Subclasses will provide implementations for specific filetypes (example, FileInfoXML
 * reads XML files.) This base class contains the important instance variables and getter functions
 * for each piece of data.
 *
 * @author tmh85
 */
// Note: These sources helped me out with this class.
// Prof. Duvall's original main function for this class.
// Lab Bounce (input streams)
// https://www.baeldung.com/java-new-custom-exception
// https://stackoverflow.com/questions/12831530/getting-all-the-tags-in-an-xml-file-using-java

public abstract class FileInfo {

    protected String mySimTitle;
    protected String mySimType;
    protected String mySimAuthor;
    protected String mySimDescription;
    protected int mySimWidth;
    protected int mySimHeight;
    protected double[] mySimExtra;
    protected int[][] mySimInitialConditions;
    protected String mySimInitialConditionsAsString;
    protected ResourceBundle myResources;
    protected static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";

    public FileInfo(String language) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE +
                language.toLowerCase());
    }

    /**
     * Returns the type of the simulation read from the file as a String.
     *
     * @return simType
     */
    public String getType() {
        return mySimType;
    }

    /**
     * Returns the title of the simulation read from the file as a String.
     *
     * @return simTitle
     */
    public String getTitle() {
        return mySimTitle;
    }

    /**
     * Returns the author of the simulation read from the file as a String.
     *
     * @return simAuthor
     */
    public String getAuthor() {
        return mySimAuthor;
    }

    /**
     * Returns the description of the simulation read from the file as a String. Note that no String
     * manipulation is done when reading the description, so any special characters (or lack thereof)
     * will be present in this string.
     *
     * @return simDescription
     */
    public String getDescription() {
        return mySimDescription;
    }

    /**
     * Returns a 2D boolean array of the initial conditions described in the file.
     *
     * @return simInitialConditions
     */
    public int[][] getInitialConfigurations() {
        return mySimInitialConditions;
    }

    /**
     * Returns a String of the initial conditions as read from the file. This string has been cleaned
     * up a bit to eliminate unnecessary whitespace.
     *
     * @return simInitialConditionsString
     */
    public String getInitialConditionsAsString() {
        return mySimInitialConditionsAsString;
    }

    /**
     * Returns the width of the simulation grid as read from the file.
     *
     * @return simWidth
     */
    public int getWidth() {
        return mySimWidth;
    }

    /**
     * Returns the height of the simulation grid as read from the file.
     *
     * @return simHeight
     */
    public int getHeight() {
        return mySimHeight;
    }

    /**
     * Returns the extra parameter contained in the file. If there was no extra parameter, then this
     * will return NaN.
     *
     * @return simExtra
     */
    public double[] getExtraParameter() {
        return mySimExtra;
    }

    protected abstract void loadInitialConfiguration() throws Exception;

    protected abstract void loadBiographicalInformation() throws Exception;

    protected abstract void loadNumericalInformation() throws Exception;

    protected abstract void loadExtraInformation() throws Exception;

}
