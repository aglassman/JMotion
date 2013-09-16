package jmotion.effects;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Particle {
	public static final double GRAVITY = 1.2;
	
	public final int width;
	public final Color color;
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, width);
	}
	
	public void freeze() {
		dx = dy = 0;
		finished = true;
	}
	
	public void stepAhead(int time) {
		x += dx;
		y += dy;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public Particle(int width, Color color, double x, double y, double dx, double dy) {
		this.width = width;
		this.color = color;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		finished = false;
	}

	protected double x, y, dx, dy;
	private boolean finished;
}
