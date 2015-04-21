package jmotion.animation;

public class ParallelAnimation extends Animation {

	public void start() {
		finished = true;
		for (Animation a : animations) {
			a.start();
			finished &= a.isFinished();
		}
	}

	public void stepAhead(int millis) {
		finished = true;
		for (Animation a : animations) {
			a.stepAhead(millis);
			finished &= a.isFinished();
		}
	}

	public boolean isFinished() {
		return finished;
	}
	
	public ParallelAnimation(Animation... animations) {
		this.animations = animations;
	}

	private Animation[] animations;
	private boolean finished;
}
