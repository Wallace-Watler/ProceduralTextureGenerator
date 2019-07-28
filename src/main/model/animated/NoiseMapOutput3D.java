package main.model.animated;

import com.sun.istack.internal.Nullable;
import main.model.NoiseMap;
import main.model.PTGNode;

public class NoiseMapOutput3D {

	public final PTGNode outputFrom;
	private NoiseMap[] output;

	public NoiseMapOutput3D(PTGNode outputFrom) {
		this.outputFrom = outputFrom;
	}

	public void setOutput(NoiseMap[] output) {
		this.output = output;
	}

	@Nullable
	public NoiseMap[] getOutput() {
		return output;
	}
}
