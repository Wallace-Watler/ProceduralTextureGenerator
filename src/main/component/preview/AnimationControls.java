package main.component.preview;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.When;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import main.component.ProjectType;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;
import main.customfx.LimitedDecimalTextField;

import java.util.HashMap;
import java.util.Map;

public class AnimationControls implements ReactiveComponentParent {

	private final ReactiveComponent parent;
	private final AnimationTimer animationTimer;
	private final SimpleBooleanProperty playingAnimation;

	@FXML
	private final GridPane gridPane;

	public AnimationControls(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("projectType").getValue() instanceof ProjectType;
		assert parentState.get("workingWithPeriodic") instanceof BooleanProperty;
		assert parentState.get("animationDuration") instanceof IntegerProperty;
		assert parentState.get("currentFrame") instanceof IntegerProperty;

		final ObjectProperty<ProjectType> projectType = (ObjectProperty<ProjectType>) parentState.get("projectType");
		final BooleanProperty workingWithPeriodic = (BooleanProperty) parentState.get("workingWithPeriodic");
		final IntegerProperty animationDuration = (IntegerProperty) parentState.get("animationDuration");
		final IntegerProperty currentFrame = (IntegerProperty) parentState.get("currentFrame");

		playingAnimation = new SimpleBooleanProperty(false);

		//----Init View----//
		final ImageView playPauseImage = new ImageView();

		final Button previousFrame = new Button("", new ImageView("resources/previous_frame.png"));
		final Button playPause = new Button("", playPauseImage);
		final Button nextFrame = new Button("", new ImageView("resources/next_frame.png"));

		final Slider frameSlider = new Slider();
		frameSlider.setPadding(new Insets(0, 0, 0, 10));
		frameSlider.setBlockIncrement(1);

		final LimitedDecimalTextField fpsInput = new LimitedDecimalTextField(3, 3, "20");
		fpsInput.setPrefWidth(60);

		final Label frameLabel = new Label();

		final HBox buttons = new HBox(previousFrame, playPause, nextFrame);
		buttons.setAlignment(Pos.CENTER);

		final HBox fps = new HBox(new Label("FPS: "), fpsInput);
		fps.setAlignment(Pos.CENTER);

		final ColumnConstraints columnConstraints = new ColumnConstraints();
		final RowConstraints rowConstraints = new RowConstraints();
		columnConstraints.setHalignment(HPos.CENTER);
		rowConstraints.setValignment(VPos.CENTER);

		gridPane = new GridPane();
		gridPane.addRow(0, buttons, frameSlider);
		gridPane.addRow(1, fps, frameLabel);
		gridPane.setVgap(4);
		gridPane.setPadding(new Insets(4));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);
		gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints);

		animationTimer = new AnimationTimer() {
			private long lastTime = System.nanoTime();
			private long delta = 0;

			@Override
			public void start() {
				super.start();
				lastTime = System.nanoTime();
			}

			@Override
			public void handle(long now) {
				delta += now - lastTime;
				final double nanosecondsPerFrame = 1_000_000_000 / fpsInput.getTextAsDouble();
				while(delta >= nanosecondsPerFrame) {
					nextFrame();
					delta -= nanosecondsPerFrame;
				}
				lastTime = now;
			}

			private void nextFrame() {
				if(frameSlider.getValue() == frameSlider.getMax()) {
					if(workingWithPeriodic.get()) frameSlider.setValue(0);
					else {
						playingAnimation.set(false);
						stop();
					}
				} else frameSlider.increment();
			}
		};

		//----Init Controller----//
		playPauseImage.imageProperty().bind(
				new When(playingAnimation)
						.then(new Image("resources/pause.png"))
						.otherwise(new Image("resources/play.png"))
		);

		previousFrame.disableProperty().bind(frameSlider.valueProperty().isEqualTo(0).and(workingWithPeriodic.not()));
		previousFrame.setOnAction(event -> {
			animationTimer.stop();
			playingAnimation.set(false);
			if(frameSlider.getValue() == 0) frameSlider.setValue(frameSlider.getMax());
			else frameSlider.decrement();
		});

		playPause.setOnAction(event -> {
			if(playingAnimation.get()) {
				playingAnimation.set(false);
				animationTimer.stop();
			} else {
				if(frameSlider.getValue() == frameSlider.getMax()) frameSlider.setValue(0);
				playingAnimation.set(true);
				animationTimer.start();
			}
		});

		nextFrame.disableProperty().bind(frameSlider.valueProperty().isEqualTo(frameSlider.maxProperty()).and(workingWithPeriodic.not()));
		nextFrame.setOnAction(event -> {
			animationTimer.stop();
			playingAnimation.set(false);
			if(frameSlider.getValue() == frameSlider.getMax()) frameSlider.setValue(0);
			else frameSlider.increment();
		});

		frameSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			animationTimer.stop();
			playingAnimation.set(false);
		});
		frameSlider.maxProperty().bind(animationDuration.add(-1));
		frameSlider.valueProperty().addListener((obs, oldval, newVal) -> frameSlider.setValue(newVal.intValue()));
		frameSlider.valueProperty().bindBidirectional(currentFrame);

		frameLabel.textProperty().bind(frameSlider.valueProperty().asString("Frame: %.0f"));

		gridPane.visibleProperty().bind(projectType.isEqualTo(ProjectType.ANIMATION));
		gridPane.managedProperty().bind(gridPane.visibleProperty());
	}

	@Override
	public Parent parentView() {
		return gridPane;
	}

	@Override
	public Map<String, Property> state() {
		final Map<String, Property> state = new HashMap<>(parent.state());
		state.put("playingAnimation", playingAnimation);
		return state;
	}
}
