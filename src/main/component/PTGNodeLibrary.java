package main.component;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;

import java.util.Map;

public class PTGNodeLibrary implements ReactiveComponentParent {

	private final ReactiveComponentParent parent;

	@FXML
	private final Accordion accordion;

	public PTGNodeLibrary(ReactiveComponentParent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		accordion = new Accordion();
	}

	@Override
	public Parent parentView() {
		return accordion;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
