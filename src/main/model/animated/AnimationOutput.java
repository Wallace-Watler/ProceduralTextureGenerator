package main.model.animated;

import com.sun.istack.internal.Nullable;
import javafx.scene.image.WritableImage;
import main.model.PTGNode;

public class AnimationOutput {

	public final PTGNode outputFrom;
	private WritableImage[] output;

	public AnimationOutput(PTGNode outputFrom) {
		this.outputFrom = outputFrom;
	}

	public void setOutput(WritableImage[] output) {
		this.output = output;
	}

	@Nullable
	public WritableImage[] getOutput() {
		return output;
	}
}
