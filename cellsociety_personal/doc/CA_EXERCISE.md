# Cell Society Design Lab Discussion
#### Aryan Kothari (ak616), Trevon Helm (tmh85), Charles Turpin


### Simulations

 * Commonalities
All are Cellular Automata; it’s all a collection of cells in a grid, and the next state is dependent on the neighbors of a cell.
 
 * Variations
Different set of rules that apply towards a specific simulation. 


### Discussion Questions

 * How does a Cell know what rules to apply for its simulation?

By checking the state of its neighbors.

 * How does a Cell know about its neighbors?

Each cell would have a row, and col ID. Based of that, it would know its neighboring cells by checking for their rows and columns

 * How can a Cell update itself without affecting its neighbors update?

The cell will save its next state into a separate instance variable, that will only be set to the current state at the same time as all other cells. (on the second pass.)

 * What behaviors does the Grid itself have?

Set grid state, clear grid, reset grid, pause simulation, change speed

 * How can a Grid update all the Cells it contains?

Check next state instance variable for each cell in a for loop that checks row by column then apply rule

 * What information about a simulation needs to be in the configuration file?

Simulation type, title, author, description, width/height of grid, parameter values of simulation

 * How is configuration information used to set up a simulation?
Provides the starting configuration of the simulation, which upon being “run” will update according to rules of the simulation

 * How is the graphical view of the simulation updated after all the cells have been updated?


Function that continuously checks if active_state is 0 or 1, and accordingly changes the color from black(dead) to white(alive)


### Alternate Designs

#### Design Idea #1

 * Data Structure #1 and File Format #1

 * Data Structure #2 and File Format #2


#### Design Idea #2

 * Data Structure #1 and File Format #1

 * Data Structure #2 and File Format #2
 

### High Level Design Goals
Keep constants in place (text file)
Cell class
Grid class
UI class
FileReader class
Simulation class



```java
public class Grid {
     // Returns the number of active neighbors around a given cell
     public int checkNeighbors (Cell [ ]  [ ] cell)
    //update the currentState and nextState values
     Public void updateCurrentStates()
     //clear the grid back to its initial state, with all cells set to dead
     Public void clear()
 }
 ```



```java
public class Cell {
     Boolean nextState;
     Boolean isEdge;
     Boolean currentState;
     // update the nextState of the Cell object
     public void updateNextState (boolean nextState)
     //returns the value of isEdge
     Public boolean getMiddleCell()
 }
```


```java
public class Simulation {
    Public Boolean checkRules(int numActiveNeighbours, Boolean isMiddleCell);
 }
```

```java
public class FileReader {
  //reads in information about
    Public double readExtraParameters(String myFilePath)
 }
```

```java
public class UI {
    //visually display the state of the simulation
    Public void showCurrentGrid()
 }
```

### Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
Int numberOfActiveNeighbors = myGrid.checkActiveNeighbors(Cell[][] cell);
Boolean makeCellActive = mySimulation.checkRules(int numActiveNeighbors, Boolean cell.getIsMiddleCell);
cell.updateNextState(Boolean makeCellActive);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
Int numberOfActiveNeighbors = myGrid.checkActiveNeighbors(Cell[][] cell);
Boolean makeCellActive = mySimulation.checkRules(int numActiveNeighbors, Boolean cell.getIsMiddleCell);
cell.updateNextState(Boolean makeCellActive);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
myGrid.updateCurrentStates();
myUI.showCurrentGrid();
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in a data file
```java
Double probCatch = myFileReader.readExtraParameters(String myFilePath);
mySimulation.updateParameters(double probCatch);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
myGrid.clear();
myFileReader.getSimulationInfo();
mySimulation.update();
boolean[][] startingConfig = myFileReader.getInitialConfiguration();
myGrid.loadInitial(boolean[][] startingConfig);
myUI.show();
```

