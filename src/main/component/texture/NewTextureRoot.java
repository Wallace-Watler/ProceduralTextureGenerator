package main.component.texture;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class NewTextureRoot implements ReactiveComponentParent {

	private final ReactiveComponentParent parent;

	@FXML
	private VBox root;

	public NewTextureRoot(ReactiveComponentParent parent, Stage stage) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("workingWithTileableX") instanceof BooleanProperty;
		assert parentState.get("workingWithTileableY") instanceof BooleanProperty;

		//----Init View----//
		final TextField widthInput = new TextField();
		widthInput.setText("256");

		final TextField heightInput = new TextField();
		heightInput.setText("256");

		final CheckBox wrapX = new CheckBox();
		wrapX.setSelected(true);
		wrapX.setTooltip(new Tooltip("Check to ensure that the generated texture horizontally tiles seamlessly."));

		final CheckBox wrapY = new CheckBox();
		wrapY.setSelected(true);
		wrapY.setTooltip(new Tooltip("Check to ensure that the generated texture vertically tiles seamlessly."));

		final ColumnConstraints columnConstraints = new ColumnConstraints(145);
		final RowConstraints rowConstraints = new RowConstraints(30);

		final GridPane configuration = new GridPane();
		configuration.setPrefWidth(300);
		configuration.addRow(0, new Label("Width"), widthInput);
		configuration.addRow(1, new Label("Height"), heightInput);
		configuration.addRow(2, new Label("Wrap X"), wrapX);
		configuration.addRow(3, new Label("Wrap Y"), wrapY);
		configuration.getColumnConstraints().addAll(columnConstraints, columnConstraints);
		configuration.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints);

		final Button ok = new Button("OK");
		final Button cancel = new Button("Cancel");
		final ButtonBar buttonBar = new ButtonBar();
		buttonBar.getButtons().add(ok);
		buttonBar.getButtons().add(cancel);
		buttonBar.setPrefHeight(40);

		root = new VBox(configuration, buttonBar);
		root.setPadding(new Insets(0, 10, 0, 10));

		//----Init Controller----//
		widthInput.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")) widthInput.setText(newValue.replaceAll("[^\\d]", ""));
		});

		heightInput.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")) heightInput.setText(newValue.replaceAll("[^\\d]", ""));
		});

		ok.setOnAction(event -> {
			((BooleanProperty) parentState.get("workingWithTileableX")).set(wrapX.isSelected());
			((BooleanProperty) parentState.get("workingWithTileableY")).set(wrapY.isSelected());
			//TODO: Somehow trigger parent to update (probably should wrap state into a separate class)
			stage.close();
		});

		cancel.setOnAction(event -> stage.close());
	}

	@Override
	public Parent parentView() {
		return root;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
