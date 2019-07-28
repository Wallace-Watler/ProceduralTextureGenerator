package main.component;

import javafx.scene.Node;

import java.util.Map;

public interface ReactiveComponent {

	Node view();
	Map<String, Object> state();
}
