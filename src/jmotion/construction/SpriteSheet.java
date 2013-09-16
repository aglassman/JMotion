package jmotion.construction;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public void write(String filepath, String name) {
		try {
			// Save the Sprite-Sheet
			ImageIO.write(sheetImage, "gif", new File(filepath + name + ".gif"));
			
			// Save the Manifest
			FileWriter f = new FileWriter(new File(filepath + name + ".txt"));
			f.write(numRows + " " + frames + " " + frameWidth + " " + frameHeight + " " + margin + "\n");

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getNumFrames() {
		return frames;
	}
	
	public void drawCenteredFrame(Image img, int row, int frame) {
		int x = getXForFrame(frame) + (frameWidth - img.getWidth(null))/2;
		int y = getYForRow(row) + (frameHeight-img.getHeight(null))/2;
		
		sheetGraphics.drawImage(img, x, y, null);
	}

	public SpriteSheet(int frames, int numRows, int frameWidth, int frameHeight, int margin) {
		this.frames = frames;
		this.numRows = numRows;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.margin = margin;

		int sheetWidth = frameWidth * frames + (frames+1)*margin;
		int sheetHeight = frameHeight * numRows + (numRows+1)*margin;
		
		sheetImage = new BufferedImage(sheetWidth, sheetHeight, BufferedImage.TYPE_INT_ARGB);
		sheetGraphics = (Graphics2D)sheetImage.getGraphics();

		sheetGraphics.setColor(Color.red);

		// vertical gridlines
		for (int c = 0; c<frames; ++c) {
			int colX = c * (frameWidth+margin);
			sheetGraphics.fillRect(colX, 0, margin, sheetHeight);
		}
		sheetGraphics.fillRect(sheetWidth-margin, 0, sheetWidth, sheetHeight);
		
		// horizontal gridlines
		for (int r = 0; r<numRows; ++r) {
			int rowY = r * (frameHeight+margin);
			sheetGraphics.fillRect(0, rowY, sheetWidth, margin);
		}
		sheetGraphics.fillRect(0, sheetHeight-margin, sheetWidth, margin);
	}

	private int getXForFrame(int frame) {
		return frame*(frameWidth+margin) + margin;
	}

	private int getYForRow(int row) {
		return row*(frameHeight+margin) + margin;
	}
	
	private int frameWidth;
	private int frameHeight;
	private int frames;
	private int numRows;
	private int margin;
	
	private BufferedImage sheetImage;
	private Graphics2D sheetGraphics;
}
