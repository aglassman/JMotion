package jmotion.animation;

public class Wait extends Animation {

	public void start() {
		doneTime = System.currentTimeMillis() + millis;
	}

	public void stepAhead(int millis) {
	}

	public boolean isFinished() {
		return System.currentTimeMillis() > doneTime;
	}
	
	public Wait(int millis) {
		this.millis = millis;
	}
	
	private int millis;
	private long doneTime;
}
