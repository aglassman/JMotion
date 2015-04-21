package jmotion.animation;

public class LoopAnimation extends Animation {

	public void start() {
		internal.start();
	}

	public void stepAhead(int millis) {
		if (internal.isFinished())
			internal.start();
		internal.stepAhead(millis);
	}

	public boolean isFinished() {
		return false;
	}

	public LoopAnimation(Animation animation) {
		internal = animation;
	}
	
	private Animation internal;
}
