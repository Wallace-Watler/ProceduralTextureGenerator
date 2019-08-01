package main.component;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.component.nodelibrary.PTGNodeLibrary;
import main.component.preview.Preview;
import main.component.workspace.Workspace;

import java.util.HashMap;
import java.util.Map;

public class Root implements ReactiveComponentParent {

	private final SimpleObjectProperty<ProjectType> projectType;
	private final SimpleIntegerProperty imageWidth;
	private final SimpleIntegerProperty imageHeight;
	private final SimpleIntegerProperty animationDuration;
	private final SimpleBooleanProperty workingWithTileableX;
	private final SimpleBooleanProperty workingWithTileableY;
	private final SimpleBooleanProperty workingWithPeriodic;

	@FXML
	private VBox root;

	public Root(Stage stage) {
		//----Init Model----//
		projectType = new SimpleObjectProperty<>(ProjectType.NONE);
		imageWidth = new SimpleIntegerProperty(1);
		imageHeight = new SimpleIntegerProperty(1);
		animationDuration = new SimpleIntegerProperty(1);
		workingWithTileableX = new SimpleBooleanProperty(false);
		workingWithTileableY = new SimpleBooleanProperty(false);
		workingWithPeriodic = new SimpleBooleanProperty(false);

		PTGNodeLibrary ptgNodeLibrary = new PTGNodeLibrary(this);
		Workspace workspace = new Workspace(this);
		Preview preview = new Preview(this);

		//----Init View----//
		final MenuItem newTexture = new MenuItem("New Texture...");
		final MenuItem newAnimation = new MenuItem("New Animation...");
		final MenuItem exit = new MenuItem("Exit");

		final Menu fileMenu = new Menu("File", null, newTexture, newAnimation, new SeparatorMenuItem(), exit);

		final BooleanBinding panesDisabledProperty = projectType.isEqualTo(ProjectType.NONE);

		final TitledPane ptgNodeLibraryPane = new TitledPane("Library", ptgNodeLibrary.view());
		ptgNodeLibraryPane.setCollapsible(false);
		ptgNodeLibraryPane.disableProperty().bind(panesDisabledProperty);
		ptgNodeLibraryPane.setPrefWidth(100);
		ptgNodeLibraryPane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane workspacePane = new TitledPane("Workspace", workspace.view());
		workspacePane.setCollapsible(false);
		workspacePane.disableProperty().bind(panesDisabledProperty);
		workspacePane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane previewPane = new TitledPane("Preview", preview.view());
		previewPane.setCollapsible(false);
		previewPane.disableProperty().bind(panesDisabledProperty);
		previewPane.setPrefWidth(150);
		previewPane.setMaxHeight(Double.MAX_VALUE);

		final TitledPane inspectorPane = new TitledPane("Inspector", null);
		inspectorPane.setCollapsible(false);
		inspectorPane.disableProperty().bind(panesDisabledProperty);
		inspectorPane.setPrefHeight(150);
		inspectorPane.setMaxHeight(Double.MAX_VALUE);

		final SplitPane workspaceAndInspector = new SplitPane(workspacePane, inspectorPane);
		workspaceAndInspector.setOrientation(Orientation.VERTICAL);
		workspaceAndInspector.setDividerPositions(0.8);

		final SplitPane workspaceInspectorLibraryPreview = new SplitPane(ptgNodeLibraryPane, workspaceAndInspector, previewPane);
		workspaceInspectorLibraryPreview.setOrientation(Orientation.HORIZONTAL);
		workspaceInspectorLibraryPreview.setDividerPositions(0.1, 0.85);
		VBox.setVgrow(workspaceInspectorLibraryPreview, Priority.ALWAYS);

		root = new VBox(new MenuBar(fileMenu), workspaceInspectorLibraryPreview);

		//----Init Controller----//
		newTexture.setOnAction(event -> {
			Stage newTextureWindow = new Stage();
			newTextureWindow.setTitle("New Texture");
			newTextureWindow.setScene(new Scene(new NewTextureRoot(this, newTextureWindow).parentView(), 300, 160));
			newTextureWindow.setResizable(false);
			newTextureWindow.initOwner(stage);
			newTextureWindow.initModality(Modality.APPLICATION_MODAL);
			newTextureWindow.showAndWait();
		});

		newAnimation.setOnAction(event -> {
			Stage newAnimationWindow = new Stage();
			newAnimationWindow.setTitle("New Animation");
			newAnimationWindow.setScene(new Scene(new NewAnimationRoot(this, newAnimationWindow).parentView(), 300, 220));
			newAnimationWindow.setResizable(false);
			newAnimationWindow.initOwner(stage);
			newAnimationWindow.initModality(Modality.APPLICATION_MODAL);
			newAnimationWindow.showAndWait();
		});

		exit.setOnAction(event -> System.exit(0));
	}

	@Override
	public Parent parentView() {
		return root;
	}

	@Override
	public Map<String, Property> state() {
		final Map<String, Property> state = new HashMap<>();
		state.put("projectType", projectType);
		state.put("imageWidth", imageWidth);
		state.put("imageHeight", imageHeight);
		state.put("animationDuration", animationDuration);
		state.put("workingWithTileableX", workingWithTileableX);
		state.put("workingWithTileableY", workingWithTileableY);
		state.put("workingWithPeriodic", workingWithPeriodic);
		return state;
	}
}
