
package jmotion.animation;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class AnimatorPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public void showDiagnostics() {
		JFrame window = new JFrame();
		window.setSize(200, 100);

		frameRenderLabel = new JLabel();
		frameSleepLabel = new JLabel();

		window.setLayout(new FlowLayout());
		window.add(new JLabel("render time: "));
		window.add(frameRenderLabel);
		window.add(new JLabel("sleep time: "));
		window.add(frameSleepLabel);
		
		window.setVisible(true);
		window.setLocationRelativeTo(this);
		debugWindow = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	    if (doubleBuffImage != null)
	    	g.drawImage(doubleBuffImage, 0, 0, null);
	}

	public void pauseAnimating() {
		isPaused = true;
	}
	
	public void startAnimating() {
		isPaused = false;
		if (animator == null) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void run() {
		while(true) {
			long frameStart = System.currentTimeMillis();

			if (!isPaused) {
				Point mouse = getMousePosition();
				if (mouse != null) {
					isMouseOver = true;
					mouseX = mouse.x;
					mouseY = mouse.y;
				} else {
					isMouseOver = false;
				}
				mouseClicked = mouseClickedEvent;
				mouseRightClicked = mouseRightEvent;
	
				// Deriving class will step its time forward
				advanceFrame(frameSleep);
	
				doubleBuffImage = createImage(getWidth(), getHeight());
				if (doubleBuffImage != null)
					render((Graphics2D)doubleBuffImage.getGraphics());
	
				// Schedule rendering the buffer
				repaint();
				
				// Reset mouse data, waiting for new events in the sleep time
				mouseClickedEvent = false;
				mouseRightEvent = false;
			}

			int frameRenderTime = (int) (System.currentTimeMillis() - frameStart);
			int sleepTime = Math.max(frameSleep - frameRenderTime, 20);
			
			if (debugWindow) {
				frameRenderLabel.setText(frameRenderTime+"");
				frameSleepLabel.setText(sleepTime+"");
			}
			try {
				Thread.sleep(sleepTime);
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
				mouseClickedEvent = me.getButton() == MouseEvent.BUTTON1;
				mouseRightEvent = me.getButton() == MouseEvent.BUTTON3;
			}
			public void mousePressed(MouseEvent me) {
			}
			public void mouseReleased(MouseEvent me) {
				mouseClickedEvent |= me.getButton() == MouseEvent.BUTTON1;
				mouseRightEvent |= me.getButton() == MouseEvent.BUTTON3;
			}
		};
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	protected abstract void advanceFrame(int millis);

	protected abstract void render(Graphics2D g);
	
	protected int mouseX;
	protected int mouseY;
	protected boolean isMouseOver;
	protected boolean mouseClicked;
	protected boolean mouseRightClicked;

	protected int frameSleep;
	
	private Image doubleBuffImage;
	private Thread animator;
	private int targetFPS;

	private boolean mouseClickedEvent;
	private boolean mouseRightEvent;
	
	private boolean isPaused;
	private boolean debugWindow;
	private JLabel frameSleepLabel;
	private JLabel frameRenderLabel;
}