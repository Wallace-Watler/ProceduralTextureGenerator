package main.component.workspace;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class PTGNode implements ReactiveComponentParent {

    private final ReactiveComponent parent;
    private double dragDeltaX, dragDeltaY;

    @FXML
    private final TitledPane titledPane;

    public PTGNode(ReactiveComponent parent, String name) {
        //----Init Model----//
        this.parent = parent;

        //----Init View----//
        titledPane = new TitledPane(name, null);

        //----Init Controller----//
        titledPane.setOnMousePressed(event -> {
            dragDeltaX = titledPane.getLayoutX() - event.getSceneX();
            dragDeltaY = titledPane.getLayoutY() - event.getSceneY();
            titledPane.getScene().setCursor(Cursor.MOVE);
        });

        titledPane.setOnMouseReleased(event -> titledPane.getScene().setCursor(Cursor.HAND));

        titledPane.setOnMouseDragged(event -> {
            titledPane.setLayoutX(event.getSceneX() + dragDeltaX);
            titledPane.setLayoutY(event.getSceneY() + dragDeltaY);
        });

        titledPane.setOnMouseEntered(event -> {
            if(!event.isPrimaryButtonDown()) titledPane.getScene().setCursor(Cursor.HAND);
        });

        titledPane.setOnMouseExited(event -> {
            if(!event.isPrimaryButtonDown()) titledPane.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    @Override
    public Parent parentView() {
        return titledPane;
    }

    @Override
    public Map<String, Property> state() {
        return parent.state();
    }
}
