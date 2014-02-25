package jmotion.sample;

import javax.swing.JFrame;

import jmotion.tilegame.TileGamePanel;

public class RunSample {
	public static void main(String... args) {
		SampleMap map = MapGenerator.makeMap(100, 100);
		TileGamePanel<SampleTile> game = new SampleMapPanel(map, 300, 300);
		
		JFrame window = new JFrame("Sample Game");
		window.add(game);
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		game.startAnimating();
	}
}
