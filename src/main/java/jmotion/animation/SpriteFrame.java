package jmotion.animation;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class SpriteFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	public final Image image;
	public final int width;
	public final int height;
	
	public SpriteFrame(Image image) {
		this.image = image;
		ImageIcon icon = new ImageIcon(image);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
	}
}
