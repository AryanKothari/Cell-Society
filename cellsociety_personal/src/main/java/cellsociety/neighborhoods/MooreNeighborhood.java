package cellsociety.neighborhoods;

import cellsociety.Grid;

/**
 * The basic Moore neighborhood is a cell with eight cells around it that are considered neighbors.
 * <p>
 * Methods were originally coded by ak616 for the Grid class, and was transferred to a Neighborhood
 * setting by tmh85.
 *
 * @author ak616
 * @author tmh85
 */
public class MooreNeighborhood implements Neighborhood {

    public static final int MAX_NUM_NEIGHBORS = 8;
    public static final int NUM_EDGE_NEIGHBORS = 5;
    public static final int NUM_CORNER_NEIGHBORS = 3;
    public static final int INVALID_INDEX = -515;

    /**
     * Finds the number of active neighbors of a cell that match a desired state for a moore
     * neighborhood.
     *
     * @param rowIndex     current row index of a cell.
     * @param colIndex     current col index of a cell.
     * @param desiredState state that the neighbors should be to be counted.
     * @param grid         grid that the cells are a part of.
     * @return number of neighbors that match a given state.
     */
    @Override
    public int calculateActiveNeighbours(int rowIndex, int colIndex, int desiredState, Grid grid) {
        int ret = 0;

        ret += checkTopNeighbors(rowIndex, colIndex, desiredState, grid);
        ret += checkMiddleNeighbors(rowIndex, colIndex, desiredState, grid);
        ret += checkBottomNeighbors(rowIndex, colIndex, desiredState, grid);

        return ret;
    }

    /**
     * Calculates how many total neighbor a cell has in a Moore neighborhood. If an edge, this returns
     * 5. If a corner, this returns 3. If neither, returns 8.
     *
     * @param rowIndex current row index of a cell
     * @param colIndex current col index of a cell
     * @param grid     grid that the cells are a part of
     * @return number of neighbors around a cell.
     */
    @Override
    public int calculateTotalNeighbours(int rowIndex, int colIndex, Grid grid) {
        int maxRowIndex = grid.getRows() - 1;
        int maxColIndex = grid.getCols() - 1;

        if (rowIndex == 0 || rowIndex == maxRowIndex) {
            return ((colIndex == 0 || colIndex == maxColIndex) ? NUM_CORNER_NEIGHBORS
                    : NUM_EDGE_NEIGHBORS);
        }
        if (colIndex == 0 || colIndex == maxColIndex) {
            return NUM_EDGE_NEIGHBORS;
        }
        return MAX_NUM_NEIGHBORS;
    }

    /**
     * Checks neighbors on top of a cell to see if they match a desired state. Ternary operations are
     * used to make sure we don't get an index out of bounds.
     *
     * @param rowIndex     current row index of a cell
     * @param colIndex     current col index of a cell
     * @param desiredState state that we wish to check for.
     * @param grid         grid that the cells are a part of
     */
    private int checkTopNeighbors(int rowIndex, int colIndex, int desiredState, Grid grid) {
        int runningTotal = 0;
        int topMiddleState = ((rowIndex - 1 >= 0) ? grid.cellState(rowIndex - 1, colIndex)
                : INVALID_INDEX);
        if (topMiddleState == desiredState) {
            runningTotal++;
        }

        int topRightState = ((rowIndex - 1 >= 0 && colIndex + 1 < grid.getCols())
                ? grid.cellState(rowIndex - 1, colIndex + 1) : INVALID_INDEX);
        if (topRightState == desiredState) {
            runningTotal++;
        }

        int topLeftState = ((rowIndex - 1 >= 0 && colIndex - 1 >= 0)
                ? grid.cellState(rowIndex - 1, colIndex - 1) : INVALID_INDEX);
        if (topLeftState == desiredState) {
            runningTotal++;
        }

        return runningTotal;
    }

    /**
     * Checks neighbors to the left and to the right of a cell for a desired state. Ternary operations
     * are used to make sure we don't get an index out of bounds.
     *
     * @param rowIndex     current row index of a cell
     * @param colIndex     current col index of a cell
     * @param desiredState state that we wish to check for.
     * @param grid         grid that the cells are a part of
     */
    private int checkMiddleNeighbors(int rowIndex, int colIndex, int desiredState, Grid grid) {
        int runningTotal = 0;
        int middleRightState = ((colIndex + 1 < grid.getCols()) ? grid.cellState(rowIndex, colIndex + 1)
                : INVALID_INDEX);
        if (middleRightState == desiredState) {
            runningTotal++;
        }

        int middleLeftState = ((colIndex - 1 >= 0) ? grid.cellState(rowIndex, colIndex - 1)
                : INVALID_INDEX);
        if (middleLeftState == desiredState) {
            runningTotal++;
        }

        return runningTotal;
    }

    /**
     * Checks neighbors below a cell to see if they match a desired state. Ternary operations are used
     * to make sure we don't get an index out of bounds.
     *
     * @param rowIndex     current row index of a cell
     * @param colIndex     current col index of a cell
     * @param desiredState state that we wish to check for.
     * @param grid         grid that the cells are a part of
     */
    private int checkBottomNeighbors(int rowIndex, int colIndex, int desiredState, Grid grid) {
        int runningTotal = 0;
        int bottomMiddleState = ((rowIndex + 1 < grid.getRows()) ? grid.cellState(rowIndex + 1,
                colIndex)
                : INVALID_INDEX);
        if (bottomMiddleState == desiredState) {
            runningTotal++;
        }

        int bottomRightState = ((rowIndex + 1 < grid.getRows() && colIndex + 1 < grid.getCols())
                ? grid.cellState(rowIndex + 1, colIndex + 1) : INVALID_INDEX);
        if (bottomRightState == desiredState) {
            runningTotal++;
        }

        int bottomLeftState = ((rowIndex + 1 < grid.getRows() && colIndex - 1 >= 0)
                ? grid.cellState(rowIndex + 1, colIndex - 1) : INVALID_INDEX);
        if (bottomLeftState == desiredState) {
            runningTotal++;
        }

        return runningTotal;
    }
}
