package cellsociety.simulations;

import cellsociety.Grid;
import cellsociety.Simulation;
import cellsociety.exceptions.SimulationSpecificException;

/**
 * Contains the rules for the Spreading Fire (forest-fire model) simulation. The rules are as
 * follows: 1. A burning cell turns into an empty cell. 2. A tree with burn if at least one neighbor
 * is burning. 3. A tree ignites with probability f even if no neighbor is burning. 4. An empty
 * space fills a tree with probability p.
 *
 * @author tmh85
 */
public class SpreadingFireSimulation extends Simulation {

    public static final int CELL_BURNING = 1;
    public static final int CELL_TREE = 2;
    public static final int CELL_EMPTY = 0;
    public double probCatch;
    public double probGrow;

    /**
     * Create a Spreading Fire simulation class with extra parameters probCatch and probGrow
     *
     * @param extraParameters 2 element double array with values from 0-1.
     */
    public SpreadingFireSimulation(double[] extraParameters) {
        super(extraParameters);
        probCatch = getExtraParameters()[0];
        probGrow = getExtraParameters()[1];
    }

    /**
     * This constructor should never be used, as extra parameters are a requirement for this
     * simulation. If used, it will just error out.
     *
     * @throws SimulationSpecificException when constructor is used without an argument.
     */
    public SpreadingFireSimulation() throws SimulationSpecificException {
        super();
        throw new SimulationSpecificException(
                "Spreading Fire simulation requires extra parameters to be added, " +
                        "specifically a double array of two elements.");
    }

    @Override
    protected void checkRules(Grid grid, int rowIndex, int colIndex) {
        int desiredNextState;
        int burningNeighbors = getNeighborhood().calculateActiveNeighbours(
                rowIndex, colIndex, CELL_BURNING, grid);
        int currentCellStatus = grid.cellState(rowIndex, colIndex);

        if (currentCellStatus == CELL_BURNING) {
            desiredNextState = CELL_EMPTY;
        } else if (burningNeighbors >= 1 && currentCellStatus == CELL_TREE) {
            desiredNextState = CELL_BURNING;
        } else if (currentCellStatus == CELL_TREE) {
            desiredNextState = rollTheDice(probCatch, currentCellStatus, CELL_BURNING);
        } else if (currentCellStatus == CELL_EMPTY) {
            desiredNextState = rollTheDice(probGrow, currentCellStatus, CELL_TREE);
        } else {
            desiredNextState = currentCellStatus;
        }

        grid.setCellNextState(rowIndex, colIndex, desiredNextState);
    }

    /**
     * Will randomly decide if a desired cell outcome is chosen based on a probability. Inspired by
     * https://stackoverflow.com/questions/10368202/java-how-do-i-simulate-probability#10368388
     *
     * @param probability       probability that a desired outcome with occur
     * @param currentCellStatus current status of the cell
     * @param desiredOutcome    what we wish the cell will change to.
     * @return if luck is on our side, the desired outcome. Else, currentCellStatus.
     */
    private int rollTheDice(double probability, int currentCellStatus, int desiredOutcome) {
        if (Math.random() < probability) {
            return currentCellStatus;
        } else {
            return desiredOutcome;
        }
    }

}
