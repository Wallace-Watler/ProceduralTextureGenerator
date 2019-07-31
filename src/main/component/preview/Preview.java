package main.component.preview;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class Preview implements ReactiveComponentParent {

	private final ReactiveComponentParent parent;

	@FXML
	private ScrollPane scrollPane;

	public Preview(ReactiveComponentParent parent) {
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
