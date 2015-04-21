package jmotion.tilegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import jmotion.animation.AnimatorPanel;
import jmotion.tilegame.model.Map;
import jmotion.tilegame.model.MapTile;
import jmotion.tilegame.model.TileCoord;

public abstract class TileGamePanel<T extends MapTile> extends AnimatorPanel {

	private static final long serialVersionUID = 1L;

	public static boolean GRAPHICS_DEBUG = false;

	/**
	 * Attempts to move the viewport, but will not scroll beyond the boundaries
	 * of the map
	 */
	public void tryScroll(int xScroll, int yScroll) {
		setViewport(viewportX + xScroll, viewportY + yScroll);
	}

	public void setViewport(int x, int y) {
		viewportX = Math.min(viewportMaxX, Math.max(0, x));
		viewportY = Math.min(viewportMaxY, Math.max(0, y));

		rowOffset = viewportY % tileWidth;
		colOffset = viewportX % tileWidth;

		rowStart = viewportY / tileWidth;
		colStart = viewportX / tileWidth;
	}

	/**
	 * Gets a position on the panel of (x,y) that accounts for the scroll
	 * 
	 * (x,y) is some real position in the game map
	 */
	public Point getPointOnScreen(int realX, int realY) {
		return new Point(realX - viewportX, realY - viewportY);
	}

	/**
	 * The absolute coordinates (not adjusted for scrolling) of the center of a
	 * tile at the given TileCoord
	 * 
	 * For example if tileWidth is 10, the Point representing the center of
	 * TileCoord (2, 3) would be (25, 35)
	 */
	public Point getSquareCenter(TileCoord s) {
		return new Point(s.col * tileWidth + tileWidth / 2, s.row * tileWidth + tileWidth / 2);
	}

	/**
	 * Finds the TileCoord of the tile containing the given on-screen point
	 */
	public TileCoord getCoordFromScreen(int realX, int realY) {
		int row = (realY + viewportY) / tileWidth;
		int col = (realX + viewportX) / tileWidth;
		return new TileCoord(row, col);
	}

	public int getColDisplayX(int col) {
		return (col - colStart) * tileWidth - colOffset;
	}

	public int getRowDisplayY(int row) {
		return (row - rowStart) * tileWidth - rowOffset;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setMap(Map<T> map) {
		this.map = map;

		viewportMaxX = map.WIDTH * tileWidth - WIDTH - 1;
		viewportMaxY = map.HEIGHT * tileWidth - HEIGHT - 1;
		
		System.out.println("setting map");
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		WIDTH = width;
		HEIGHT = height;
		visibleRows = (height + tileWidth) / tileWidth;
		visibleCols = (width + tileWidth) / tileWidth;
		System.out.println("setting size: visRows: " + visibleRows);
		
		if (map != null)
			setMap(map);
	}
	
	@Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public TileGamePanel(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public TileGamePanel(Map<T> map, int tileWidth) {
		this(tileWidth);
		setMap(map);
	}

	protected final void render(Graphics2D g) {
		int maxCol = colStart + visibleCols - 1;
		int maxRow = rowStart + visibleRows - 1;

		// Draw the visible tiles
		int y = -rowOffset;
		for (int row = rowStart; row <= maxRow && row < map.HEIGHT; ++row) {
			int x = -colOffset;
			for (int col = colStart; col <= maxCol && col < map.WIDTH; ++col) {
				drawTile(g, x, y, row, col, map.get(row, col));
				x += tileWidth;
			}
			y += tileWidth;
		}

		renderForeground(g);

		g.setColor(Color.GREEN);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
	}

	protected abstract void drawTile(Graphics2D g, int x, int y, int row, int col, T tile);

	protected abstract void renderForeground(Graphics2D g);

	protected int WIDTH;
	protected int HEIGHT;
	protected final int tileWidth;
	protected Map<T> map;

	protected int viewportX;
	protected int viewportY;
	protected int rowStart;
	protected int colStart;
	protected int rowOffset;
	protected int colOffset;

	protected int visibleRows;
	protected int visibleCols;
	private int viewportMaxX;
	private int viewportMaxY;
}
