package jmotion.animation;

/**
 * An Animation that serves to execute some action when it is started.
 * 
 * This would primarily be used to add or remove Sprites, or change a frameset of some Sprite,
 * at some point during an AnimationSequence.
 *
 */
public abstract class ActionAnimation extends Animation {
	
	public void start() {
		action();
	}

	public void stepAhead(int millis) {
	}

	public boolean isFinished() {
		return true;
	}
	
	public abstract void action();
}
