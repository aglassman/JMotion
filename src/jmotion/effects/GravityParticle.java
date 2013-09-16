package jmotion.effects;

import java.awt.Color;

public class GravityParticle extends Particle {
	public void stepAhead(int time) {
		super.stepAhead(time);
		if (y >= groundY) {
			y = groundY;
			freeze();
		}
		dy += GRAVITY;
	}
	
	public GravityParticle(int width, Color color, double x, double y, double dx, double dy, int groundY) {
		super(width, color, x, y, dx, dy);
		this.groundY = groundY;
	}
	
	private int groundY;
}