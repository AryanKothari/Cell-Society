package cellsociety;

import cellsociety.exceptions.SimulationSpecificException;
import cellsociety.fileinfos.*;
import cellsociety.simulations.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

import cellsociety.exceptions.InvalidNumberException;
import cellsociety.exceptions.XMLContentIsEmptyException;
import cellsociety.exceptions.XMLTagNotFoundException;
import javafx.application.Application;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

    /**
     * global variables created by ak616
     */
    public static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    public static final String DEFAULT_FILE_PATH = "./data/XML_files/GameOfLife/GameOfLife_3x3.xml";
    public static final String TITLE = "Cell Society";
    public static final String DEFAULT_LANGUAGE = "english";

    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start(Stage primaryStage)
            throws InvalidNumberException, ParserConfigurationException, XMLContentIsEmptyException, XMLTagNotFoundException, SAXException, SimulationSpecificException {
        File dataFile = new File(DEFAULT_FILE_PATH);
        FileInfo defaultFileInfo = new FileInfoXMLExtended(dataFile, DEFAULT_LANGUAGE);
        UI userInterface = new UI(defaultFileInfo,
                new LifeSimulation(defaultFileInfo.getExtraParameter()));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(
                userInterface.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height, primaryStage));
        primaryStage.show();
    }

    /**
     * Start the program, give complete control to JavaFX.
     * <p>
     * Default version of main() is actually included within JavaFX, so this is not technically
     * necessary!
     */
    public static void main(String[] args) {
        launch(args);
    }
}
