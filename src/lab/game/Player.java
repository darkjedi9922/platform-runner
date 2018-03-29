package lab.game;

import android.graphics.Canvas;
import android.graphics.Point;

public class Player {
	
	private QGameRect rect = new QGameRect(0, 0, 0, 0);
	private Background background;
	
	public Player(Background background) {
		setBackground(background);
	}
	public void setBackground(Background b) {
		background = b;
	}
	public void draw(Canvas canvas) {
		background.draw(canvas, rect.rect);
	}
	public void setSize(int width, int height) {
		rect.setSize(width, height);
	}
	public int getWidth() {
		return rect.rect.width();
	}
	public int getHeight() {
		return rect.rect.height();
	}
	public void setPosition(int left, int bottom) {
		rect.setPosition(left, bottom);
	}
	public int getLeft() {
		return rect.rect.left;
	}
	public int getTop() {
		return rect.rect.top;
	}
	public int getRight() {
		return rect.rect.right;
	}
	public int getBottom() {
		return rect.rect.bottom;
	}
	public Point getLeftBottom() {
		return new Point(rect.rect.left, rect.rect.bottom);
	}
	public Point getRightBottom() {
		return new Point(rect.rect.right, rect.rect.bottom);
	}
	public int getWalkSpeed() {
		return 6;
	}
	public int getFallSpeed() {
		return 10;
	}
	public int getJumpMaxHeight() {
		return 225;
	}
	public int getJumpSpeed() {
		return 10;
	}
	public void move(int dx, int dy) {
		rect.rect.offset(dx, dy);
	}
	public void moveTo(int left, int bottom) {
		rect.setPosition(left, bottom);
	}
}
