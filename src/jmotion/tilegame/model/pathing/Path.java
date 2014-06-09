package jmotion.tilegame.model.pathing;

import java.util.LinkedList;

import jmotion.tilegame.model.TileCoord;

public class Path {
	public String toString() {
		String path = "";
		for (Node n : waypoints)
			path += n.getCoord() + ",";
		return path;
	}

	public TileCoord get(int i) {
		return waypoints.get(i).getCoord();
	}
	
	public TileCoord getLast() {
		return waypoints.getLast().getCoord();
	}
	
	public Path compress() {
		LinkedList<Node> newNodes = new LinkedList<>();
		
		int i = 0;
		while (i < waypoints.size() - 1) {
			Node anchor = waypoints.get(i);
			newNodes.add(anchor);
			
			Node next = waypoints.get(i+1);
			++i;
			// TODO this should be done using the Direction class to track
			// which direction is being taken for each leg
			if (anchor.north == next) {
				while (i < waypoints.size() -1 && next.north == waypoints.get(i+1)) {
					++i;
					next = waypoints.get(i);
				}
			} else if (anchor.south == next) {
				while (i < waypoints.size() -1 && next.south == waypoints.get(i+1)) {
					++i;
					next = waypoints.get(i);
				}
			} else if (anchor.east == next) {
				while (i < waypoints.size() -1 && next.east == waypoints.get(i+1)) {
					++i;
					next = waypoints.get(i);
				}
			} else if (anchor.west == next) {
				while (i < waypoints.size() -1 && next.west == waypoints.get(i+1)) {
					++i;
					next = waypoints.get(i);
				}
			}
			// TODO compress the diagonals as well
		}
		if (waypoints.size() > 0)
			newNodes.add(waypoints.getLast());
		
		return new Path(newNodes);
	}
	
	public Path() {
		waypoints = new LinkedList<>();
	}
	
	public int getLength() {
		return waypoints.size();
	}

	public Node getWayPoint(int index) {
		return waypoints.get(index);
	}

	public void appendWayPoint(Node n) {
		waypoints.add(n);
	}

	public void prependWayPoint(Node n) {
		waypoints.add(0, n);
	}

	public Path(LinkedList<Node> waypoints) {
		this.waypoints = waypoints;
	}
	
	/**
	 *  The waypoints in the path (list of coordinates making up the path)
	 */
	private LinkedList<Node> waypoints;

}
