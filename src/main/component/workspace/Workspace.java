package main.component.workspace;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class Workspace implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final StackPane stackPane;

	public Workspace(ReactiveComponent parent) {
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
