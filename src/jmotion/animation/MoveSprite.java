package jmotion.animation;

import jmotion.sprite.Sprite;

public class MoveSprite extends Animation {

	public void stepAhead(int millis) {
		int newX;
		int newY;

		if (Math.abs(sprite.getX() - targetX) < speed)
			newX = targetX;
		else
			newX = sprite.getX() + deltaX;
		if (Math.abs(sprite.getY() - targetY) < speed)
			newY = targetY;
		else
			newY = sprite.getY() + deltaY;
			
		sprite.setLocation(newX, newY);
		isFinished = newX == targetX && newY == targetY;
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public void start() {
		isFinished = false;
		if (sprite.getX() < targetX)
			deltaX = speed;
		else if (sprite.getX() > targetX)
			deltaX = -speed;
		
		if (sprite.getY() < targetY)
			deltaY = speed;
		else if (sprite.getY() > targetY)
			deltaY = -speed;
	}
	
	public MoveSprite(Sprite sprite, int targetX, int targetY, int speed) {
		this.sprite = sprite;
		this.targetX = targetX;
		this.targetY = targetY;
		this.speed = speed;
	}

	private Sprite sprite;
	private int targetX;
	private int targetY;
	private int speed;
	
	private int deltaX;
	private int deltaY;
	
	private boolean isFinished;
}
