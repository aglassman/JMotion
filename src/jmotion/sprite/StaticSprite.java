package jmotion.sprite;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class StaticSprite implements Sprite {

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
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
	
	public StaticSprite(String imagePath) {
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Image image;
}
