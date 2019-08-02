package main.component.preview;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.component.ReactiveComponent;

import java.util.Map;

public class Preview implements ReactiveComponent {

	private final ReactiveComponent parent;

	@FXML
	private final ImageView imageView;

	public Preview(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		imageView = new ImageView();
		imageView.setFitWidth(256);
		imageView.setFitHeight(256);
	}

	@Override
	public Node view() {
		return imageView;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}
}
