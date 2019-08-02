package main.component.preview;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.HashMap;
import java.util.Map;

public class PreviewWindow implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	private final SimpleIntegerProperty currentFrame;

	@FXML
	private final ScrollPane scrollPane;

	public PreviewWindow(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;
		currentFrame = new SimpleIntegerProperty(0);

		final Preview preview = new Preview(this);
		final AnimationControls animationControls = new AnimationControls(this);

		//----Init View----//
		final Separator separator = new Separator();
		separator.setPadding(new Insets(4, 0, 4, 0));

		final VBox vbox = new VBox(preview.view(), animationControls.view(), separator);
		vbox.setPadding(new Insets(10));

		scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	}

	@Override
	public Parent parentView() {
		return scrollPane;
	}

	@Override
	public Map<String, Property> state() {
		final Map<String, Property> state = new HashMap<>(parent.state());
		state.put("currentFrame", currentFrame);
		return state;
	}
}
