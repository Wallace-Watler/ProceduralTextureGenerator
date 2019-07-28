package main.model;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface ColorCombiner {

	/**
	 * @param c1 the first color
	 * @param c2 the second color
	 * @return A combination of the two colors.
	 */
	Color combine(Color c1, Color c2);
}
