package main.component.nodelibrary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.component.ProjectType;
import main.component.ReactiveComponentParent;

import java.util.Arrays;
import java.util.Map;

public class PTGNodeLibrary implements ReactiveComponentParent {

	private final ReactiveComponentParent parent;

	@FXML
	private final Accordion accordion;

	public PTGNodeLibrary(ReactiveComponentParent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		final Insets libraryPadding = new Insets(10, 10, 10, 10);

		final VBox noiseLibrary = new VBox(Arrays.stream(noiseLibrary()).map(LibraryItem::view).toArray(Node[]::new));
		noiseLibrary.setPadding(libraryPadding);
		noiseLibrary.setSpacing(10);

		final VBox patternLibrary = new VBox(Arrays.stream(patternLibrary()).map(LibraryItem::view).toArray(Node[]::new));
		patternLibrary.setPadding(libraryPadding);
		patternLibrary.setSpacing(10);

		final VBox operatorLibrary = new VBox(Arrays.stream(operatorLibrary()).map(LibraryItem::view).toArray(Node[]::new));
		operatorLibrary.setPadding(libraryPadding);
		operatorLibrary.setSpacing(10);

		final ScrollPane noise = new ScrollPane(noiseLibrary);
		noise.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		noise.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		final ScrollPane patterns = new ScrollPane(patternLibrary);
		patterns.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		patterns.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		final ScrollPane operators = new ScrollPane(operatorLibrary);
		operators.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		operators.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		accordion = new Accordion(new TitledPane("Noise", noise), new TitledPane("Patterns", patterns), new TitledPane("Operators", operators));

		//----Init Controller----//
	}

	@Override
	public Parent parentView() {
		return accordion;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}

	private LibraryItem[] noiseLibrary() {
		return new LibraryItem[] {
				new LibraryItem(this, "Perlin Noise", "resources/perlin_noise.png") {
					protected ObservableValue<? extends Boolean> showForProjectType(ObjectProperty<ProjectType> projectType) {
						return projectType.isEqualTo(ProjectType.TEXTURE).or(projectType.isEqualTo(ProjectType.ANIMATION));
					}
				},
				new LibraryItem(this, "Cellular Noise", "resources/cellular_noise.png") {
					protected ObservableValue<? extends Boolean> showForProjectType(ObjectProperty<ProjectType> projectType) {
						return projectType.isEqualTo(ProjectType.TEXTURE).or(projectType.isEqualTo(ProjectType.ANIMATION));
					}
				}
		};
	}

	private LibraryItem[] patternLibrary() {
		return new LibraryItem[] {};
	}

	private LibraryItem[] operatorLibrary() {
		return new LibraryItem[] {};
	}
}
