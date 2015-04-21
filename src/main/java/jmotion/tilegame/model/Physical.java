package jmotion.tilegame.model;

import java.awt.*;

public class Physical {

    public Rectangle getBounds() {
        return new Rectangle(x+xOffset, y+yOffset, width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

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

    public void setBounds(int xOffset, int yOffset, int width, int height) {
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int xOffset;
    protected int yOffset;
}
