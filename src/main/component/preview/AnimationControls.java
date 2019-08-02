package main.component.preview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class AnimationControls implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final VBox vbox;

	public AnimationControls(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("animationDuration") instanceof IntegerProperty;
		assert parentState.get("currentFrame") instanceof IntegerProperty;

		//----Init View----//
		final ImageView playPauseImage = new ImageView("resources/play.png");

		final Button previousFrame = new Button("", new ImageView("resources/previous_frame.png"));
		final Button playPause = new Button("", playPauseImage);
		final Button nextFrame = new Button("", new ImageView("resources/next_frame.png"));

		final Slider frameSlider = new Slider();
		frameSlider.setPadding(new Insets(0, 0, 0, 10));
		frameSlider.setBlockIncrement(1);

		final HBox controls = new HBox(previousFrame, playPause, nextFrame, frameSlider);
		controls.setPadding(new Insets(10, 0, 0, 0));
		controls.setAlignment(Pos.CENTER);

		final Label frameLabel = new Label();

		vbox = new VBox(controls, frameLabel);
		vbox.setAlignment(Pos.CENTER);

		//----Init Controller----//
		frameSlider.maxProperty().bind(((IntegerProperty) parentState.get("animationDuration")).add(-1));
		frameSlider.valueProperty().addListener((obs, oldval, newVal) -> frameSlider.setValue(newVal.intValue()));
		frameSlider.valueProperty().bindBidirectional(parentState.get("currentFrame"));

		frameLabel.textProperty().bind(frameSlider.valueProperty().asString("Frame: %.0f"));
	}

	@Override
	public Parent parentView() {
		return vbox;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
