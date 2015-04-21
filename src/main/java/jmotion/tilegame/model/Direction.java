package jmotion.tilegame.model;

import java.awt.Point;

public enum Direction {
	
	NORTH(0, -1),
	SOUTH(0, 1),
	EAST(1, 0),
	WEST(-1, 0),
	NORTH_WEST(-1, -1),
	NORTH_EAST(1, 1),
	SOUTH_WEST(-1, 1),
	SOUTH_EAST(-1, -1);

	public static final Direction[] CARDINALS = {NORTH, SOUTH, EAST, WEST};
	
	/**
	 * Treating p as a vector, return the Direction that
	 * best approximates its orientation
	 */
	public static Direction get(Point p) {
		if (p.x < 0) {
			if (p.y > 0)
				return SOUTH_WEST;
			else if (p.y < 0)
				return NORTH_WEST;
			return WEST;
		} else if (p.x > 0) {
			if (p.y > 0)
				return SOUTH_EAST;
			else if (p.y < 0)
				return NORTH_EAST;
			return EAST;
		}
		else if (p.y > 0)
			return SOUTH;
		return NORTH;
	}
	
	public static Direction get(TileCoord from, TileCoord to) {
		if (to.col > from.col) {
			if (to.row > from.col)
				return SOUTH_EAST;
			else if (to.row < from.col)
				return NORTH_EAST;
			return EAST;
		} else if (to.col < from.col) {
			if (to.row > from.col)
				return SOUTH_WEST;
			else if (to.row < from.col)
				return NORTH_WEST;
			return WEST;
		} 
		if (to.row > from.row)
			return SOUTH;
		return NORTH;
	}
	
	public final int x;
	public final int y;
	
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
