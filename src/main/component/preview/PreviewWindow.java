package main.component.preview;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
		currentFrame = new SimpleIntegerProperty(0);

		final Preview preview = new Preview(this);
		final AnimationControls animationControls = new AnimationControls(this);

		//----Init View----//
		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		vbox = new VBox(preview.view(), animationControls.view(), scrollPane);
		vbox.setPadding(new Insets(-1, -1, -1, -1));
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
