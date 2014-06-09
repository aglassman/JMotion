package jmotion.tilegame.model.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import jmotion.tilegame.model.Direction;
import jmotion.tilegame.model.Map;
import jmotion.tilegame.model.MapTile;
import jmotion.tilegame.model.TileCoord;

public abstract class AStar<T extends MapTile> {
	
	/**
	 * Finds a shortest path that ends at a coordinate that satisfies the predicate.
	 * (This is a Breadth First Search)
	 * 
	 * For example, use this for an AI that always paths to the nearest enemy, building, etc
	 */
	public Path pathToNearestTarget(TileCoord start, CoordPredicate pred, int maxLength) {
		nodes = new HashMap<>();
		Node startNode = getNode(start);
		startNode.setVisited(true);

		LinkedList<Node> frontier = new LinkedList<>();
		frontier.add(startNode);
		
		for (int i = 0; i<maxLength; ++i) {			
			LinkedList<Node> nextFrontier = new LinkedList<>();
			
			for (Node f : frontier) {				
				// We may have found a target
				if (pred.predicate(f.getCoord()))
					return reconstructPath(f);
				
				// Don't expand a non-walkable Node
				if (!f.isObstical()) {
					expandNeighbors(f);
					for (Node n : f.getNeighborList()) {
						if (n != null && !n.isVisited()) {
							nextFrontier.add(n);
							n.setPreviousNode(f);
							n.setVisited(true);
						}
					}
				}
			}
			frontier = nextFrontier;
		}
		
		return null;
	}

	public Path calcShortestPath(TileCoord source, TileCoord destination) {
		nodes = new HashMap<>();
		Node startNode = getNode(source);

		// No path to a non-walkable square
		if (isObstacle(map.get(destination)))
			return null;

		startNode.setDistanceFromStart(0);
		closedList.clear();
		openList.clear();
		openList.add(startNode);

		// while we haven't reached the goal yet
		while (openList.size() != 0) {

			// get the first Node from non-searched Node list, sorted by lowest
			// distance from our goal as guessed by our heuristic
			Node current = openList.getFirst();

			// check if our current Node location is the goal Node. If it is, we
			// are done.
			if (current.getCoord().equals(destination))
				return reconstructPath(current);

			// move current Node to the closed (already searched) list
			openList.remove(current);
			closedList.add(current);

			// go through all the current Nodes neighbors and calculate if one should be our next step
			expandNeighbors(current);
			for (Node neighbor : current.getNeighborList()) {
				boolean neighborIsBetter;

				// if we have already searched this Node, don't bother and
				// continue to the next one
				if (closedList.contains(neighbor))
					continue;

				// also just continue if the neighbor is an obstacle
				if (neighbor != null && !neighbor.isObstical()) {
					// calculate how long the path is if we choose this neighbor
					// as the next step in the path
					int neighborDistanceFromStart = current.getDistanceFromStart() + distance(current, neighbor);

					// add neighbor to the open list if it is not there
					if (!openList.contains(neighbor)) {
						openList.add(neighbor);
						neighborIsBetter = true;
						// if neighbor is closer to start it could also be better
					} else if (neighborDistanceFromStart < current.getDistanceFromStart()) {
						neighborIsBetter = true;
					} else {
						neighborIsBetter = false;
					}
					// set neighbors parameters if it is better
					if (neighborIsBetter) {
						neighbor.setPreviousNode(current);
						neighbor.setDistanceFromStart(neighborDistanceFromStart);
						neighbor.setHeuristic(heuristic(neighbor.getCoord(), destination));
					}
				}
			}
		}
		return null;
	}
	
	public abstract boolean isObstacle(T tile);

	public AStar(Map<T> map) {
		this.map = map;

		closedList = new LinkedList<>();
		openList = new SortedNodeList();
	}
	
	/**
	 * The true distance between two Nodes
	 * @Todo should this be Euclidean or Manhatten distance?
	 */
	private int distance(Node a, Node b) {
		return 1;
	}
	
	/**
	 * 
	 */
	private int heuristic(TileCoord from, TileCoord to) {
		return from.manhattenDistance(to);
	}

	private Path reconstructPath(Node node) {
		Path path = new Path();
		while (node.getPreviousNode() != null) {
			path.prependWayPoint(node);
			node = node.getPreviousNode();
		}
		return path;
	}

	private class SortedNodeList {

		private ArrayList<Node> list = new ArrayList<Node>();

		public Node getFirst() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}

		public void add(Node node) {
			list.add(node);
			Collections.sort(list);
		}

		public void remove(Node n) {
			list.remove(n);
		}

		public int size() {
			return list.size();
		}

		public boolean contains(Node n) {
			return list.contains(n);
		}
	}

	private void expandNeighbors(Node node) {
		Direction[] directions = diagonals ? Direction.values() : Direction.CARDINALS;

		TileCoord origin = node.getCoord();
		for (Direction dir : directions) {
			TileCoord neighbor = origin.getNeighbor(dir);
			if (map.contains(neighbor) && !isObstacle(map.get(neighbor))) {
				Node neighborNode = getNode(neighbor);
				node.setNeighbor(neighborNode, dir);
			}
		}
	}

	private Node getNode(TileCoord c) {
		if (!nodes.containsKey(c)) {
			Node n = new Node(c);
			n.setObstical(isObstacle(map.get(c)));
			nodes.put(c, n);
			return n;
		}
		return nodes.get(c);
	}
	
	private Map<T> map;
	
	/**
	 * closedList The list of Nodes not searched yet, sorted by their distance
	 * to the goal as guessed by our heuristic.
	 */
	private LinkedList<Node> closedList;
	private SortedNodeList openList;
	private HashMap<TileCoord, Node> nodes;
	private boolean diagonals;

}