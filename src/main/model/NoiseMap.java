package main.model;

public class NoiseMap {

	private double[][] values;
	private int width, height;

	public NoiseMap(double[][] values) {
		this.values = values;
		width = values[0].length;
		height = values.length;
	}

	public double[][] getValues() {
		return values;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
