package main.model.animated;

import com.sun.istack.internal.Nullable;
import main.model.NoiseMap;
import main.model.PTGNode;

public class NoiseMapInput3D {

	private final PTGNode inputTo;
	private NoiseMapOutput3D connectedOutput;

	public NoiseMapInput3D(PTGNode inputTo) {
		this.inputTo = inputTo;
	}

	public void setConnectedOutput(@Nullable NoiseMapOutput3D connectedOutput) {
		if(this.connectedOutput == connectedOutput) return;

		if(this.connectedOutput != null) {
			this.connectedOutput.outputFrom.setNextNode(null);
		}
		this.connectedOutput = connectedOutput;
		connectedOutput.outputFrom.setNextNode(inputTo);

		inputTo.updateOutputAndNextNode();
	}

	@Nullable
	public NoiseMap[] getInput() {
		return connectedOutput == null ? null : connectedOutput.getOutput();
	}
}
