package main.model;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface Colorizer {

	/**
	 * An arbitrary map from numeric value to color.
	 * @param d a number
	 * @return A color. Will be {@code Color.TRANSPARENT} if the number caused an invalid color to be created.
	 */
	Color colorize(double d);

	/**Takes a value between 0 and 1, inclusive. 0 is black, 1 is white.*/
	Colorizer GRAYSCALE = d -> {
		try {
			return new Color(d, d, d, 1);
		} catch(IllegalArgumentException e) {
			return Color.TRANSPARENT;
		}
	};
}
