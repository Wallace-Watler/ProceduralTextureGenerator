package main.component.preview;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class PreviewWindow implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private ScrollPane scrollPane;

	public PreviewWindow(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		scrollPane = new ScrollPane();
	}

	@Override
	public Parent parentView() {
		return scrollPane;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
