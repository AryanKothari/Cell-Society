package cellsociety;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;

public class CellView extends Cell {

    private Rectangle myRect;
    private ResourceBundle myCellProperties;

    public CellView(int initialState) {
        super(initialState);
        myCellProperties = ResourceBundle.getBundle(UI.DEFAULT_RESOURCE_PACKAGE + "cell");
    }

    public void createView(int width, int height) {
        myRect = new Rectangle(width, height);
        myRect.setStroke(Color.WHITE);
        myRect.setFill(Color.valueOf(myCellProperties.getString("StrokeFill")));
        myRect.setStrokeWidth(Double.parseDouble(myCellProperties.getString("StrokeWidth")));
    }

    public Rectangle getView() {
        return myRect;
    }

    public void setColor() {
        Color color = Color.valueOf(myCellProperties.getString("Color" + getCurrState()));
        myRect.setFill(color);
    }
}
