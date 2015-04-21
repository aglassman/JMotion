package jmotion.sprite;

import java.awt.Graphics2D;

import jmotion.animation.FrameSet;

public class FramesSprite implements Sprite {

	public void render(Graphics2D g) {
		g.drawImage(frames.currentFrame(), x, y, null);
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return frames.getWidth();
	}

	public int getHeight() {
		return frames.getHeight();
	}
	
	public FramesSprite(FrameSet frames) {
		this.frames = frames;
		frames.setSequence(0);
	}
	
	private int x;
	private int y;
	private FrameSet frames;
}
