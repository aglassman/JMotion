package jmotion.sample;

import java.awt.Point;


public class MapGenerator {
	public static SampleMap makeMap(int width, int height) {
		SampleMap map = new SampleMap(width, height, 30);
		
		for (int i = 0; i<600; ++i) {
			int col = (int)(Math.random()*width);
			int row = (int)(Math.random()*height);
			map.set(SampleTile.ROCK, row, col);
		}
		
		Point p = map.getTileCenter(3, 3);	
		map.getHero().setLocation(p.x, p.y);
		
		return map;
	}
}
