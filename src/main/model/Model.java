package main.model;

import javafx.scene.image.Image;
import main.model.animated.Colorizer3D;
import main.model.animated.NoiseMapOutput3D;

public final class Model {

	public static Image getImage() {
		Colorizer3D colorizer3D = new Colorizer3D();
		NoiseMapOutput3D temp = new NoiseMapOutput3D(new PTGNode() {
			@Override
			protected void updateOutput() {}
		});
		temp.setOutput(new NoiseMap[] { new NoiseMap(new double[100][100]) });
		colorizer3D.input.setConnectedOutput(temp);
		return colorizer3D.output.getOutput()[0];
	}
}
