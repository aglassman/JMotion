package jmotion.animation;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class FrameSet {

	public FrameSet clone() {
		FrameSet copy = new FrameSet(width, height);
		copy.framesets = framesets;
		
		return copy;
	}

	public void addFrame(int sequence, BufferedImage frameImage) {
		if (framesets.size() <= sequence)
			framesets.add(new LinkedList<BufferedImage>());

		framesets.get(sequence).add(frameImage);
	}

	public boolean isFinalFrame() {
		return currentFrame == currentSet.size()-1;
	}
	
	public void advanceFrame() {
		++currentFrame;
		if (currentFrame >= currentSet.size())
			currentFrame = 0;
	}
	
	public void setSequence(int sequenceNum) {
		List<BufferedImage> newSet = framesets.get(sequenceNum);
		
		if (currentSet != newSet)
			currentFrame = 0;
		
		currentSet = newSet;
	}
	
	public void beginSequence(int sequenceNum) {
		setSequence(sequenceNum);
		currentFrame = 0;
	}

	public void duplicate(int i) {
		if (i < currentSet.size() && i >= 0)
			currentSet.add(i+1, currentSet.get(i));
	}

	public BufferedImage currentFrame() {
		return currentSet.get(currentFrame);
	}
	
	public Iterable<BufferedImage> getFrames(int sequenceNumber) {
		return framesets.get(sequenceNumber);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public FrameSet(int width, int height) {
		this.width = width;
		this.height = height;
		framesets = new LinkedList<List<BufferedImage>>();
	}
	
	private List<List<BufferedImage>> framesets;
	private List<BufferedImage> currentSet;
	private int currentFrame;
	private int width;
	private int height;
}
