package main.model.animated;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import main.model.Colorizer;
import main.model.NoiseMap;
import main.model.PTGNode;

public class Colorizer3D extends PTGNode {

	private Colorizer colorizer;
	public final NoiseMapInput3D input;
	public final AnimationOutput output;

	public Colorizer3D() {
		colorizer = Colorizer.GRAYSCALE;
		input = new NoiseMapInput3D(this);
		output = new AnimationOutput(this);
	}

	public void setColorizer(Colorizer colorizer) {
		this.colorizer = colorizer;
	}

	@Override
	protected void updateOutput() {
		final NoiseMap[] input = this.input.getInput();

		if(input == null) {
			output.setOutput(null);
		} else {
			WritableImage[] output = this.output.getOutput();

			// Only need to reallocate array if input and output durations don't match
			if(output == null || output.length != input.length) {
				output = new WritableImage[input.length];
			}

			for(int z = 0; z < input.length; z++) {
				// Only need to reallocate frame if input and output dimensions don't match
				if(output[z] == null || output[z].getWidth() != input[z].getWidth() || output[z].getHeight() != input[z].getHeight()) {
					output[z] = new WritableImage(input[z].getWidth(), input[z].getHeight());
				}

				final PixelWriter writer = output[z].getPixelWriter();
				final double[][] noiseValues = input[z].getValues();

				for(int y = 0; y < input[z].getHeight(); y++) {
					for(int x = 0; x < input[z].getWidth(); x++) {
						writer.setColor(x, y, colorizer.colorize(noiseValues[x][y]));
					}
				}
			}

			this.output.setOutput(output);
		}
	}
}
