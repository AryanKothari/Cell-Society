package cellsociety;

import javafx.scene.layout.GridPane;

import java.awt.*;

import static cellsociety.Main.DEFAULT_SIZE;

public class GridView extends Grid {

    private static final Dimension GRID_SIZE = new Dimension(DEFAULT_SIZE.width / 2,
            DEFAULT_SIZE.height / 2);

    private GridPane myGridPane;

    public GridView(FileInfo file) {
        super(file);
        myGridPane = new GridPane();
        myGridPane.setHgap(0);
        myGridPane.setVgap(0);
        myGridPane.getStyleClass().add("grid");
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }

    public void clear() {
        super.clear();
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                getCell(row, col).setColor();
            }
        }

    }

    public void update() {
        super.update();
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                getCell(row, col).setColor();
            }
        }
    }

    public void loadCells() {
        super.loadCells();
        int cellWidth = GRID_SIZE.width / getCols();
        int cellHeight = GRID_SIZE.height / getRows();
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                if (row < getRows() && col < getCols()) {
                    getCell(row, col).createView(cellWidth, cellHeight);
                    getCell(row, col).setColor();
                    myGridPane.add(getCell(row, col).getView(), col, row);
                }
            }
        }
    }
}

