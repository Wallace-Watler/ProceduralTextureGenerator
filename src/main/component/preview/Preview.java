package main.component.preview;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.component.ReactiveComponent;

import java.util.Map;

public class Preview implements ReactiveComponent {

	public static final int IMAGE_WIDTH = 256;

	private final ReactiveComponent parent;

	@FXML
	private final ImageView imageView;

	public Preview(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		imageView = new ImageView();
		imageView.setFitWidth(IMAGE_WIDTH);
		imageView.setFitHeight(IMAGE_WIDTH);
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
