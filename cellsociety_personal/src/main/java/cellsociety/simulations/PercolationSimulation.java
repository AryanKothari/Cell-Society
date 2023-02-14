package cellsociety.simulations;

import cellsociety.Grid;
import cellsociety.Simulation;

/**
 * Simulates the CompSci 201 Percolation assignment using Cellular Automata rules. The rules are as
 * follows: 1. If a cell is blocked, it can never be changed. 2. If a cell is percolated, it can
 * never be changed. 3. If a cell is open and one of its neighbors is percolated, it becomes
 * percolated.
 *
 * @author tmh85
 */
public class PercolationSimulation extends Simulation {

    public static final int CELL_BLOCKED = 0;
    public static final int CELL_PERCOLATED = 1;
    public static final int CELL_OPEN = 2;

    public PercolationSimulation(double[] extraParameters) {
        super(extraParameters);
    }

    public PercolationSimulation() {
        super();
    }

    /**
     * Checks if the current cell should be changed to percolated based on the rules above.
     *
     * @param grid     Grid that holds the cells for this simulation
     * @param rowIndex Current row index of a cell.
     * @param colIndex Current column index of a cell.
     */
    @Override
    protected void checkRules(Grid grid, int rowIndex, int colIndex) {

        int decidedNextState = makePercolatedIfNeeded(grid, rowIndex, colIndex);
        grid.setCellNextState(rowIndex, colIndex, decidedNextState);
    }

    /**
     * Makes a cell percolated if it has one or more percolated neighbors.
     *
     * @param grid     Grid that the cell is on. Needed to calculate it's percolated neighbors and set
     *                 its next state.
     * @param rowIndex Current row index of the cell.
     * @param colIndex Current column index of the cell
     */
    private int makePercolatedIfNeeded(Grid grid, int rowIndex, int colIndex) {
        int percolatedNeighbors = getNeighborhood().calculateActiveNeighbours(
                rowIndex, colIndex, CELL_PERCOLATED, grid);
        if (percolatedNeighbors >= 1 && grid.cellState(rowIndex, colIndex) == CELL_OPEN) {
            return CELL_PERCOLATED;
        } else {
            return grid.cellState(rowIndex, colIndex);
        }
    }

}
