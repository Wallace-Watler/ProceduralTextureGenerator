package main.component.nodelibrary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.component.ProjectType;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Map;

public abstract class LibraryItem implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final VBox vbox;

	public LibraryItem(ReactiveComponent parent, String name, String imageUrl) {
		//----Init Model----//
		this.parent = parent;
		final Map<String, Property> parentState = parent.state();
		assert parentState.get("projectType").getValue() instanceof ProjectType;

		//----Init View----//
		vbox = new VBox(new ImageView(imageUrl), new Label(name));
		vbox.setAlignment(Pos.CENTER);

		//----Init Controller----//
		vbox.visibleProperty().bind(showForProjectType(((ObjectProperty<ProjectType>) parentState.get("projectType"))));
		vbox.managedProperty().bind(vbox.visibleProperty());
	}

	protected abstract ObservableValue<? extends Boolean> showForProjectType(ObjectProperty<ProjectType> projectType);

	@Override
	public Parent parentView() {
		return vbox;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
