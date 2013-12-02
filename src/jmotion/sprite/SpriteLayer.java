package jmotion.sprite;

public interface SpriteLayer extends Iterable<Sprite> {
	public void addSprite(Sprite s);
	public void removeSprite(Sprite s);
	public void removeAll();
}
