package main.component;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;

import java.util.Map;

public class PTGNodeLibrary implements ReactiveComponentParent {

	private ReactiveComponent parent;

	@FXML
	private Accordion accordion;

	public PTGNodeLibrary(ReactiveComponent parent) {
		this.parent = parent;

		//----Init View----//
		accordion = new Accordion();
	}

	@Override
	public Parent parentView() {
		return accordion;
	}

	@Override
	public Map<String, Object> state() {
		return parent.state();
	}
}
