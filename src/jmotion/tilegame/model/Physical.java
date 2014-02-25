package jmotion.tilegame.model;

public class Physical {
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int dX, int dY) {
		x += dX;
		y += dY;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected int x;
	protected int y;
}
