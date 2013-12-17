package jmotion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import jmotion.animation.FrameSet;
import jmotion.construction.SpriteSheet;

public class AnimationFactory {

	private static final HashMap<String, FrameSet> cachedAnimations;
	private static final String ASSETS_PATH;

	static {
		cachedAnimations = new HashMap<String, FrameSet>();

		ASSETS_PATH = "assets/";
	}

	public static void main(String[] args) {
		String sourcesPath = "assets/sources/";
		String folderName = "mercenary";
		String name = "merc";

		String[] directions = new String[] {"u", "r", "d", "l"};
		String[] actions = new String[] {"walk", "attack", "die"};

		int numRows = (actions.length+1) * directions.length;
		
		int width = 50;
		int height = 50;
		SpriteSheet sheet = new SpriteSheet(8, numRows, width, height, 0);

		int row = 0;
		for (String dir : directions) {
			// stand
			fillRowFromImage(sheet, 1, sourcesPath+folderName+"/"+name+" "+dir +".gif", row++);
			
			// other actions
			addRowFromVertical(sheet, 4, sourcesPath+folderName+"/"+name+" "+dir+" walk.gif", row++, width, height);
			addRowFromVertical(sheet, 8, sourcesPath+folderName+"/"+name+" "+dir+" attack.gif", row++, width, height);
			++row; // no animation for dying
		}
		
		sheet.write(ASSETS_PATH, folderName);
	}

	private static void addRowFromVertical(SpriteSheet target, int numFrames, String sourceFile, int row, int sourceFrameWidth, int sourceFrameHeight) {
		try {
			BufferedImage i = ImageIO.read(new File(sourceFile));
			
			for (int f = 0; f<numFrames; ++f) {
				try {
					Image frame = i.getSubimage(0, f*sourceFrameHeight, sourceFrameWidth, sourceFrameHeight);
					target.drawCenteredFrame(frame, row, f);
				} catch (RasterFormatException rfe) {
					
				}
			}
		} catch (IOException e) {
			System.out.println("Couldn't find " + sourceFile);
		}
	}

	private static void fillRowFromImage(SpriteSheet target, int numFrames, String sourceFile, int row) {
		try {
			BufferedImage i = ImageIO.read(new File(sourceFile));
			
			for (int f = 0; f<numFrames; ++f) {
				target.drawCenteredFrame(i, row, f);
			}
		} catch (IOException e) {
			System.out.println("Couldn't find " + sourceFile);
		}
	}
	
	/**
	 * Creates a new Sprite-Sheet and corresponding Manifest. The Manifest records the metadata for the
	 * FrameSetAnimation to be built from the Sprite-Sheet.
	 */
	public static void createNew(String name, int frameWidth, int frameHeight, int frameBuffer, int framesLong, int numRows) {		
		int sheetWidth = frameWidth * framesLong + (framesLong+1)*frameBuffer;
		int sheetHeight = frameHeight * numRows + (numRows+1)*frameBuffer;
		
		BufferedImage sheetImage = new BufferedImage(sheetWidth, sheetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D sheetGraphics = (Graphics2D)sheetImage.getGraphics();
		
		sheetGraphics.setColor(Color.red);

		// vertical gridlines
		for (int c = 0; c<framesLong; ++c) {
			int colX = c * (frameWidth+frameBuffer);
			sheetGraphics.fillRect(colX, 0, frameBuffer, sheetHeight);
		}
		sheetGraphics.fillRect(sheetWidth-frameBuffer, 0, sheetWidth, sheetHeight);

		// horizontal gridlines
		for (int r = 0; r<numRows; ++r) {
			int rowY = r * (frameHeight+frameBuffer);
			sheetGraphics.fillRect(0, rowY, sheetWidth, frameBuffer);
		}
		sheetGraphics.fillRect(0, sheetHeight-frameBuffer, sheetWidth, frameBuffer);
		
		// File IO
		try {
			// Save the Sprite-Sheet
			ImageIO.write(sheetImage, "gif", new File(ASSETS_PATH + name + ".gif"));
			
			// Save the Manifest
			FileWriter f = new FileWriter(new File(ASSETS_PATH + name + ".txt"));
			f.write(frameWidth + " " + frameHeight + " " + framesLong + " " + frameBuffer + "\n");
			
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load a Manifest and a Sprite-Sheet and use them to construct a FrameSetAnimation
	 */
	public static FrameSet get(String name) {
		name = name.toLowerCase();
		if (cachedAnimations.containsKey(name))
			return cachedAnimations.get(name).clone();
		
		Scanner manifestScanner = null;
		try {
			BufferedImage sheetImage = ImageIO.read(new File(ASSETS_PATH + name + ".gif"));

			manifestScanner =  new Scanner(new File(ASSETS_PATH + name + ".txt"));
	
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
		} catch (IOException e) {
			return null;
		} finally {
			if (manifestScanner != null)
				manifestScanner.close();
		}
	}

	private static int columnX(int column, int frameWidth, int frameBuffer) {
		return frameBuffer + column * (frameBuffer+frameWidth);
	}

	private static int rowY(int row, int frameHeight, int frameBuffer) {
		return frameBuffer + row * (frameBuffer+frameHeight);
	}
}
