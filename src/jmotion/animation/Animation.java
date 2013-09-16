package jmotion.animation;

public abstract class Animation {
	public void finish() {
		while (!isFinished()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
	}
	public abstract void start();
	public abstract void stepAhead(int millis);
	public abstract boolean isFinished();
}
