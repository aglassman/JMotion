package jmotion.test;

import jmotion.animation.AnimationSequence;
import jmotion.animation.LoopAnimation;
import jmotion.animation.MoveSprite;
import jmotion.sprite.Sprite;
import jmotion.sprite.StaticSprite;

public class AnimationTest {
	public static void main(String... args) {
		AnimatorWindow window = new AnimatorWindow();
		
		DefaultAnimatorPanel panel = window.getPanel();
		Sprite staticSmiley = new StaticSprite("assets/smiley.gif");
		staticSmiley.setLocation((panel.getWidth()-staticSmiley.getWidth())/2, (panel.getHeight()-staticSmiley.getHeight())/2);
		panel.addSprite(staticSmiley);
		
		Sprite movingSprite = new StaticSprite("assets/smiley.gif");
		panel.addSprite(movingSprite);
		panel.addAnimation(
				new LoopAnimation(
					new AnimationSequence(
							new MoveSprite(movingSprite, 500, 0, 10),
							new MoveSprite(movingSprite, 500, 500, 10),
							new MoveSprite(movingSprite, 0, 500, 10),
							new MoveSprite(movingSprite, 0, 0, 10)
							)));
		
		window.start();
	}
}
