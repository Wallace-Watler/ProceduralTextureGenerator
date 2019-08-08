package main.component.preview;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
		final WritableImage image = new WritableImage(IMAGE_WIDTH, IMAGE_WIDTH);

		imageView = new ImageView(image);
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
