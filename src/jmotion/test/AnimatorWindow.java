package jmotion.test;

import java.awt.Dimension;

import javax.swing.JFrame;

public class AnimatorWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public DefaultAnimatorPanel getPanel() {
		return panel;
	}
	
	public void start() {
		panel.startAnimating();
	}
	
	public AnimatorWindow() {
		panel = new DefaultAnimatorPanel();
		panel.setSize(new Dimension(800, 600));
		panel.setPreferredSize(new Dimension(800, 600));
		add(panel);
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private DefaultAnimatorPanel panel;
}
