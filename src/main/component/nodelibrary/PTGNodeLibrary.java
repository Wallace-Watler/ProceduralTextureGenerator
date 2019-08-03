package main.component.nodelibrary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import main.component.ProjectType;
import main.component.ReactiveComponent;
import main.component.ReactiveComponentParent;

import java.util.Arrays;
import java.util.Map;

public class PTGNodeLibrary implements ReactiveComponentParent {

	private final ReactiveComponent parent;

	@FXML
	private final Accordion accordion;

	public PTGNodeLibrary(ReactiveComponent parent) {
		//----Init Model----//
		this.parent = parent;

		//----Init View----//
		accordion = new Accordion(noiseFolder(), patternFolder(), operatorFolder());
	}

	@Override
	public Parent parentView() {
		return accordion;
	}

	@Override
	public Map<String, Property> state() {
		return parent.state();
	}

	private TitledPane noiseFolder() {
		return accordionFolder("Noise", new LibraryItem[] {
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
		});
	}

	private TitledPane patternFolder() {
		return accordionFolder("Patterns", new LibraryItem[] {});
	}

	private TitledPane operatorFolder() {
		return accordionFolder("Operators", new LibraryItem[] {});
	}

	private TitledPane accordionFolder(String name, LibraryItem[] libraryItems) {
		final VBox vbox = new VBox(Arrays.stream(libraryItems).map(LibraryItem::view).toArray(Node[]::new));
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);

		final ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

		return new TitledPane(name, scrollPane);
	}
}
