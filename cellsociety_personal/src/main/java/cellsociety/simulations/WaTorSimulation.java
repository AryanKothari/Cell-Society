package cellsociety.simulations;

import cellsociety.Grid;
import cellsociety.Simulation;

/**
 * Simulates the WaTor Cellular Automata. This Cellular Automata uses the Chronon, which is supposed
 * to represent a unit of time. In this version, it also represents the health/lifespan of a fish
 * and shark. If the chronon is below zero for a shark, it dies!
 * <p>
 * The rules are a bit complicated, so, we have two species:
 * <p>
 * Fishes, who's rules are: 1. Move to one adjacent square if there is space. 2. Reproduce by
 * leaving a fish in it's old space (after a certain number of time)
 * <p>
 * Sharks: 1. Move to a random adjacent square of fish, eating them (and gaining energy). If there
 * are no fish, just move like a fish. 2. On each move, lose energy. 3. Once energy is depleted, the
 * shark dies, leaving an empty space. 4. Reproduce like the fish-- after a certain number of time,
 * a new shark will take it's old place.
 * <p>
 * NOTE: HUNTING TAKES PRECEDENCE.
 * <p>
 * TODO: THIS DOES NOT IMPLEMENT REPRODUCTION OF SHARKS AND FISHES.
 *
 * @author tmh85
 */
public class WaTorSimulation extends Simulation {

    public static final int CELL_EMPTY = 0;
    public static final int CELL_FISH = 1;
    public static final int CELL_SHARK = 2;

    private static final int CHRONON_INDEX = 0;
    private static final int FISH_INIT_HEALTH = 1;

    private int fishReproduceTime;
    private int sharkReproduceTime;
    private int sharkEnergyGain;
    private int sharkDeathRate;
    private int sharkInitialHealth;


    public WaTorSimulation() {
        super();
    }

    public WaTorSimulation(double[] extraParameters) {
        super(extraParameters);
        fishReproduceTime = (int) getExtraParameters()[0];
        sharkReproduceTime = (int) getExtraParameters()[1];
        sharkEnergyGain = (int) getExtraParameters()[2];
        sharkDeathRate = (int) getExtraParameters()[3];
        sharkInitialHealth = (int) getExtraParameters()[4];
    }

    /**
     * checkRules will check if the cell is a shark or a fish, and then check for the rules as given
     * by the WaTor ruleset.
     *
     * @param grid     Grid that holds the cells for this simulation
     * @param rowIndex Current row index of a cell.
     * @param colIndex Current column index of a cell.
     */
    @Override
    protected void checkRules(Grid grid, int rowIndex, int colIndex) {
        int currCellChronon = grid.getExtraInfo(rowIndex, colIndex, CHRONON_INDEX);
        int currState = grid.cellState(rowIndex, colIndex);
        if (currState == CELL_SHARK) {
            if (killSharkIfDead(grid, rowIndex, colIndex,
                    grid.getExtraInfo(rowIndex, colIndex, CHRONON_INDEX))) {
                return;
            }
            if (currCellChronon == 0) {
                grid.updateExtraInfo(rowIndex, colIndex, CHRONON_INDEX, sharkInitialHealth);
            }
            sharkManeuvers(grid, rowIndex, colIndex, currCellChronon);
        } else if (currState == CELL_FISH) {
            if (currCellChronon == 0) {
                grid.updateExtraInfo(rowIndex, colIndex, CHRONON_INDEX, FISH_INIT_HEALTH);
            }
            fishManeuvers(grid, rowIndex, colIndex, currCellChronon);
        }
    }

    /**
     * "Moves" the cell to an adjacent location if the location exists and if the location is empty.
     *
     * @param grid           Grid that holds the cells for this simulation
     * @param rowIndex       Current row index of a cell.
     * @param colIndex       Current column index of a cell.
     * @param checkState     Cell state that we are looking for in a location (only moves here if the
     *                       state matches checkState)
     * @param newState       State that we want to set the new location to.
     * @param desiredChronon Chronon that we want to set the new cell to.
     * @return true if the cell did move, false if not.
     */
    private boolean moveCellToLocation(Grid grid, int rowIndex, int colIndex, int checkState,
                                       int newState, int desiredChronon) {
        int topRow = rowIndex - 1;
        int bottomRow = rowIndex + 1;
        int leftCol = colIndex - 1;
        int rightCol = colIndex + 1;

        boolean setCellSuccessfully = false;
        for (int currRow = topRow; currRow <= bottomRow; currRow++) {
            for (int currCol = leftCol; currCol <= rightCol; currCol++) {
                if (currCol == colIndex && currRow == rowIndex) {
                    continue;
                }
                if (safeCheckIfCellStateIsDesired(grid, currRow, currCol, checkState)) {
                    setCellSuccessfully = true;
                    setStateAtLocationAndUpdateChronon(grid, currRow, currCol, newState, desiredChronon);
                    grid.setCellNextState(rowIndex, colIndex, CELL_EMPTY);
                    return setCellSuccessfully;
                }
            }
        }

        return setCellSuccessfully;
    }

    /**
     * Checks if an adjacent cell matche sa given checkState and if the next cell state for that cell
     * is empty (meaning that it isn't already being modified by another cell)
     *
     * @param grid       Grid that contains the cells
     * @param desiredRow Row that contains the cell we want
     * @param desiredCol Column that contains the cell we want
     * @param checkState State we want to check for
     * @return True if the cell is within bounds, matches check state, and next cell state is empty.
     * False if not.
     */
    private boolean safeCheckIfCellStateIsDesired(Grid grid, int desiredRow, int desiredCol,
                                                  int checkState) {
        boolean isRowOutOfBounds = (desiredRow < 0) || (desiredRow >= grid.getRows());
        boolean isColOutOfBounds = (desiredCol < 0) || (desiredCol >= grid.getCols());
        if (isRowOutOfBounds || isColOutOfBounds) {
            return false;
        }
        boolean isNextCellStateEmpty = grid.nextCellState(desiredRow, desiredCol) == CELL_EMPTY;
        return (grid.cellState(desiredRow, desiredCol) == checkState) && isNextCellStateEmpty;
    }

    /**
     * Sets the state of a cell and updates its Chronon.
     *
     * @param grid           Grid that holds our cells
     * @param rowIndex       Row index that our cell resides on
     * @param colIndex       Column index that our cell resides on
     * @param desiredState   Desired state of our cell/
     * @param desiredChronon Chronon that we wish to update the cell with.
     */
    private void setStateAtLocationAndUpdateChronon(Grid grid, int rowIndex, int colIndex, int desiredState,
                                          int desiredChronon) {
        grid.setCellNextState(rowIndex, colIndex, desiredState);
        grid.updateExtraInfo(rowIndex, colIndex, desiredChronon, CHRONON_INDEX);
    }

    /**
     * Emulates the movements of a shark in WaTor. Shark checks for a fish, and if there's no fish, it
     * then checks for an empty location. If none of these apply, it will stay still and lose some
     * Chronon.
     *
     * @param grid            Grid that contains the cells
     * @param rowIndex        Index of the row that contains the cell
     * @param colIndex        Index of the column that contains the cell.
     * @param currCellChronon Current cell chronon of the shark.
     */
    private void sharkManeuvers(Grid grid, int rowIndex, int colIndex, int currCellChronon) {
        if (!checkForFish(grid, rowIndex, colIndex, currCellChronon + sharkEnergyGain)) {
            if (!checkForEmptyLocation(grid, rowIndex, colIndex, currCellChronon)) {
                int updatedChronon = currCellChronon - sharkDeathRate;
                grid.updateExtraInfo(rowIndex, colIndex, updatedChronon, CHRONON_INDEX);
            }
        }
    }

    /**
     * Wrapper for moveCellToLocation that checks specifically for fishes that a shark can eat.
     *
     * @param grid            Grid that contains the cells
     * @param rowIndex        Index of the row that contains the cell
     * @param colIndex        Index of the column that contains the cell.
     * @param currCellChronon Current cell chronon of the shark.
     * @return true if a fish was eaten, false if not
     */
    private boolean checkForFish(Grid grid, int rowIndex, int colIndex, int currCellChronon) {
        return moveCellToLocation(grid, rowIndex, colIndex, CELL_FISH, CELL_SHARK,
                currCellChronon - sharkEnergyGain);
    }

    /**
     * Checks for an empty location around a given cell.
     *
     * @param grid            Grid that contains the cells
     * @param rowIndex        Index of the row that contains the cell
     * @param colIndex        Index of the column that contains the cell.
     * @param currCellChronon Current cell chronon of the cell.
     * @return true if cell did move to an empty location, false if not.
     */
    private boolean checkForEmptyLocation(Grid grid, int rowIndex, int colIndex,
                                          int currCellChronon) {
        int currState = grid.cellState(rowIndex, colIndex);
        int desiredChronon;
        if (currState == CELL_FISH) {
            desiredChronon = currCellChronon + 1;
        } else {
            desiredChronon = currCellChronon - sharkDeathRate;
        }
        return moveCellToLocation(grid, rowIndex, colIndex, CELL_EMPTY, currState, desiredChronon);
    }

    /**
     * Checks to see if the shark should be dead, and kills it if it is.
     *
     * @param grid        Grid that contains the cells
     * @param rowIndex    Index of the row that contains the cell
     * @param colIndex    Index of the column that contains the cell.
     * @param currChronon Current cell chronon of the shark.
     * @return
     */
    private boolean killSharkIfDead(Grid grid, int rowIndex, int colIndex, int currChronon) {
        if (currChronon < 0) {
            grid.setCellNextState(rowIndex, colIndex, CELL_EMPTY);
            grid.updateExtraInfo(rowIndex, colIndex, 0, CHRONON_INDEX);
            return true;
        } else {
            grid.updateExtraInfo(rowIndex, colIndex, currChronon, CHRONON_INDEX);
            return false;
        }
    }

    /**
     * Emulates the maneuvers of a fish in the WaTor cellular automata. Fish checks for an empty
     * location, and if it doesn't find one, it stays put and gains chronon.
     *
     * @param grid            Grid that contains the cells
     * @param rowIndex        Index of the row that contains the cell
     * @param colIndex        Index of the column that contains the cell.
     * @param currCellChronon Current cell chronon of the fish.
     */
    private void fishManeuvers(Grid grid, int rowIndex, int colIndex, int currCellChronon) {
        if (!checkForEmptyLocation(grid, rowIndex, colIndex, currCellChronon)) {
            grid.setCellNextState(rowIndex, colIndex, CELL_FISH);
            grid.updateExtraInfo(rowIndex, colIndex, currCellChronon + 1, CHRONON_INDEX);
        }
    }

}
