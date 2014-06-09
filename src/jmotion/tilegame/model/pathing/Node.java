package jmotion.tilegame.model.pathing;

import jmotion.tilegame.model.Direction;
import jmotion.tilegame.model.TileCoord;

class Node implements Comparable<Node> {

	public int compareTo(Node otherNode) {
		int thisTotalDistanceFromGoal = heuristicDistanceFromGoal + distanceFromStart;
		int otherTotalDistanceFromGoal = otherNode.getHeuristic() + otherNode.getDistanceFromStart();

		if (thisTotalDistanceFromGoal < otherTotalDistanceFromGoal) {
			return -1;
		} else if (thisTotalDistanceFromGoal > otherTotalDistanceFromGoal) {
			return 1;
		} else {
			return 0;
		}
	}	
	
	public String toString() {
		return coord.toString();
	}

	public Node[] getNeighborList() {
		return new Node[]{north, south, east, west, northEast, northWest, southEast, southWest};
	}

	boolean isVisited() {
		return visited;
	}

	void setVisited(boolean visited) {
		this.visited = visited;
	}

	int getDistanceFromStart() {
		return distanceFromStart;
	}

	void setDistanceFromStart(int d) {
		this.distanceFromStart = d;
	}

	Node getPreviousNode() {
		return previousNode;
	}

	void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	int getHeuristic() {
		return heuristicDistanceFromGoal;
	}

	void setHeuristic(int heuristicDistanceFromGoal) {
		this.heuristicDistanceFromGoal = heuristicDistanceFromGoal;
	}

	TileCoord getCoord() {
		return coord;
	}

	boolean isObstical() {
		return isObstacle;
	}

	void setObstical(boolean isObstical) {
		this.isObstacle = isObstical;
	}

	boolean isStart() {
		return isStart;
	}

	void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	boolean isGoal() {
		return isGoal;
	}

	void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	boolean equals(Node node) {
		return coord.equals(node.coord);
	}
	
	void setNeighbor(Node n, Direction d) {
		switch (d) {
		case NORTH:
			north = n;
			break;
		case EAST:
			east = n;
			break;
		case NORTH_EAST:
			northEast = n;
			break;
		case NORTH_WEST:
			northWest = n;
			break;
		case SOUTH:
			south = n;
			break;
		case SOUTH_EAST:
			southEast = n;
			break;
		case SOUTH_WEST:
			southWest = n;
			break;
		case WEST:
			west = n;
			break;
		}
	}

	Node(TileCoord coord) {
		this.coord = coord;
		this.visited = false;
		this.distanceFromStart = Integer.MAX_VALUE;
		this.isStart = false;
		this.isGoal = false;
	}

	protected Node north;
	protected Node northEast;
	protected Node northWest;
	protected Node east;
	protected Node south;
	protected Node southEast;
	protected Node southWest;
	protected Node west;
	
	private boolean visited;
	private int distanceFromStart;
	private int heuristicDistanceFromGoal;
	private Node previousNode;
	private TileCoord coord;
	private boolean isObstacle;
	private boolean isStart;
	private boolean isGoal;
}