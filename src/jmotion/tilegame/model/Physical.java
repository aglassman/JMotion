package jmotion.tilegame.model;

import java.awt.*;

public class Physical {

    public Rectangle getBounds() {
        return new Rectangle(x-width/2, y-height/2, width, height);
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

    public Physical(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected int x;
    protected int y;
    protected int width;
    protected int height;
}
