package jmotion.test;

import jmotion.animation.AnimationSequence;
import jmotion.animation.FrameSet;
import jmotion.animation.LoopAnimation;
import jmotion.animation.MoveSprite;
import jmotion.animation.PlayFramesAnimation;
import jmotion.construction.SpriteLoader;
import jmotion.sprite.FramesSprite;
import jmotion.sprite.Sprite;

public class AnimationTest {
	public static void main(String... args) {
		AnimatorWindow window = new AnimatorWindow();
		SpriteLoader factory = new SpriteLoader("assets/");
		
		DefaultAnimatorPanel panel = window.getPanel();
		Sprite staticSmiley = factory.getStaticSprite("smiley.gif");
		staticSmiley.setLocation((panel.getWidth()-staticSmiley.getWidth())/2, (panel.getHeight()-staticSmiley.getHeight())/2);
		panel.addSprite(staticSmiley);
		
		// A smiley face moving around the panel in a square
		Sprite movingSprite = factory.getStaticSprite("smiley.gif");
		panel.addSprite(movingSprite);
		panel.addAnimation(
				new LoopAnimation(
					new AnimationSequence(
							new MoveSprite(movingSprite, 500, 0, 10),
							new MoveSprite(movingSprite, 500, 500, 10),
							new MoveSprite(movingSprite, 0, 500, 10),
							new MoveSprite(movingSprite, 0, 0, 10)
							)));
		
		// Test playing through frames - should end on blue "2"
		FrameSet testFrames = factory.getFrames("testframes");
		Sprite frameSprite = new FramesSprite(testFrames);
		panel.addSprite(frameSprite);
		panel.addAnimation(new PlayFramesAnimation(testFrames, 0));
				
		// Test looping through multiple sequences - should end on red "2"
		FrameSet testFrames2 = factory.getFrames("testframes");
		Sprite frameSprite2 = new FramesSprite(testFrames2);
		frameSprite2.setLocation(100, 0);
		panel.addSprite(frameSprite2);
		panel.addAnimation(
				new LoopAnimation(
					new AnimationSequence(
							new PlayFramesAnimation(testFrames2, 0),
							new PlayFramesAnimation(testFrames2, 1)
							)));

		window.start();
	}
}
