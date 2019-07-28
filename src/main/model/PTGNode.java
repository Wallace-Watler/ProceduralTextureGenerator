package main.model;

import com.sun.istack.internal.Nullable;

public abstract class PTGNode {

	private PTGNode nextNode;

	public void updateOutputAndNextNode() {
		updateOutput();
		if(nextNode != null) nextNode.updateOutputAndNextNode();
	}

	protected abstract void updateOutput();

	public void setNextNode(@Nullable PTGNode nextNode) {
		this.nextNode = nextNode;
	}
}
