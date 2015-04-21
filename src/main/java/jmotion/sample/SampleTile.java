package jmotion.sample;

import java.awt.Color;

import jmotion.tilegame.model.MapTile;

public class SampleTile extends MapTile {

	public static final SampleTile GRASS = new SampleTile(Color.green);
	public static final SampleTile ROCK = new SampleTile(Color.black);
	
	public final Color color;
	
	public SampleTile(Color color) {
		this.color = color;
	}
}
