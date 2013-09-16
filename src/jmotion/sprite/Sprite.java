package jmotion.sprite;

import java.awt.Graphics2D;

public interface Sprite {
	public void render(Graphics2D g);
	public void setLocation(int x, int y);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
}
