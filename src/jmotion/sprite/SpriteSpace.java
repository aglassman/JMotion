package jmotion.sprite;

import java.util.Iterator;

/**
 * Maintains a set of Sprites in datastructure such that they can be efficiently
 * retrieved in a y-sorted order.
 * 
 * This allows Sprites to be rendered so that their overlap pattern is appropriate.
 *
 */
public class SpriteSpace implements SpriteLayer {

	public Iterator<Sprite> iterator() {
		return new Iterator<Sprite>() {
			public boolean hasNext() {
				return current != tail;
			}

			public Sprite next() {
				Sprite next = current.sprite;
				current = current.next;
				return next;
			}

			public void remove() {
			}
			
			SpriteNode current = head.next;
		};
	}
	
	public void moveSprite(Sprite s, int deltaX, int deltaY) {
		s.setLocation(s.getX()+deltaX, s.getY()+deltaY);
		
		SpriteNode before = findBefore(s);
		SpriteNode node = before.next;
		node.y = s.getY();
		
		// remove the node
		before.next = node.next;
		
		// Bubble the node up from the beginning of the list
		tail.y = node.y + 1;
		SpriteNode current = head;
		while (current.next.y < node.y)
			current = current.next;
		
		node.next = current.next;
		current.next = node;
	}
	
	public void addSprite(Sprite s) {
		SpriteNode node = new SpriteNode();
		node.sprite = s;
		node.y = s.getY();
		
		tail.y = s.getY() + 1;
		
		SpriteNode current = head;
		while (current.next.y < node.y)
			current = current.next;
		
		node.next = current.next;
		current.next = node;
	}
	
	public void removeSprite(Sprite s) {
		SpriteNode before = findBefore(s);
		before.next = before.next.next;
	}
	
	public void removeAll() {
		head.next = tail;
	}
	
	public SpriteSpace() {
		head = new SpriteNode();
		tail = new SpriteNode();
		head.next = tail;
	}
	
	/**
	 * Find the Node which points to the one containing the given sprite
	 */
	private SpriteNode findBefore(Sprite s) {
		SpriteNode before = head;
		
		while (before.next.sprite != s)
			before = before.next;
			
		return before;
	}
	
	private class SpriteNode {
		Sprite sprite;
		int y;
		SpriteNode next;
	}
	
	private SpriteNode head;
	private SpriteNode tail;
}
