package main.component;

import javafx.beans.property.Property;
import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

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
