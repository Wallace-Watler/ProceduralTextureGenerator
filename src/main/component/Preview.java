package main.component;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;

import java.util.Map;

public class Preview implements ReactiveComponentParent {

	private ReactiveComponent parent;

	@FXML
	private ScrollPane scrollPane;

	public Preview(ReactiveComponent parent) {
		this.parent = parent;

		//----Init View----//
		scrollPane = new ScrollPane();
	}

	@Override
	public Parent parentView() {
		return scrollPane;
	}

	@Override
	public Map<String, Object> state() {
		return parent.state();
	}
}
