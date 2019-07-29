package main.component;

import javafx.beans.property.Property;
import javafx.scene.Node;

import java.util.Map;

public interface ReactiveComponent {

	Node view();
	Map<String, Property> state();
}
