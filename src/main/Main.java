package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.controller.ImageViewController;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ImageViewController.init();
		Parent root = new AnchorPane(ImageViewController.getView());

		primaryStage.setTitle("Procedural Texture Generator");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
