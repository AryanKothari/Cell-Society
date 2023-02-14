package cellsociety.fileinfos;

import cellsociety.exceptions.InvalidNumberException;
import cellsociety.exceptions.XMLContentIsEmptyException;
import cellsociety.exceptions.XMLTagNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * An extended version of FileInfoXML designed to support the additional initial configuration
 * options in the change document for this assignment.
 *
 * @author tmh85
 */
public class FileInfoXMLExtended extends FileInfoXML {

    /**
     * Constructor will take an XML file and load all of its contents into the instance variables of
     * the class. Note that if the extra field is empty (or nonexistent), the simExtra parameter will
     * be NaN.
     *
     * @param file     XML file that will be used to load a simulation.
     * @param language Language for properties file that you wish to use.
     * @throws ParserConfigurationException for an invalid XML configuration
     * @throws SAXException                 for invalid XML data being entered
     * @throws InvalidNumberException       for a number out of bounds being entered in Width and
     *                                      Height (<= 0)
     * @throws XMLContentIsEmptyException   for a required XML tag that has no content.
     * @throws XMLTagNotFoundException      for a required XML tag that does not exist.
     */

    public FileInfoXMLExtended(File file, String language)
            throws ParserConfigurationException, SAXException, InvalidNumberException, XMLContentIsEmptyException, XMLTagNotFoundException {
        super(file, language);
    }

    /**
     * Does same as superclass along with the ability to fill the array with a random distribution of
     * the given values.
     *
     * @throws XMLContentIsEmptyException
     * @throws XMLTagNotFoundException
     */
    @Override
    protected void loadInitialConfiguration()
            throws XMLContentIsEmptyException, XMLTagNotFoundException {
        mySimInitialConditionsAsString = removeStringBlankspace(getValuesAsText("initconfig"));
        String randomAsString = "random";
        String firstLetters = mySimInitialConditionsAsString.substring(0,
                Math.min(mySimInitialConditionsAsString.length(), randomAsString.length()));
        String[] boolArray = mySimInitialConditionsAsString.replaceAll(" ", "").split(",");
        if (firstLetters.equals(randomAsString)) {
            makeRandomInitialConditionsArray(boolArray);
        } else {
            makeInitialConditionsArray(boolArray);
        }

    }

    /**
     * Method will take a given array of possible values and then randomly set each value of the
     * initial conditions array to that element. Note that it is assumed that the first element of the
     * array is the string "random", so it is skipped.
     *
     * @param stringArray Array with first index being the string "Random", other indexes being the
     *                    possible states.
     */
    protected void makeRandomInitialConditionsArray(String[] stringArray) {
        ArrayList<Integer> conditionList = new ArrayList<>();
        // first element is random, so, let's get all the other elements.
        for (int i = 1; i < stringArray.length; i++) {
            conditionList.add(Integer.parseInt(stringArray[i]));
        }
        mySimInitialConditions = new int[getHeight()][getWidth()];

        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                mySimInitialConditions[row][col] = conditionList.get(getRandomIndex(conditionList.size()));
            }
        }

    }

    /**
     * Picks a random index given the length of a list. Gotten from
     * https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/ , thank you!
     *
     * @param listLength length of any list
     * @return index that was randomly chosen.
     */
    private int getRandomIndex(int listLength) {
        Random randElement = new Random();
        return randElement.nextInt(listLength);
    }

}
