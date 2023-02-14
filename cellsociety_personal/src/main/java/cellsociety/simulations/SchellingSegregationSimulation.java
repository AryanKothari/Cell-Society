package cellsociety.simulations;

import cellsociety.Grid;
import cellsociety.Simulation;

/**
 * Schelling's Model of Segregation intends to model how real life segregation can permeate through
 * a location through Cellular Automata. It follows the following rules: 1. If the fraction of
 * neighbors matching their group around them is greater than or equal to a given parameter (in our
 * case is matchPercent), the cell will stay put. 2. If not, they will teleport to any unused cell.
 * (Some models choose a location that has a fraction greater than matchPercent-- this one will just
 * pick an unused space for simplicity) NOTE: WHEN CHECKING FOR NEIGHBOR PERCENTAGE, IGNORE EMPTY
 * SPACES IN CALCULATION.
 *
 * @author tmh85
 */
public class SchellingSegregationSimulation extends Simulation {

    public static final int CELL_EMPTY = 0;
    public static final int CELL_GROUP_1 = 1;
    public static final int CELL_GROUP_2 = 2;
    private double matchPercent;

    public SchellingSegregationSimulation(double[] extraParameters) {
        super(extraParameters);
        matchPercent = extraParameters[0];
    }

    public SchellingSegregationSimulation() {
        super();
    }

    /**
     * Determines whether a cell should remain it's current state or turn empty based on Segregation
     * rules. Will also determine if this cell should "move" to an empty cell based on Segregation
     * rules.
     *
     * @param grid     Grid that holds the cells for this simulation
     * @param rowIndex Current row index of a cell.
     * @param colIndex Current column index of a cell.
     */
    @Override
    protected void checkRules(Grid grid, int rowIndex, int colIndex) {
        int currState = grid.cellState(rowIndex, colIndex);
        if (currState == CELL_EMPTY) {
            return;
        }

        int sameGroupNeighbors = getNeighborhood().calculateActiveNeighbours
                (rowIndex, colIndex, currState, grid);
        int allGroupNeighbors = getNumberOfNonEmptyNeighbors(rowIndex, colIndex, grid);

        double cellGroupMatchPercent = ((double) sameGroupNeighbors) / ((double) allGroupNeighbors);
        if (cellGroupMatchPercent >= matchPercent) {
            grid.setCellNextState(rowIndex, colIndex, currState);
        } else {
            grid.setCellNextState(rowIndex, colIndex, CELL_EMPTY);
            setCellToEmptyLocation(rowIndex, colIndex, currState, grid);
        }

    }

    /**
     * Will find an empty cell in the grid that has not already been claimed by another cell and will
     * set that new empty cell's next state to the current cell's state. (It moves the current cell to
     * an empty, non-claimed space)
     *
     * @param rowIndex     current row index of the cell.
     * @param colIndex     current col index of the cell
     * @param currentState current state of the cell
     * @param grid         grid that contains all the cells
     */
    private void setCellToEmptyLocation(int rowIndex, int colIndex, int currentState, Grid grid) {
        for (int row = rowIndex; row < grid.getRows(); row++) {
            for (int col = colIndex; col < grid.getCols(); col++) {
                if (grid.cellState(row, col) == CELL_EMPTY && !checkIfCellNextStateIsAGroup(rowIndex,
                        colIndex, grid)) {
                    grid.setCellNextState(row, col, currentState);
                    return;
                }
            }
        }
    }

    /**
     * Checks if the next cell state is any of the groups.
     *
     * @param rowIndex current cell row index
     * @param colIndex current cell column index
     * @param grid     grid that contains the cells
     * @return true if the cell is a part of a group, false if not.
     */
    private boolean checkIfCellNextStateIsAGroup(int rowIndex, int colIndex, Grid grid) {
        return (grid.nextCellState(rowIndex, colIndex) == CELL_GROUP_1) ||
                (grid.nextCellState(rowIndex, colIndex) == CELL_GROUP_2);
    }

    /**
     * Returns the number of non-empty neighbors around a cell.
     *
     * @param rowIndex row index of the current cell
     * @param colIndex column index of the current cell
     * @param grid     Grid that contains all the cells
     * @return number of non-empty neighbors
     */
    private int getNumberOfNonEmptyNeighbors(int rowIndex, int colIndex, Grid grid) {
        int totalNeighbors = getNeighborhood().calculateTotalNeighbours(rowIndex, colIndex, grid);
        int numEmptySpaces = getNeighborhood().calculateActiveNeighbours(
                rowIndex, colIndex, CELL_EMPTY, grid);
        return totalNeighbors - numEmptySpaces;
    }

}
