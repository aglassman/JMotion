package jmotion.sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SpritePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		sprite.render((Graphics2D) g);
	}

	public SpritePanel(Sprite sprite) {
		this.sprite = sprite;
		setPreferredSize(new Dimension(sprite.getWidth(), sprite.getHeight()));
	}
	
	private Sprite sprite;
}
