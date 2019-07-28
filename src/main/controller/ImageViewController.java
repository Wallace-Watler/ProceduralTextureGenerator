package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import main.model.Model;

public final class ImageViewController {

	@FXML
	private static ImageView imageView = new ImageView();

	public static void init() {
		imageView.setImage(Model.getImage());
	}

	public static Node getView() {
		return imageView;
	}
}
