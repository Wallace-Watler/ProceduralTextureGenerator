package main.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.customfx.LimitedIntegerTextField;

import java.util.Map;

public class NewAnimationRoot implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final VBox root;

	public NewAnimationRoot(ReactiveComponent parent, Stage stage) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("projectType").getValue() instanceof ProjectType;
		assert parentState.get("imageWidth") instanceof IntegerProperty;
		assert parentState.get("imageHeight") instanceof IntegerProperty;
		assert parentState.get("animationDuration") instanceof IntegerProperty;
		assert parentState.get("workingWithTileableX") instanceof BooleanProperty;
		assert parentState.get("workingWithTileableY") instanceof BooleanProperty;
		assert parentState.get("workingWithPeriodic") instanceof BooleanProperty;

		//----Init View----//
		final LimitedIntegerTextField widthInput = new LimitedIntegerTextField(9, "256");
		final LimitedIntegerTextField heightInput = new LimitedIntegerTextField(9, "256");
		final LimitedIntegerTextField durationInput = new LimitedIntegerTextField(9, "256");

		final CheckBox wrapX = new CheckBox();
		wrapX.setSelected(true);
		wrapX.setTooltip(new Tooltip("Check to ensure that the generated animation horizontally tiles seamlessly."));

		final CheckBox wrapY = new CheckBox();
		wrapY.setSelected(true);
		wrapY.setTooltip(new Tooltip("Check to ensure that the generated animation vertically tiles seamlessly."));

		final CheckBox periodic = new CheckBox();
		periodic.setSelected(true);
		periodic.setTooltip(new Tooltip("Check to ensure that the generated animation loops seamlessly."));

		final ColumnConstraints columnConstraints = new ColumnConstraints(145);
		final RowConstraints rowConstraints = new RowConstraints(30);

		final GridPane configuration = new GridPane();
		configuration.setPrefWidth(300);
		configuration.addRow(0, new Label("Width"), widthInput);
		configuration.addRow(1, new Label("Height"), heightInput);
		configuration.addRow(2, new Label("Frames"), durationInput);
		configuration.addRow(3, new Label("Wrap X"), wrapX);
		configuration.addRow(4, new Label("Wrap Y"), wrapY);
		configuration.addRow(5, new Label("Loop"), periodic);
		configuration.getColumnConstraints().addAll(columnConstraints, columnConstraints);
		configuration.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints);

		final Button ok = new Button("OK");
		final Button cancel = new Button("Cancel");
		final ButtonBar buttonBar = new ButtonBar();
		buttonBar.getButtons().add(ok);
		buttonBar.getButtons().add(cancel);
		buttonBar.setPrefHeight(40);

		root = new VBox(configuration, buttonBar);
		root.setPadding(new Insets(0, 10, 0, 10));

		//----Init Controller----//
		ok.setOnAction(event -> {
			int width = -1, height = -1, duration = -1;
			boolean improperInput = false;

			try {
				width = Integer.parseInt(widthInput.getText());
				if(width == 0) {
					widthInput.setText("");
					widthInput.setPromptText("Must be at least 1");
					improperInput = true;
				}
			} catch(NumberFormatException e) {
				widthInput.setPromptText("Required field");
				improperInput = true;
			}

			try {
				height = Integer.parseInt(heightInput.getText());
				if(height == 0) {
					heightInput.setText("");
					heightInput.setPromptText("Must be at least 1");
					improperInput = true;
				}
			} catch(NumberFormatException e) {
				heightInput.setPromptText("Required field");
				improperInput = true;
			}

			try {
				duration = Integer.parseInt(durationInput.getText());
				if(duration == 0) {
					durationInput.setText("");
					durationInput.setPromptText("Must be at least 1");
					improperInput = true;
				}
			} catch(NumberFormatException e) {
				durationInput.setPromptText("Required field");
				improperInput = true;
			}

			if(!improperInput) {
				((ObjectProperty<ProjectType>) parentState.get("projectType")).set(ProjectType.ANIMATION);
				((IntegerProperty) parentState.get("imageWidth")).set(Integer.parseInt(widthInput.getText()));
				((IntegerProperty) parentState.get("imageHeight")).set(Integer.parseInt(heightInput.getText()));
				((IntegerProperty) parentState.get("animationDuration")).set(Integer.parseInt(durationInput.getText()));
				((BooleanProperty) parentState.get("workingWithTileableX")).set(wrapX.isSelected());
				((BooleanProperty) parentState.get("workingWithTileableY")).set(wrapY.isSelected());
				((BooleanProperty) parentState.get("workingWithPeriodic")).set(periodic.isSelected());
				stage.close();
			}
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
