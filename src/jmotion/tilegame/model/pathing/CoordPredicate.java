package jmotion.tilegame.model.pathing;

import jmotion.tilegame.model.TileCoord;

public abstract class CoordPredicate {
	public abstract boolean predicate(TileCoord coord);
}
