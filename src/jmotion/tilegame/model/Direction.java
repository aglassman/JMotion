package jmotion.tilegame.model;

import java.awt.Point;

public enum Direction {
	
	NORTH, SOUTH, EAST, WEST;
	
	/**
	 * Treating p as a vector, return the Direction that
	 * best approximates its orientation
	 */
	public static Direction get(Point p) {
		if (p.x > 0)
			return WEST;
		else if (p.x < 0)
			return EAST;
		else if (p.y > 0)
			return SOUTH;
		else
			return NORTH;
	}
	
	public static Direction get(TileCoord from, TileCoord to) {
		if (to.col > from.col)
			return WEST;
		else if (to.col < from.col)
			return EAST;
		else if (to.row > from.row)
			return SOUTH;
		else
			return NORTH;
	}
}
