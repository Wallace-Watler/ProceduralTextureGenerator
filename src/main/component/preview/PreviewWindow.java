package main.component.preview;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import main.component.ProjectType;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.HashMap;
import java.util.Map;

public class PreviewWindow implements ReactiveComponentParent {

	private final ReactiveComponent parent;
	private final SimpleIntegerProperty currentFrame;

	@FXML
	private final VBox vbox;

	public PreviewWindow(ReactiveComponent parent) {
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

		currentFrame = new SimpleIntegerProperty(0);

		final Preview preview = new Preview(this);
		final AnimationControls animationControls = new AnimationControls(this);

		//----Init View----//
		final Label widthLabel = new Label();
		final Label heightLabel = new Label();
		final Label durationLabel = new Label();
		final CheckBox wrapX = new CheckBox();
		final CheckBox wrapY = new CheckBox();
		final CheckBox periodic = new CheckBox();

		wrapX.setDisable(true);
		wrapY.setDisable(true);
		periodic.setDisable(true);

		final ColumnConstraints columnConstraints = new ColumnConstraints(60);
		final RowConstraints rowConstraints = new RowConstraints(20);

		final GridPane configuration = new GridPane();
		configuration.setPrefWidth(200);
		configuration.setPadding(new Insets(4, 16, 4, 16));
		configuration.addRow(0, new Label("Width:"), widthLabel);
		configuration.addRow(1, new Label("Height:"), heightLabel);
		configuration.addRow(2, new Label("Frames:"), durationLabel);
		configuration.addRow(3, new Label("Wrap X:"), wrapX);
		configuration.addRow(4, new Label("Wrap Y:"), wrapY);
		configuration.addRow(5, new Label("Loop:"), periodic);
		configuration.getColumnConstraints().addAll(columnConstraints, columnConstraints);
		configuration.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints);

		final ScrollPane scrollPane = new ScrollPane(configuration);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		vbox = new VBox(preview.view(), animationControls.view(), scrollPane);
		vbox.setPadding(new Insets(10, -1, -1, -1));
		vbox.setAlignment(Pos.CENTER);

		//----Init Controller----//
		widthLabel.textProperty().bind(((IntegerProperty) parentState.get("imageWidth")).asString());
		heightLabel.textProperty().bind(((IntegerProperty) parentState.get("imageHeight")).asString());
		durationLabel.textProperty().bind(((IntegerProperty) parentState.get("animationDuration")).asString());
		wrapX.selectedProperty().bindBidirectional((BooleanProperty) parentState.get("workingWithTileableX"));
		wrapY.selectedProperty().bindBidirectional((BooleanProperty) parentState.get("workingWithTileableY"));
		periodic.selectedProperty().bindBidirectional((BooleanProperty) parentState.get("workingWithPeriodic"));

		vbox.visibleProperty().bind(((ObjectProperty<ProjectType>) parentState.get("projectType")).isNotEqualTo(ProjectType.NONE));
	}

	@Override
	public Parent parentView() {
		return vbox;
	}

	@Override
	public Map<String, Property> state() {
		final Map<String, Property> state = new HashMap<>(parent.state());
		state.put("currentFrame", currentFrame);
		return state;
	}
}
