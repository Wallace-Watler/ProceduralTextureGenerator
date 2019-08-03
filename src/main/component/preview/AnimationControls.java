package main.component.preview;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.When;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.component.ProjectType;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.HashMap;
import java.util.Map;

public class AnimationControls implements ReactiveComponentParent {

	private final ReactiveComponent parent;
	private final AnimationTimer animationTimer;
	private final SimpleBooleanProperty playingAnimation;

	@FXML
	private final VBox vbox;

	public AnimationControls(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("projectType").getValue() instanceof ProjectType;
		assert parentState.get("workingWithPeriodic") instanceof BooleanProperty;
		assert parentState.get("animationDuration") instanceof IntegerProperty;
		assert parentState.get("currentFrame") instanceof IntegerProperty;

		playingAnimation = new SimpleBooleanProperty(false);

		//----Init View----//
		final ImageView playPauseImage = new ImageView();

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

		animationTimer = new AnimationTimer() {
			private long lastTime = System.nanoTime();

			@Override
			public void start() {
				super.start();
				lastTime = System.nanoTime();
			}

			@Override
			public void handle(long now) {
				// TODO: Look into why this seems to be slow
				final double fps = 60;
				long delta = now - lastTime;
				if(delta / (1_000_000_000.0 / fps) >= 1) {
					tick();
					lastTime = now;
				}
			}

			private void tick() {
				if(frameSlider.getValue() == frameSlider.getMax()) {
					if(((BooleanProperty) parentState.get("workingWithPeriodic")).get()) frameSlider.setValue(0);
					else playingAnimation.set(false);
				} else frameSlider.increment();
			}
		};

		//----Init Controller----//
		playPauseImage.imageProperty().bind(
				new When(playingAnimation)
						.then(new Image("resources/pause.png"))
						.otherwise(new Image("resources/play.png"))
		);

		previousFrame.disableProperty().bind(frameSlider.valueProperty().isEqualTo(0).and(((BooleanProperty) parentState.get("workingWithPeriodic")).not()));
		previousFrame.setOnAction(event -> {
			animationTimer.stop();
			playingAnimation.set(false);
			decrementWithLoop(frameSlider, (BooleanProperty) parentState.get("workingWithPeriodic"));
		});

		playPause.setOnAction(event -> {
			if(playingAnimation.get()) {
				playingAnimation.set(false);
				animationTimer.stop();
			} else {
				if(frameSlider.getValue() == frameSlider.getMax() && !((BooleanProperty) parentState.get("workingWithPeriodic")).get())
					frameSlider.setValue(0);
				playingAnimation.set(true);
				animationTimer.start();
			}
		});

		nextFrame.disableProperty().bind(frameSlider.valueProperty().isEqualTo(frameSlider.maxProperty()).and(((BooleanProperty) parentState.get("workingWithPeriodic")).not()));
		nextFrame.setOnAction(event -> {
			animationTimer.stop();
			playingAnimation.set(false);
			incrementWithLoop(frameSlider, (BooleanProperty) parentState.get("workingWithPeriodic"));
		});

		frameSlider.maxProperty().bind(((IntegerProperty) parentState.get("animationDuration")).add(-1));
		frameSlider.valueProperty().addListener((obs, oldval, newVal) -> frameSlider.setValue(newVal.intValue()));
		frameSlider.valueProperty().bindBidirectional(parentState.get("currentFrame"));

		frameLabel.textProperty().bind(frameSlider.valueProperty().asString("Frame: %.0f"));

		vbox.visibleProperty().bind(((ObjectProperty<ProjectType>) parentState.get("projectType")).isEqualTo(ProjectType.ANIMATION));
		vbox.managedProperty().bind(vbox.visibleProperty());
	}

	private static void incrementWithLoop(Slider slider, BooleanProperty periodic) {
		if(slider.getValue() == slider.getMax()) {
			if(periodic.get()) slider.setValue(0);
		} else slider.increment();
	}

	private static void decrementWithLoop(Slider slider, BooleanProperty periodic) {
		if(slider.getValue() == 0) {
			if(periodic.get()) slider.setValue(slider.getMax());
		} else slider.decrement();
	}

	@Override
	public Parent parentView() {
		return vbox;
	}

	@Override
	public Map<String, Property> state() {
		final Map<String, Property> state = new HashMap<>(parent.state());
		state.put("playingAnimation", playingAnimation);
		return state;
	}
}
