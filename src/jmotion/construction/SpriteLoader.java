package jmotion.construction;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import jmotion.animation.FrameSet;
import jmotion.sprite.StaticSprite;

public class SpriteLoader {

	public StaticSprite getStaticSprite(String name) {		;
		try {
			BufferedImage i = ImageIO.read(new File(assetFolder + name));
			return new StaticSprite(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int columnX(int column, int frameWidth, int frameBuffer) {
		return frameBuffer + column * (frameBuffer+frameWidth);
	}

	private static int rowY(int row, int frameHeight, int frameBuffer) {
		return frameBuffer + row * (frameBuffer+frameHeight);
	}
	
	/**
	 * Load a Manifest and a Sprite-Sheet and use them to construct a FrameSetAnimation
	 */
	public FrameSet getFrames(String name) {
		name = name.toLowerCase();
		if (cachedAnimations.containsKey(name))
			return cachedAnimations.get(name).clone();
		
		String filePath = assetFolder + name;		
		try (Scanner manifestScanner = new Scanner(new File(filePath+".txt"))) {
			BufferedImage sheetImage = ImageIO.read(new File(filePath + ".gif"));
	
			String[] numbers = manifestScanner.nextLine().split(" ");
			int numRows = Integer.parseInt(numbers[0]);
			int frameWidth = Integer.parseInt(numbers[2]);
			int frameHeight = Integer.parseInt(numbers[3]);
			int frameBuffer = Integer.parseInt(numbers[4]);
			String[] lengths = manifestScanner.nextLine().split(" ");

			FrameSet animation = new FrameSet(frameWidth, frameHeight);
			for (int row = 0; row < numRows; ++row) {
				int rowLength = Integer.parseInt(lengths[row]);
				for (int f = 0; f < rowLength; ++f) {
					int x = columnX(f, frameWidth, frameBuffer);
					int y = rowY(row, frameHeight, frameBuffer);

					BufferedImage frameImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
					Graphics2D frameGraphics = (Graphics2D) frameImage.getGraphics();
					frameGraphics.drawImage(sheetImage.getSubimage(x, y, frameWidth, frameHeight), 0, 0, null);

					animation.addFrame(row, frameImage);
				}
			}
			cachedAnimations.put(name, animation);
			
			return animation;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public SpriteLoader(String assetFolder) {
		this.assetFolder = assetFolder + "/";
		cachedAnimations = new HashMap<>();
	}
	
	private HashMap<String, FrameSet> cachedAnimations;
	private String assetFolder;
}
