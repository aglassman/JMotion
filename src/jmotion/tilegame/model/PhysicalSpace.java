package jmotion.tilegame.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Tracks physical objects in two dimensional space.
 * 
 * Detects collisions between objects in the space and each other, or their environment.
 * 
 *
 */
public class PhysicalSpace {
	
	public Iterable<Physical> getAllPhysicals() {
		return new Iterable<Physical>() {
			public Iterator<Physical> iterator() {
				return new Iterator<Physical>() {
					PhysicalNode current = head;
					
					public boolean hasNext() {
						return current.next != tail;
					}
					
					public Physical next() {
						current = current.next;
						return current.physical;
					}
					
					public void remove() {
					}
				};
			}
		};
	}

    public List<Physical> getCollisions(Physical p) {
        List<Physical> collisions = new LinkedList<>();

        // TODO - this is terrible brute force. You know what to do.
        for (Physical other : getAllPhysicals()) {
            if (other != p && collides(p, other))
                collisions.add(other);
        }

        return collisions;
    }
	
	public void moveObject(Physical p, int deltaX, int deltaY) {
		p.setLocation(p.getX()+deltaX, p.getY()+deltaY);
		
		PhysicalNode before = findBefore(p);
		PhysicalNode node = before.next;
		node.y = p.getY();
		
		// remove the node
		before.next = node.next;
		
		// Bubble the node up from the beginning of the list
		tail.y = node.y + 1;
		PhysicalNode current = head;
		while (current.next.y < node.y)
			current = current.next;
		
		node.next = current.next;
		current.next = node;
	}
	
	public void addPhysical(Physical p) {
		PhysicalNode node = new PhysicalNode();
		node.physical = p;
		node.y = p.getY();
		
		tail.y = p.getY() + 1;
		
		PhysicalNode current = head;
		while (current.next.y < node.y)
			current = current.next;
		
		node.next = current.next;
		current.next = node;
        node.previous = current.next;
	}
	
	public void removePhysical(Physical p) {
		PhysicalNode before = findBefore(p);
		before.next = before.next.next;
        before.next.previous = before;
	}
	
	public boolean contains(int x, int y) {
		return x >= 0 && x < width
			&& y >= 0 && y < height;
	}
	
	public void removeAll() {
		head.next = tail;
        tail.previous = head;
	}
	
	public PhysicalSpace(int width, int height) {
		this.width = width;
		this.height = height;

		head = new PhysicalNode();
		tail = new PhysicalNode();
		head.next = tail;
	}

    private boolean collides(Physical a, Physical b) {
        return a.getBounds().intersects(b.getBounds());
    }
	
	/**
	 * Find the Node which contains the given Physical
	 */
	private PhysicalNode findBefore(Physical p) {
		PhysicalNode before = head;
		
		while (before.next.physical != p)
			before = before.next;
			
		return before;
	}
	
	private class PhysicalNode {
		Physical physical;
		int y;
		PhysicalNode next;
        PhysicalNode previous;
	}
	
	private PhysicalNode head;
	private PhysicalNode tail;
	private int width;
	private int height;
}
