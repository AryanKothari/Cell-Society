package cellsociety.simulations;

import cellsociety.Grid;
import cellsociety.Simulation;

/**
 * Simulation for the Game of Life by John Horton Conway The rules are as follows (courtesy of
 * Wikipedia):
 * <p>
 * 1. Any live cell with fewer than two live neighbors dies, as if by underpopulation. 2. Any live
 * cell with two or three live neighbors lives on to the next generation. 3. Any live cell with more
 * than three live neighbors dies, as if by overpopulation. 4. Any dead cell with exactly three live
 * neighbors becomes a live cell, as if by reproduction.
 * <p>
 * These rules are condensed to be the following: 1. Any live cell with two or three live neighbors
 * survives. 2. Any dead cell with three live neighbors becomes a live cell. 3. All other live cells
 * die in the next generation. Similarly, all other dead cells stay dead.
 *
 * @author tmh85
 */
public class LifeSimulation extends Simulation {

    public static final int CELL_ALIVE = 1;
    public static final int CELL_DEAD = 0;

    public LifeSimulation(double[] extraParameters) {
        super(extraParameters);
    }

    public LifeSimulation() {
        super();
    }

    /**
     * Check if a cell should be alive or not according to the rules of Life.
     *
     * @param grid Grid that contains the cells we are checking the rules for.
     * @return true if cell should be alive, false if not.
     */
    @Override
    protected void checkRules(Grid grid, int rowIndex, int colIndex) {
        int decidedNextState = CELL_DEAD;
        int activeNeighbors = getNeighborhood().calculateActiveNeighbours(rowIndex, colIndex,
                CELL_ALIVE, grid);
        if (grid.cellState(rowIndex, colIndex) == CELL_ALIVE) {
            decidedNextState = checkForRuleOne(activeNeighbors);
        } else {
            decidedNextState = checkForRuleTwo(activeNeighbors);
        }

        grid.setCellNextState(rowIndex, colIndex, decidedNextState);
    }

    /**
     * Checks if a cell passes rule one of the condensed Life ruleset.
     *
     * @param activeNeighbors number of alive neighbors around the cell.
     * @return true if it passes, false if not.
     */
    private int checkForRuleOne(int activeNeighbors) {
        return ((activeNeighbors == 2) || (activeNeighbors == 3)) ? CELL_ALIVE : CELL_DEAD;
    }

    /**
     * Checks if a cell passes rule two of the condensed Life ruleset. Note that this rule should only
     * be used when we know if the cell is currently dead.
     *
     * @param activeNeighbors number of alive neighbors around the cell.
     * @return true if it passes, false if not
     */
    private int checkForRuleTwo(int activeNeighbors) {
        // Implicitly, we are also checking if the cell is currently dead. Done outside of this function though.
        return (activeNeighbors == 3) ? CELL_ALIVE : CELL_DEAD;
    }

}
