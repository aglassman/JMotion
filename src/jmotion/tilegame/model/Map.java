package jmotion.tilegame.model;

import java.awt.Point;

public class Map<T extends MapTile> {
	public final int WIDTH;
	public final int HEIGHT;
	public final int TILE_WIDTH;
	
	public void movePhysical(Physical p, int deltaX, int deltaY) {
		space.moveObject(p, deltaX, deltaY);
	}
	
	public void addPhysical(Physical p) {
		space.addPhysical(p);
	}
	
	public void set(T tile, int row, int col) {
		tiles[row][col] = tile;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int row, int col) {
		return (T)tiles[row][col];
	}

	@SuppressWarnings("unchecked")
	public T get(TileCoord c) {
		return (T)tiles[c.row][c.col];
	}	
	
	public PhysicalSpace getSpace() {
		return space;
	}

	public <TC extends TileCoord> TileCoord closest(TileCoord from, Iterable<TC> coords) {
		int dist = Integer.MAX_VALUE;
		TileCoord closest = null;
		for (TileCoord c : coords) {
			int d = from.manhattenDist(c);
			if (d < dist) {
				dist = d;
				closest = c;
			}
		}
		return closest;
	}

	public Point getTileCenter(int row, int col) {
		return new Point(col*TILE_WIDTH + TILE_WIDTH/2, row*TILE_WIDTH + TILE_WIDTH/2);
	}

	public Point getTileOrigin(int row, int col) {
		return new Point(col*TILE_WIDTH, row*TILE_WIDTH);
	}
	
	public TileCoord getCoords(int x, int y) {
		return new TileCoord(y/TILE_WIDTH, x/TILE_WIDTH);
	}

	public boolean contains(TileCoord c) {
		return c.row >= 0 && c.row < HEIGHT
			&& c.col >= 0 && c.col < WIDTH;
	}

	public TileCoord getCoord(int x, int y) {
		return new TileCoord(y/TILE_WIDTH, x/TILE_WIDTH);
	}
	
	public Map(int width, int height, int tileWidth) {
		WIDTH = width;
		HEIGHT = height;
		TILE_WIDTH = tileWidth;
		
		tiles = new MapTile[HEIGHT][WIDTH];
		space = new PhysicalSpace(WIDTH*tileWidth, HEIGHT*tileWidth);
	}
	public Map(int width, int height, int tileWidth, MapTile defaultTile) {
		this(width, height, tileWidth);
		for (int row = 0; row<HEIGHT; ++row) {
			for (int col = 0; col<WIDTH; ++col)
				tiles[row][col] = defaultTile;
		}
	}
	
	protected PhysicalSpace space;
	private MapTile[][] tiles;
}
