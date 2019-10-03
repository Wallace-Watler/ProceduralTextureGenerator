package main.component.workspace;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public class Workspace implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final ScrollPane scrollPane;

	public Workspace(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
        final AnchorPane anchorPane = new AnchorPane();
        scrollPane = new ScrollPane(anchorPane);
        scrollPane.setPannable(true);

        //----Init Controller----//
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
