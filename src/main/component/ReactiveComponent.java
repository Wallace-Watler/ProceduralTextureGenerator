package main.component;

import javafx.beans.property.Property;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * An individual piece of the program with a self-contained model (state), view, and controller.
 * Should be able to inherit state from its parent, another {@code ReactiveComponent}.
 */
public interface ReactiveComponent {

	ReactiveComponent NULL = new ReactiveComponent() {
		@Override
		public Node view() { return null; }

		@Override
		public Map<String, Property> state() { return new HashMap<>(); }
	};

	Node view();
	Map<String, Property> state();
}
