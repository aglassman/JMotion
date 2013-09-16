package jmotion.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import jmotion.animation.Animation;
import jmotion.animation.AnimatorPanel;
import jmotion.sprite.Sprite;

public class DefaultAnimatorPanel extends AnimatorPanel {
	private static final long serialVersionUID = 1L;

	public void addAnimation(Animation a) {
		animations.add(a);
		a.start();
	}
	
	public void addSprite(Sprite s) {
		sprites.add(s);
	}
	
	protected void advanceFrame(int millis) {
		LinkedList<Animation> finishedAnimations = new LinkedList<Animation>();
		for (Animation a : animations) {
			a.stepAhead(millis);
			if (a.isFinished())
				finishedAnimations.add(a);
		}
		
		animations.removeAll(finishedAnimations);
	}

	protected void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (Sprite s : sprites)
			s.render(g);
	}

	public DefaultAnimatorPanel() {
		sprites = new LinkedList<Sprite>();
		animations = new LinkedList<Animation>();
	}
	
	private LinkedList<Sprite> sprites;
	private LinkedList<Animation> animations;
}
