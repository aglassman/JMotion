package jmotion.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;

import jmotion.animation.Animation;

public class ParticleAscension extends Animation {
	public void stepAhead(int millis) {
		for (Particle p : ps)
			p.stepAhead(millis);
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public void start() {
	}

	public void render(Graphics2D g) {
		++frameCount;
		
		if (frameCount == 2 || frameCount == 4 || frameCount == 6) {
			ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
			ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
			ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
		}
		
		isFinished = true;
		for (Particle p : ps) {
			if (!p.isFinished()) {
				p.render(g);
				isFinished = false;
			}
		}
	}
	
	public ParticleAscension(Color color, int startX, int startY, int ceilingY) {
		this.startX = startX;
		this.startY = startY;
		this.ceilingY = ceilingY;
		this.color = color;

		frameCount = 0;
		isFinished = false;
		
		ps = new HashSet<Particle>();
		ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
		ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
		ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
		ps.add(new AntigravityParticle(2, color, startX+xOffset(), startY+yOffset(), 0, 0, ceilingY+yOffset()));
	}
	
	private int xOffset() {
		return (int)(Math.random()*25)-12;
	}
	
	private int yOffset() {
		return (int)(Math.random()*13)-6;
	}
	
	private int startX;
	private int startY;
	private int ceilingY;
	private Color color;
	private HashSet<Particle> ps;
	private boolean isFinished;
	private int frameCount;
}
