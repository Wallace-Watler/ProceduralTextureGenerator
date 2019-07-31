package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.component.Root;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Parent root = new Root(primaryStage).parentView();

		primaryStage.setTitle("Procedural Texture Generator");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
