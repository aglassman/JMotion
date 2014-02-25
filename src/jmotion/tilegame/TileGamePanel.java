package jmotion.tilegame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import jmotion.animation.AnimatorPanel;
import jmotion.tilegame.model.Map;
import jmotion.tilegame.model.MapTile;

public abstract class TileGamePanel<T extends MapTile> extends AnimatorPanel {

	private static final long serialVersionUID = 1L;
	
	public static boolean GRAPHICS_DEBUG = false;
	
	/**
	 * Attempts to move the viewport by X and Y,
	 * will not scroll beyond the boundaries of the map
	 */
	public void tryScroll(int x, int y) {
		viewportX += x;
		if (viewportX < 0)
			viewportX = 0;
		else if (viewportX > viewportMaxX)
			viewportX = viewportMaxX;
		
		viewportY += y;
		if (viewportY < 0)
			viewportY = 0;
		else if (viewportY > viewportMaxY)
			viewportY = viewportMaxY;
	}

	/**
	 * Gets a position on the panel of (x,y) that accounts for the scroll
	 */
	public Point getScrolledPoint(int x, int y) {
		return new Point(x-viewportX, y-viewportY);
	}
	
	public TileGamePanel(Map<T> map, int tileWidth, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		this.map = map;
		this.tileWidth = tileWidth;
		
		visibleRows = (height+tileWidth) / tileWidth;
		visibleCols = (width+tileWidth) / tileWidth;

		viewportMaxX = map.WIDTH*tileWidth - width - tileWidth;
		viewportMaxY = map.HEIGHT*tileWidth - height - tileWidth;
		
		setSize(width, height);
	}

	protected final void render(Graphics2D g) {
		int minCol = viewportX / tileWidth;
		int maxCol = minCol + visibleCols;
		
		int minRow = viewportY / tileWidth;
		int maxRow = minRow + visibleRows - 1;

		int xOffset = viewportX % tileWidth;
		int yOffset = viewportY % tileWidth;
		
		// Draw the visible tiles
		int y = -yOffset;
		for (int row = minRow; row<=maxRow; ++row) {
			int x = -xOffset;
			for (int col = minCol; col<=maxCol; ++col) {
				drawTile(g, x, y, row, col, map.get(row, col));
				x += tileWidth;
			}
			y += tileWidth;
		}
		
		renderForeground(g);
		
		g.setColor(Color.GREEN);
		g.drawRect(0, 0, WIDTH-1, HEIGHT-1);
	}

	protected abstract void drawTile(Graphics2D g, int x, int y, int row, int col, T tile);

	protected abstract void renderForeground(Graphics2D g);

	protected final int WIDTH;
	protected final int HEIGHT;
	protected final int tileWidth;
	protected final Map<T> map;
	protected int viewportX;
	protected int viewportY;

	private int visibleRows;
	private int visibleCols;
	private int viewportMaxX;
	private int viewportMaxY;
}
