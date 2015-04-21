package jmotion.test;

import java.awt.Graphics2D;

import jmotion.sprite.Sprite;
import jmotion.sprite.SpriteSpace;

public class SpriteSpaceTest {
	public static void main(String... args) {
		SpriteSpace space = new SpriteSpace();

		for (int i = 0; i<10; ++i) {
			int y = (int)(Math.random()*100);
			System.out.println("Adding Sprite(" + y + ")");
			Sprite s = new YSprite(y);
			space.addSprite(s);
		}
		
		for (Sprite s : space)
			System.out.print(s.getY() + " ");
		System.out.println();

		space = new SpriteSpace();
		for (int i = 0; i<10; ++i) {
			int y = 2*i;
			Sprite s = new YSprite(y);
			space.addSprite(s);
		}
		Sprite moving = new YSprite(11);
		space.addSprite(moving);

		for (int i = 0; i<6; ++i) {
			space.moveSprite(moving, 0, 1);
			for (Sprite s : space)
				System.out.print(s.getY() + " ");
			System.out.println();
		}
		for (int i = 0; i<6; ++i) {
			space.moveSprite(moving, 0, -3);
			for (Sprite s : space)
				System.out.print(s.getY() + " ");
			System.out.println();
		}
	}
	
	private static class YSprite implements Sprite {
		public void render(Graphics2D g) {
		}

		public void setLocation(int x, int y) {
			this.y = y;
		}

		public int getX() {
			return 0;
		}

		public int getY() {
			return y;
		}

		public int getWidth() {
			return 0;
		}

		public int getHeight() {
			return 0;
		}
		
		YSprite(int y) {
			this.y = y;
		}
		
		private int y;
	}
}
