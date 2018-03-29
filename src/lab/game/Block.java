package lab.game;

import android.graphics.Canvas;

public class Block {
	private Background background;
	private QGameRect rect;
	
	public Block(Background background, int width, int height) {
		this.background = background;
		rect = new QGameRect(0, 0, width, height);
	}
	public void draw(Canvas canvas) {
		background.draw(canvas, rect.rect);
	}
	public void setGamePosition(int x, int y) {
		rect.setPosition(x, y);
	}
	public QGameRect getGameRect() {
		return rect;
	}
	public Background getBackground() {
		return background;
	}
	public int getWidth() {
		return rect.rect.width();
	}
	public int getHeight() {
		return rect.rect.height();
	}
	public int getLeft() {
		return rect.rect.left;
	}
	public int getRight() {
		return rect.rect.right;
	}
	public int getTop() {
		return rect.rect.top;
	}
	public int getBottom() {
		return rect.rect.bottom;
	}
}
