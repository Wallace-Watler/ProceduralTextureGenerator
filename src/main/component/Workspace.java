package main.component;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.Map;

public class Workspace implements ReactiveComponentParent {

	private final ReactiveComponentParent parent;

	@FXML
	private StackPane stackPane;

	public Workspace(ReactiveComponentParent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		stackPane = new StackPane();
	}

	@Override
	public Parent parentView() {
		return stackPane;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
