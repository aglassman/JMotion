package jmotion.tilegame.model;

public class TileCoord {
	public final int row;
	public final int col;

	public int manhattenDistance(TileCoord other) {
		return Math.abs(row - other.row) + Math.abs(col - other.col);
	}

	public int euclideanDist(TileCoord other) {
		return Math.max(Math.abs(row - other.row), Math.abs(col - other.col));
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof TileCoord))
			return false;
		TileCoord otherCoords = (TileCoord)other;
		return otherCoords.col == col && otherCoords.row == row;
	}
	
	public String toString() {
		return "(" + row + "," + col + ")";
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * The Von Neumann Neighborhood, four neighbors touching orthogonally
	 */
	public TileCoord[] getNeighbors() {
		return new TileCoord[]{getNorth(), getSouth(), getEast(), getWest()};
	}

	/**
	 * The Moore Neighborhood is the eight cells including diagonals
	 */
	public TileCoord[] getMooreNeighbors() {
		return new TileCoord[]{getNorth(), getSouth(), getEast(), getWest()};
	}

	public TileCoord getNorth() {
		return new TileCoord(row-1, col);
	}

	public TileCoord getSouth() {
		return new TileCoord(row+1, col);
	}

	public TileCoord getEast() {
		return new TileCoord(row, col+1);
	}

	public TileCoord getWest() {
		return new TileCoord(row, col-1);
	}
	
	public TileCoord getNeighbor(Direction d) {
		return new TileCoord(row + d.y, col+d.x);
	}
	
	public TileCoord(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
