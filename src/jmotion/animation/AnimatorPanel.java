
package jmotion.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public abstract class AnimatorPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	    if (doubleBuffImage != null)
	    	g.drawImage(doubleBuffImage, 0, 0, null);
	}

	public void startAnimating() {
		if (animator == null) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void run() {
		while(true) {
			long frameStart = System.currentTimeMillis();

			Point mouse = getMousePosition();
			if (mouse != null) {
				isMouseOver = true;
				mouseX = mouse.x;
				mouseY = mouse.y;
			} else {
				isMouseOver = false;
			}
			mouseClicked = mouseClickedEvent;

			// Deriving class will step its time forward
			advanceFrame(frameSleep);

			doubleBuffImage = createImage(getWidth(), getHeight());
			if (doubleBuffImage != null)
				render((Graphics2D)doubleBuffImage.getGraphics());

			// Schedule rendering the buffer
			repaint();
			
			// Reset mouse data, waiting for new events in the sleep time
			mouseClickedEvent = false;
			
			int frameRenderTime = (int) (System.currentTimeMillis() - frameStart);
			
			try {
				Thread.sleep(Math.max(frameSleep - frameRenderTime, 20));
			} catch (InterruptedException ie) {
				System.out.println("Animation thread interrupted!");
				ie.printStackTrace();
			}
		}
	}

	public int getTargetFPS() {
		return targetFPS;
	}
	
	public void setTargetFPS(int fps) {
		targetFPS = fps;
		frameSleep = 1000/fps;
	}
	
	public AnimatorPanel() {
		setFocusable(true);
		setBackground(Color.WHITE);
		setOpaque(true);
		setTargetFPS(29);
		
		MouseAdapter mouse = new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				mouseClickedEvent = true;
			}
			public void mousePressed(MouseEvent me) {
				mouseDownEvent = true;
			}
			public void mouseReleased(MouseEvent me) {
				mouseClickedEvent |= mouseDownEvent;
				mouseDownEvent = false;
			}
		};
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	protected abstract void advanceFrame(int millis);

	protected abstract void render(Graphics2D g);

	private boolean mouseClickedEvent;
	private boolean mouseDownEvent;
	
	protected int mouseX;
	protected int mouseY;
	protected boolean isMouseOver;
	protected boolean mouseClicked;

	protected int frameSleep;
	
	private Image doubleBuffImage;
	private Thread animator;
	private int targetFPS;	
}