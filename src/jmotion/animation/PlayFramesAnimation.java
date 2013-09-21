package jmotion.animation;


public class PlayFramesAnimation extends Animation {

	public void stepAhead(int millis) {
		if (!firstFrame)
			frameSet.advanceFrame();
		firstFrame = false;
	}

	public void start() {
		firstFrame = true;
		frameSet.beginSequence(sequence);
	}
	
	public boolean isFinished() {
		return frameSet.isFinalFrame();
	}

	public PlayFramesAnimation(FrameSet animation, int sequence) {
		this.frameSet = animation;
		this.sequence = sequence;
	}

	private boolean firstFrame;
	private FrameSet frameSet;
	private int sequence;
}
