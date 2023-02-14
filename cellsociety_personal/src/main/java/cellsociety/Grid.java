package cellsociety;

public class Grid {

    FileInfo myFile;

    private CellView[][] myCellView;

    private int myRows;
    private int myCols;

    public Grid(FileInfo file) {
        myFile = file;
        myCols = file.getWidth();
        myRows = file.getHeight();
        myCellView = new CellView[myRows][myCols];
    }

    public int getRows() {
        return myRows;
    }

    public int getCols() {
        return myCols;
    }

    public CellView getCell(int row, int col) {
        return myCellView[row][col];
    }

    public int cellState(int r, int c) {
        return myCellView[r][c].getCurrState();
    }

    public int nextCellState(int r, int c) {
        return myCellView[r][c].getNextState();
    }

    /**
     * update the next state of the cells
     */
    public void setCellNextState(int r, int c, int nextState) {
        myCellView[r][c].setNextState(nextState);
    }

    public void update() {
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                int updatedState = myCellView[row][col].getNextState();
                myCellView[row][col].setCurrState(updatedState);
            }
        }
    }

    /**
     * set all cells to DEAD (0)
     */
    public void clear() {
        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                myCellView[row][col].setCurrState(0);
            }
        }
    }


    /**
     * creates cells and sets up their initial state
     */
    public void loadCells() {
        int[][] initialConditions = myFile.getInitialConfigurations();

        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myCols; col++) {
                myCellView[row][col] = new CellView(initialConditions[row][col]);
            }
        }
    }

    // extrainfo functions will be used to store extra information in a cell if a simulation
    // so desires it. --tmh85
    public void updateExtraInfo(int r, int c, int data, int index) {
        myCellView[r][c].setExtraInfo(index, data);
    }

    public int getExtraInfo(int r, int c, int index) {
        return myCellView[r][c].getExtraInfo(index);
    }

}





