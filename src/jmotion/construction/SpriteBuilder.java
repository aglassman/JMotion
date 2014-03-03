package jmotion.construction;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteBuilder {

	public static void main(String[] args) {
		String sourcesPath = "assets/sources/";
		String folderName = "soldier";
		String name = "soldier";

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
		
		sheet.write("assets", folderName);
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
}
