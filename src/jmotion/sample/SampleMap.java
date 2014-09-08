package jmotion.sample;

import jmotion.tilegame.model.Map;
import jmotion.tilegame.model.Physical;

public class SampleMap extends Map<SampleTile> {
	
	public Physical getHero() {
		return hero;
	}

	public SampleMap(int width, int height, int tileWidth) {
		super(width, height, tileWidth, SampleTile.GRASS);
		hero = new Physical();
        hero.setBounds(-5, -5, 10, 10);
	}

	private Physical hero;

}
