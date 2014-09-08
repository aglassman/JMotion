package jmotion.tilegame;

import jmotion.animation.AnimatorPanel;
import jmotion.tilegame.model.Map;
import jmotion.tilegame.model.MapTile;
import jmotion.tilegame.model.TileCoord;

import java.awt.*;

public abstract class TileScreenPanel<T extends MapTile> extends AnimatorPanel {

	private static final long serialVersionUID = 1L;

	public static boolean GRAPHICS_DEBUG = false;

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
		int row = realY / tileWidth;
		int col = realX / tileWidth;
		return new TileCoord(row, col);
	}

	public int getColDisplayX(int col) {
		return col * tileWidth;
	}

	public int getRowDisplayY(int row) {
		return row * tileWidth;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setMap(Map<T> map) {
		this.map = map;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		WIDTH = width;
		HEIGHT = height;

		if (map != null)
			setMap(map);
	}

	@Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public TileScreenPanel(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public TileScreenPanel(Map<T> map, int tileWidth) {
		this(tileWidth);
		setMap(map);
	}

	protected final void render(Graphics2D g) {
        int y = 0;
		for (int row = 0; row<map.HEIGHT; ++row) {
			int x = 0;
			for (int col = 0; col<map.WIDTH; ++col) {
				drawTile(g, x, y, row, col, map.get(row, col));
				x += tileWidth;
			}
			y += tileWidth;
		}

		renderForeground(g);

        if (GRAPHICS_DEBUG) {
		    g.setColor(Color.GREEN);
		    g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        }
	}

	protected abstract void drawTile(Graphics2D g, int x, int y, int row, int col, T tile);

	protected abstract void renderForeground(Graphics2D g);

	protected int WIDTH;
	protected int HEIGHT;
	protected final int tileWidth;
	protected Map<T> map;
}
