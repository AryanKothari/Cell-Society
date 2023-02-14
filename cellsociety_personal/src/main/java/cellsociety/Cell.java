package cellsociety;

import java.util.ArrayList;

/**
 * Cell class will be responsible for the behaviour of each individual cell
 *
 * @author ak616
 */

public class Cell {

    public static final int INITIAL_ARRAY_CAPACITY = 10;
    private int myCurrState;
    private int myNextState;
    private int[] myExtraInfo;

    //https://stackoverflow.com/questions/32350870/initialize-an-arraylist-with-zeros
    public Cell(int initialState) {
        this.myCurrState = initialState;
        myExtraInfo = new int[INITIAL_ARRAY_CAPACITY];
    }

    public int getCurrState() {
        return myCurrState;
    }

    public void setCurrState(int currState) {
        this.myCurrState = currState;
    }

    public int getNextState() {
        return myNextState;
    }

    public void setNextState(int nextState) {
        this.myNextState = nextState;
    }


    // extra info functions will be used to store extra values in a cell if a simulation so desires
    // it. --tmh85
    public Integer getExtraInfo(int index) {
        return myExtraInfo[index];
    }

    public void setExtraInfo(int index, int data) {
        myExtraInfo[index] = data;
    }
}
