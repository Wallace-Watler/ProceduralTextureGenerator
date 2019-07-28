package main.component;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.util.Map;

public class Workspace implements ReactiveComponentParent {

	private ReactiveComponent parent;

	@FXML
	private StackPane stackPane;

	public Workspace(ReactiveComponent parent) {
		this.parent = parent;

		//----Init View----//
		stackPane = new StackPane();
	}

	@Override
	public Parent parentView() {
		return stackPane;
	}

	@Override
	public Map<String, Object> state() {
		return parent.state();
	}
}
