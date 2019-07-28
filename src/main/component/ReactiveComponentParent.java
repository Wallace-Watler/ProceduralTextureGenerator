package main.component;

import javafx.scene.Node;
import javafx.scene.Parent;

public interface ReactiveComponentParent extends ReactiveComponent {

	Parent parentView();

	@Override
	default Node view() {
		return parentView();
	}
}
