package jmotion.sample;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jmotion.tilegame.TileGamePanel;
import jmotion.tilegame.model.Physical;

public class SampleMapPanel extends TileGamePanel<SampleTile> {

	private static final long serialVersionUID = 1L;

	public SampleMapPanel(SampleMap map, int width, int height) {
		super(map, map.TILE_WIDTH);
		setSize(width, height);

		this.map = map;
		scrollMargin = 4*map.TILE_WIDTH;
		
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				
			}
			
			public void keyReleased(KeyEvent e) {
				keyLeft &= e.getKeyCode() != KeyEvent.VK_LEFT;
				keyRight &= e.getKeyCode() != KeyEvent.VK_RIGHT;
				keyUp &= e.getKeyCode() != KeyEvent.VK_UP;
				keyDown &= e.getKeyCode() != KeyEvent.VK_DOWN;
			}
			
			public void keyPressed(KeyEvent e) {
				keyLeft |= e.getKeyCode() == KeyEvent.VK_LEFT;
				keyRight|= e.getKeyCode() == KeyEvent.VK_RIGHT;
				keyUp |= e.getKeyCode() == KeyEvent.VK_UP;
				keyDown |= e.getKeyCode() == KeyEvent.VK_DOWN;
			}
		});
	}
	
	protected void drawTile(Graphics2D g, int x, int y, int row, int col, SampleTile tile) {
		g.setColor(tile.color);
		g.fillRect(x, y, map.TILE_WIDTH, map.TILE_WIDTH);
	}

	protected void renderForeground(Graphics2D g) {
		int heroX = map.getHero().getX();
		int heroY = map.getHero().getY();
		Point heroOnScreen = getPointOnScreen(heroX, heroY);
		
		g.setColor(Color.blue);
		g.fillOval(heroOnScreen.x, heroOnScreen.y, 10, 10);
	}

	protected void advanceFrame(int millis) {	
		
		int scrollX = 0;
		int scrollY = 0;
		int scrollSpeed = 5;
		Physical hero = map.getHero();
		
		int heroSpeed = 5;
		int heroXMove = 0;
		if (keyLeft)
			heroXMove = -heroSpeed;
		else if (keyRight)
			heroXMove = heroSpeed;
		int heroYMove = 0;
		if (keyUp)
			heroYMove = -heroSpeed;
		else if (keyDown)
			heroYMove = heroSpeed;
		hero.move(heroXMove, heroYMove);
		
		Point heroOnScreen = getPointOnScreen(hero.getX(), hero.getY());
		
		if (heroOnScreen.x <= scrollMargin)
			scrollX = -scrollSpeed;
		else if (heroOnScreen.x >= WIDTH - scrollMargin)
			scrollX = scrollSpeed;
		if (heroOnScreen.y <= scrollMargin)
			scrollY = -scrollSpeed;
		else if (heroOnScreen.y >= HEIGHT - scrollMargin)
			scrollY = scrollSpeed;
		
		tryScroll(scrollX, scrollY);
	}
	
	private int scrollMargin;
	private SampleMap map;
	private boolean keyRight;
	private boolean keyLeft;
	private boolean keyUp;
	private boolean keyDown;
}
