package cellsociety;

import cellsociety.exceptions.InvalidNumberException;
import cellsociety.exceptions.SimulationSpecificException;
import cellsociety.exceptions.XMLContentIsEmptyException;
import cellsociety.exceptions.XMLTagNotFoundException;
import cellsociety.fileinfos.FileInfoXMLExtended;
import cellsociety.simulations.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.ResourceBundle;


public class UI {
    public static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";
    public static final String DEFAULT_RESOURCE_FOLDER =
            "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String STYLESHEET = "default.css";
    public static final String DATA_FILE_EXTENSION = "*.xml";
    public static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data/XML_files";
    public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
    public final static double DEFAULT_SPEED = 1.0;
    public final static double INCREMENT_SPEED_RATE = 0.2;


    FileInfo myFile;
    GridView myGridView;
    Simulation mySim;
    BorderPane myRoot;

    Popup myPopup;
    Text myTitle;

    private ResourceBundle myResources;
    private Timeline animation;


    public UI(FileInfo file, Simulation sim) {
        myFile = file;
        mySim = sim;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "labels");
    }

    /*
     * Inspiration for making Screen layout  from:
     * https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
     */
    public Scene makeScene(int width, int height, Stage primaryStage) {
        myRoot = new BorderPane();

        myRoot.setTop(makeTitleBar(primaryStage));
        myRoot.setBottom(makeControlButtons());
        myRoot.setCenter(makeGrid());
        updateDisplayInfo();


        mySim.setRunSpeed(DEFAULT_SPEED);
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(mySim.getRunSpeed()), e -> mySim.runSimulation(myGridView)));
        Scene scene = new Scene(myRoot, width, height);

        scene.getStylesheets()
                .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        return scene;
    }

    private Node makeTitleBar(Stage primaryStage) {
        HBox titleBar = new HBox();
        myTitle = new Text(myFile.getTitle());
        titleBar.getChildren().add(myTitle);

        Button myAboutButton;
        myAboutButton = makeButton("AboutButton", event -> AboutButtonPressed(primaryStage));
        titleBar.getChildren().add(myAboutButton);

        Button myFileChooserButton;
        myFileChooserButton = makeButton("FileChooserButton", event -> {
            try {
                ChooseFileButtonPressed(primaryStage);
            } catch (InvalidNumberException | ParserConfigurationException | XMLContentIsEmptyException
                     | XMLTagNotFoundException | SAXException | SimulationSpecificException e) {
                throw new RuntimeException(e);
            }
        });

        titleBar.getChildren().add(myFileChooserButton);
        return titleBar;
    }


    private Node makeControlButtons() {
        HBox controlButtons = new HBox();

        Button myPlayButton;
        myPlayButton = makeButton("PlayButton", event -> PlayButtonPressed());
        controlButtons.getChildren().add(myPlayButton);

        Button myClearButton;
        myClearButton = makeButton("ClearButton", event -> ClearButtonPressed());
        controlButtons.getChildren().add(myClearButton);

        Button myPauseButton;
        myPauseButton = makeButton("PauseButton", event -> PauseButtonPressed());
        controlButtons.getChildren().add(myPauseButton);

        Button mySpeedUpButton;
        mySpeedUpButton = makeButton("SpeedUpButton", event -> SpeedUpButtonPressed());
        controlButtons.getChildren().add(mySpeedUpButton);

        Button mySlowDownButton;
        mySlowDownButton = makeButton("SlowDownButton", event -> SlowDownButtonPressed());
        controlButtons.getChildren().add(mySlowDownButton);

        Button myStepButton;
        myStepButton = makeButton("StepButton", event -> StepButtonPressed());
        controlButtons.getChildren().add(myStepButton);

        return controlButtons;
    }

    private Node makeGrid() {
        myGridView = new GridView(myFile);
        myGridView.loadCells();
        return myGridView.getMyGridPane();
    }

    private void updateDisplayInfo() {
        Label myHeader = new Label(myFile.getType() + " | " + myFile.getAuthor() + " | " + myFile.getTitle());
        myPopup = new Popup();
        myHeader.setStyle(" -fx-background-color: white;");
        myPopup.getContent().add(myHeader);
    }

    private void StepButtonPressed() {
        PauseButtonPressed();
        animation.setCycleCount(1);
        mySim.makeRunnable();
        animation.play();
    }

    private void SlowDownButtonPressed() {
        if (mySim.getRunSpeed() > INCREMENT_SPEED_RATE) {
            mySim.setRunSpeed(mySim.getRunSpeed() - INCREMENT_SPEED_RATE);
        } else {
            mySim.setRunSpeed(DEFAULT_SPEED);
        }

        animation.setRate(mySim.getRunSpeed());
    }

    private void SpeedUpButtonPressed() {
        mySim.setRunSpeed(mySim.getRunSpeed() + INCREMENT_SPEED_RATE);
        animation.setRate(mySim.getRunSpeed());
    }


    private void PauseButtonPressed() {
        mySim.pause();
        animation.pause();
    }

    public void ClearButtonPressed() {
        mySim.pause();
        myGridView.clear();
        animation.stop();
        animation.setRate(DEFAULT_SPEED);
        myRoot.setCenter(makeGrid());
    }

    public void PlayButtonPressed() {
        mySim.makeRunnable();
        if (animation.getCycleCount() == 1) {
            animation.setCycleCount(Timeline.INDEFINITE);
        }
        animation.play();
    }

    private void AboutButtonPressed(Stage primaryStage) {
        if (!myPopup.isShowing())
            myPopup.show(primaryStage);
        else
            myPopup.hide();
    }

    private void ChooseFileButtonPressed(Stage primaryStage)
            throws InvalidNumberException, ParserConfigurationException, XMLContentIsEmptyException, XMLTagNotFoundException, SAXException, SimulationSpecificException {
        File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
        myFile = new FileInfoXMLExtended(dataFile, Main.DEFAULT_LANGUAGE);
        updateDisplayInfo();
        mySim.pause();
        mySim = decideSimulation(myFile.getType(), myFile.getExtraParameter());
        myRoot.setCenter(makeGrid());

    }


    private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;

    }

    /**
     * Decide on the simulation object to be created based on the current simulation type.
     *
     * @param simType         the type of simulation as read from the XML file
     * @param extraParameters the extra parameters as read from the XML file
     * @return the correct Simulation object for the simulation type.
     * @throws SimulationSpecificException if the given simType does not match the know simulation
     *                                     types.
     * @author tmh85
     */
    private Simulation decideSimulation(String simType, double[] extraParameters)
            throws SimulationSpecificException {
        switch (simType) {
            case ("Life") -> {
                return new LifeSimulation(extraParameters);
            }
            case ("Spreading Fire") -> {
                return new SpreadingFireSimulation(extraParameters);
            }
            case ("Percolation") -> {
                return new PercolationSimulation(extraParameters);
            }
            case ("WaTor") -> {
                return new WaTorSimulation(extraParameters);
            }
            case ("Segregation") -> {
                return new SchellingSegregationSimulation(extraParameters);
            }
            default -> {
                throw new SimulationSpecificException(
                        "The simulation type could not be found. Is your XML file correct?");
            }
        }
    }

    // set some sensible defaults when the FileChooser is created
    private static FileChooser makeChooser(String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(DATA_FILE_FOLDER));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter("Data Files", extensionAccepted));
        return result;
    }
}
