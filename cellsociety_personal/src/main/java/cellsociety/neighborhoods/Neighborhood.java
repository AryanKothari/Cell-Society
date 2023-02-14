package cellsociety.neighborhoods;

import cellsociety.Grid;

/**
 * The Neighborhood class will be in charge of calculating the neighbors around a given cell. The
 * different kind of neighborhoods will be created using subclasses. This interface just provides
 * the skeleton.
 *
 * @author tmh85
 */
public interface Neighborhood {

    /**
     * Will calculate the number of neighbors of a certain state around a cell.
     *
     * @param rowIndex     current row index of a cell.
     * @param colIndex     current col index of a cell.
     * @param desiredState state that the neighbors should be to be counted.
     * @param grid         grid that the cells are a part of.
     * @return number of neighbors in the desiredState.
     */
    public int calculateActiveNeighbours(int rowIndex, int colIndex, int desiredState, Grid grid);

    /**
     * Will calculate the total number of neighbors around a cell. Note that this will include empty
     * cells.
     *
     * @param rowIndex current row index of a cell
     * @param colIndex current col index of a cell
     * @param grid     grid that the cells are a part of
     * @return number of total neighbors around a cell.
     */
    public int calculateTotalNeighbours(int rowIndex, int colIndex, Grid grid);

}
