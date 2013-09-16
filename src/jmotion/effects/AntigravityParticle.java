package jmotion.effects;

import java.awt.Color;

public class AntigravityParticle extends Particle {
	public void stepAhead(int time) {
		super.stepAhead(time);
		if (y <= ceiling) {
			y = ceiling;
			freeze();
		}
		dy -= GRAVITY;
	}

	public AntigravityParticle(int width, Color color, double x, double y, double dx, double dy, int ceiling) {
		super(width, color, x, y, dx, dy);
		this.ceiling = ceiling;
	}

	private int ceiling;
}