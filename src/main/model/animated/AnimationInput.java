package main.model.animated;

import com.sun.istack.internal.Nullable;
import javafx.scene.image.WritableImage;
import main.model.PTGNode;

public class AnimationInput {

	private final PTGNode inputTo;
	private AnimationOutput connectedOutput;

	public AnimationInput(PTGNode inputTo) {
		this.inputTo = inputTo;
	}

	public void setConnectedOutput(@Nullable AnimationOutput connectedOutput) {
		if(this.connectedOutput == connectedOutput) return;

		if(this.connectedOutput != null) {
			this.connectedOutput.outputFrom.setNextNode(null);
		}
		this.connectedOutput = connectedOutput;
		connectedOutput.outputFrom.setNextNode(inputTo);

		inputTo.updateOutputAndNextNode();
	}

	@Nullable
	public WritableImage[] getInput() {
		return connectedOutput == null ? null : connectedOutput.getOutput();
	}
}
