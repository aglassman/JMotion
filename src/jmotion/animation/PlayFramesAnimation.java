package jmotion.animation;

import jmotion.FrameSet;

public class PlayFramesAnimation extends Animation {

	public void stepAhead(int millis) {
		frameSet.advanceFrame();
	}

	public void start() {
		frameSet.beginSequence(sequence);
	}
	
	public boolean isFinished() {
		return frameSet.isFinalFrame();
	}

	public PlayFramesAnimation(FrameSet animation, int sequence) {
		this.frameSet = animation;
		this.sequence = sequence;
	}

	private FrameSet frameSet;
	private int sequence;
}
