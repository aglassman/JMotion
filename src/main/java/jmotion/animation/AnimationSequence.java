package jmotion.animation;

public class AnimationSequence extends Animation {

	public void stepAhead(int millis) {
		if (index < animations.length) {
			if (animations[index].isFinished()) {
				++index;
				if (index < animations.length)
					animations[index].start();
			}
			
			if (index < animations.length)
				animations[index].stepAhead(millis);
		}
	}
	
	public void start() {
		index = 0;
		if (animations.length > 0)
			animations[0].start();
	}

	public boolean isFinished() {
		return index >= animations.length;
	}

	public AnimationSequence(Animation... animations) {
		this.animations = animations;
	}
	
	private Animation[] animations;
	private int index;
}
