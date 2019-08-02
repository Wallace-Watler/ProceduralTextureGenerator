package main.component;

import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * A {@code ReactiveComponent} that is guaranteed to have a {@code javafx.scene.Parent} view - typically only needed for
 * the root of a {@code javafx.scene.Scene}. "Parent" here does not refer to any component structure;
 * a {@code ReactiveComponent} need not be a {@code ReactiveComponentParent} to be a parent to other components.
 */
public interface ReactiveComponentParent extends ReactiveComponent {

	Parent parentView();

	@Override
	default Node view() {
		return parentView();
	}
}
