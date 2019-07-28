package main.component;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

public class Root implements ReactiveComponentParent {

	private boolean workingWithTexture;
	private boolean workingWithAnimation;
	private boolean workingWithTileableX;
	private boolean workingWithTileableY;
	private boolean workingWithPeriodic;

	@FXML
	private BorderPane root;

	public Root() {
		workingWithTexture = false;
		workingWithAnimation = false;
		workingWithTileableX = false;
		workingWithTileableY = false;
		workingWithPeriodic = false;

		//----Init Model----//
		PTGNodeLibrary ptgNodeLibrary = new PTGNodeLibrary(this);
		Workspace workspace = new Workspace(this);
		Preview preview = new Preview(this);

		//----Init View----//
		final MenuItem newTexture = new MenuItem("New Texture...");
		final MenuItem newAnimation = new MenuItem("New Animation...");
		final MenuItem exit = new MenuItem("Exit");

		final Menu fileMenu = new Menu("File", null, newTexture, newAnimation, new SeparatorMenuItem(), exit);

		final TitledPane ptgNodeLibraryPane = new TitledPane("Library", ptgNodeLibrary.view());
		ptgNodeLibraryPane.setCollapsible(false);
		ptgNodeLibraryPane.setDisable(true);
		ptgNodeLibraryPane.setPrefWidth(100);
		ptgNodeLibraryPane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane workspacePane = new TitledPane("Workspace", workspace.view());
		workspacePane.setCollapsible(false);
		workspacePane.setDisable(true);
		workspacePane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane previewPane = new TitledPane("Preview", preview.view());
		previewPane.setCollapsible(false);
		previewPane.setDisable(true);
		previewPane.setPrefWidth(150);
		previewPane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane inspectorPane = new TitledPane("Inspector", null);
		inspectorPane.setCollapsible(false);
		inspectorPane.setDisable(true);
		inspectorPane.setPrefHeight(150);
		inspectorPane.setMaxHeight(Double.MAX_VALUE);

		root = new BorderPane(workspacePane, new MenuBar(fileMenu), previewPane, inspectorPane, ptgNodeLibraryPane);

		//----Init Controller----//
		exit.setOnAction(event -> System.exit(0));
	}

	@Override
	public Parent parentView() {
		return root;
	}

	@Override
	public Map<String, Object> state() {
		final Map<String, Object> state = new HashMap<>();
		state.put("workingWithTexture", workingWithTexture);
		state.put("workingWithAnimation", workingWithAnimation);
		state.put("workingWithTileableX", workingWithTileableX);
		state.put("workingWithTileableY", workingWithTileableY);
		state.put("workingWithPeriodic", workingWithPeriodic);
		return state;
	}
}
