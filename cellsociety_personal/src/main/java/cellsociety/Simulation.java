package cellsociety;

import cellsociety.exceptions.NeighborhoodNotFoundException;
import cellsociety.neighborhoods.MooreNeighborhood;
import cellsociety.neighborhoods.Neighborhood;

/**
 * Simulation classes will contain the rules of a particular simulation to determine whether a cell
 * should be active or not. These classes also contains the speed of the simulation, and whether or
 * not a simulation should be running. There will be subclasses of each particular simulation.
 *
 * @author tmh85
 */
public abstract class Simulation {

    public static final String DEFAULT_NEIGHBORHOOD = "Moore";
    protected static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.";

    protected double mySimSpeed;
    protected boolean myRunnableSim;
    protected Neighborhood myNeighborhood;
    protected double[] myExtraParameters;

    /**
     * Default constructor for simulation will just set extra parameters to be null. If you need extra
     * parameters, provide a double[] to the constructor.
     */
    public Simulation() {
        myExtraParameters = null;
        myNeighborhood = new MooreNeighborhood();
    }

    /**
     * Saves extra parameters (in an array of doubles) to the object.
     *
     * @param extraValues array of extra parameters.
     */
    public Simulation(double[] extraValues) {
        myExtraParameters = extraValues;
        myNeighborhood = new MooreNeighborhood();
    }

    /**
     * Checks the rule set for a specific simulation and adjusts the grid accordingly. Subclasses are
     * required to implement this function.
     *
     * @param grid     Grid that holds the cells for this simulation
     * @param rowIndex Current row index of a cell.
     * @param colIndex Current column index of a cell.
     */
    protected abstract void checkRules(Grid grid, int rowIndex, int colIndex);


    /**
     * Set the simulation speed of the current simulation.
     *
     * @param speedToSet speed you desire the simulation to run at.
     */
    public void setRunSpeed(double speedToSet) {
        mySimSpeed = speedToSet;
    }

    /**
     * Get the simulation speed of the current simulation.
     *
     * @return current simulation speed
     */
    public double getRunSpeed() {
        return mySimSpeed;
    }

    /**
     * Stop the simulation by setting the isRunning flag to false.
     */
    public void pause() {
        myRunnableSim = false;
    }

    /**
     * Start the simulation by setting the isRunning flag to true.
     */
    public void makeRunnable() {
        myRunnableSim = true;
    }

    /**
     * Check if the simulation is running.
     *
     * @return true if running, false if not.
     */
    public boolean checkIfRunning() {
        return myRunnableSim;
    }

    public void runSimulation(GridView gridV) {
        if (checkIfRunning()) {
            checkRulesForWholeGrid(gridV);
            gridV.update();
        }
    }

    protected Neighborhood decideNeighborhood(String neighborhoodType)
            throws NeighborhoodNotFoundException {
        Neighborhood ourNeighborhood;
        switch (neighborhoodType) {
            case ("Moore") -> {
                ourNeighborhood = new MooreNeighborhood();
            }
            default -> {
                throw new NeighborhoodNotFoundException("Cannot find neighborhood " + neighborhoodType);
            }
        }
        return ourNeighborhood;
    }

    protected Neighborhood getNeighborhood() {
        return myNeighborhood;
    }

    protected void setNeighborhood(String neighborhoodType) throws NeighborhoodNotFoundException {
        myNeighborhood = decideNeighborhood(neighborhoodType);
    }


    protected double[] getExtraParameters() {
        return myExtraParameters;
    }

    private void checkRulesForWholeGrid(Grid grid) {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                checkRules(grid, row, col);
            }
        }
    }
}
