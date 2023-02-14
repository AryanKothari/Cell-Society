package cellsociety.fileinfos;

import cellsociety.FileInfo;
import cellsociety.exceptions.InvalidNumberException;
import cellsociety.exceptions.XMLContentIsEmptyException;
import cellsociety.exceptions.XMLTagNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * FileInfoXML will read a given XML file and load its contents into the class.
 *
 * @author tmh85
 */
// https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in-java
public class FileInfoXML extends FileInfo {

    private Element xmlContents;

    /**
     * Constructor will take an XML file and load all of its contents into the instance variables of
     * the class. Note that if the extra field is empty (or nonexistent), the simExtra parameter will
     * be NaN.
     *
     * @param file XML file that will be used to load a simulation.
     * @throws ParserConfigurationException for an invalid XML configuration
     * @throws SAXException                 for invalid XML data being entered
     * @throws InvalidNumberException       for a number out of bounds being entered in Width and
     *                                      Height (<= 0)
     * @throws XMLContentIsEmptyException   for a required XML tag that has no content.
     * @throws XMLTagNotFoundException      for a required XML tag that does not exist.
     */
    public FileInfoXML(File file, String language)
            throws ParserConfigurationException, SAXException, InvalidNumberException, XMLContentIsEmptyException, XMLTagNotFoundException {
        super(language);
        createXMLElement(file);
        loadXMLContentsInClass();
    }

    /**
     * Will take a given fileName and convert it to an Element class for use later.
     *
     * @param file Path to the requested XML file.
     * @throws SAXException XML syntax is malformed, or XML file is just wrong.
     */
    protected void createXMLElement(File file) throws SAXException, ParserConfigurationException {
        try {
            Document xmlFile = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            xmlContents = xmlFile.getDocumentElement();
        } catch (IOException | SAXException | ParserConfigurationException event) {
            // Incorrect XML file found. Return false, let the GUI handle this.
            throw new SAXException(myResources.getString("SAXError"), event);
        }
    }

    /**
     * Loads all expected contents of the XML file into the class.
     *
     * @throws XMLTagNotFoundException    If any tags (besides extra) are not found.
     * @throws InvalidNumberException     If any numerical tags have an invalid value.
     * @throws XMLContentIsEmptyException If the contents of any tags (besides extra) is empty
     * @throws NumberFormatException      If any numerical tag cannot be parsed as a number.
     */
    protected void loadXMLContentsInClass() throws XMLTagNotFoundException, InvalidNumberException,
            XMLContentIsEmptyException, NumberFormatException {
        loadBiographicalInformation();
        loadNumericalInformation();
        loadInitialConfiguration();
        loadExtraInformation();
    }

    /**
     * Loads all Biographical tags into the class. For now this includes type, title, author, and
     * description
     *
     * @throws XMLTagNotFoundException    when the given XML tag does not exist.
     * @throws XMLContentIsEmptyException when the given XML tag has nothing in it.
     */
    @Override
    protected void loadBiographicalInformation() throws XMLTagNotFoundException, XMLContentIsEmptyException {
        mySimType = getValuesAsText("type");
        mySimTitle = getValuesAsText("title");
        mySimAuthor = getValuesAsText("author");
        mySimDescription = getValuesAsText("description");
    }

    /**
     * Returns an integer from a given tag in an XML document.
     *
     * @param tagName name of the tag we want to extract data from.
     * @return integer contained with the given tagName in the XML document
     * @throws XMLTagNotFoundException    when the contents of the tag is empty
     * @throws InvalidNumberException     when the number obtained is less than or equal to zero.
     * @throws NumberFormatException      when the number obtained is not a valid integer.
     * @throws XMLContentIsEmptyException when the tag requested has no data
     */
    protected int getIntegerTag(String tagName) throws XMLTagNotFoundException, InvalidNumberException,
            NumberFormatException, XMLContentIsEmptyException {
        try {
            String num_text = getValuesAsText(tagName);
            int number = Integer.parseInt(num_text);
            if (number <= 0) {
                throw new InvalidNumberException(
                        String.format(myResources.getString("tagContentNotValidError"), tagName));
            } else {
                return number;
            }
        } catch (NumberFormatException event) {
            throw new NumberFormatException(
                    String.format(myResources.getString("integerNumberFormatError"), tagName));
        }

    }

    /**
     * Reads in the initial conditions from the XML file and creates simInitialConditions and
     * simInitialConditionsString
     *
     * @throws XMLContentIsEmptyException if initconfig is empty
     * @throws XMLTagNotFoundException    if initconfig is not found
     */
    @Override
    protected void loadInitialConfiguration()
            throws XMLContentIsEmptyException, XMLTagNotFoundException {
        mySimInitialConditionsAsString = removeStringBlankspace(getValuesAsText("initconfig"));
        String[] boolArray = mySimInitialConditionsAsString.replaceAll(" ", "").split(",");
        makeInitialConditionsArray(boolArray);
    }

    /**
     * Converts a string of 0's and 1's to a boolean array that gets saved to simInitialConditions.
     * Array width and height is based on simWidth and simHeight
     *
     * @param stringArray 1D array of 0's and 1's (in string form)
     */
    protected void makeInitialConditionsArray(String[] stringArray) {
        mySimInitialConditions = new int[getHeight()][getWidth()];
        int row_index;
        int col_index;
        String individualNumber;
        for (int i = 0; i < stringArray.length; i++) {
            col_index = i % getWidth();
            row_index = i / getWidth();    // probably a more efficient way to do this...
            individualNumber = stringArray[i].stripLeading().stripTrailing();
            mySimInitialConditions[row_index][col_index] = Integer.parseInt(individualNumber);
        }
    }

    /**
     * Finds all values for a tag and places them into a string. If the tag has multiple values spread
     * out over multiple tags, these will be separated in the string by tabs.
     *
     * @param tagName name of the tag
     * @return String that contains all values in a tag separated by commas
     * @throws XMLTagNotFoundException    if tag is not found
     * @throws XMLContentIsEmptyException if the tag is entirely empty.
     */
    protected String getValuesAsText(String tagName)
            throws XMLTagNotFoundException, XMLContentIsEmptyException {
        NodeList nodeList = xmlContents.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            throw new XMLTagNotFoundException(
                    String.format(myResources.getString("noTagFoundError"), tagName));
        }
        if (nodeList.item(0).getTextContent().length() == 0) {
            throw new XMLContentIsEmptyException(
                    String.format(myResources.getString("noContentInTagError"), tagName));
        }

        StringBuilder extraArguments = new StringBuilder();

        for (int i = 0; i < nodeList.getLength(); i++) {
            String singleItem = nodeList.item(i).getTextContent();
            if (singleItem.length() == 0) {
                continue; // don't append a blank element.
            }
            extraArguments.append(singleItem);
            extraArguments.append("\t");
        }
        return extraArguments.toString().stripTrailing();
    }

    /**
     * Loads in the extra value from the XML file. If not found, the extra value is by default NaN.
     *
     * @throws NumberFormatException when number cannot be parsed as a double.
     */
    @Override
    protected void loadExtraInformation() throws NumberFormatException {
        try {
            String extraString = getValuesAsText("extra");
            String[] splitString = extraString.split("\t");
            mySimExtra = new double[splitString.length];
            for (int i = 0; i < splitString.length; i++) {
                mySimExtra[i] = Double.parseDouble(splitString[i]);
            }
        } catch (XMLContentIsEmptyException | XMLTagNotFoundException event) {
            mySimExtra = new double[1];
            mySimExtra[0] = Double.NaN;
        } catch (NumberFormatException event) {
            throw new NumberFormatException(
                    String.format(myResources.getString("doubleNumberFormatError"), "extra"));
        }
    }

    /**
     * Removes tabs, spaces, and leading and trailing newline characters from a string. Inspired by
     * https://stackoverflow.com/questions/2163045/how-to-remove-line-breaks-from-a-file-in-java
     *
     * @param theString String with some blankspace in it
     * @return theString, but without the blankspace.
     */
    protected String removeStringBlankspace(String theString) {
        return theString.replaceAll("[\t ]", "").stripLeading().stripTrailing();
    }

    @Override
    /**
     * Wrapper function that will get Width and Height of a tag using getIntegerTag.
     */
    protected void loadNumericalInformation()
            throws InvalidNumberException, XMLContentIsEmptyException, XMLTagNotFoundException {
        mySimWidth = getIntegerTag("width");
        mySimHeight = getIntegerTag("height");
    }
}
