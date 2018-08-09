package lab.game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public class Player {
	
	private QGameRect rect = new QGameRect(0, 0, 0, 0);
	private Drawable background = null;
	
	private AnimatedBitmapDrawable standDrawable;
	private AnimatedBitmapDrawable walkDrawable;
	private AnimatedBitmapDrawable jumpDrawable;
	private AnimatedBitmapDrawable fallDrawable;
	
	public Drawable getBackground() {
		return background;
	}
	public void setBackground(Drawable b) {
		background = b;
	}
	
	public AnimatedBitmapDrawable getStandDrawable() {
		return standDrawable;
	}
	public void setStandDrawable(AnimatedBitmapDrawable standDrawable) {
		this.standDrawable = standDrawable;
	}
	
	public AnimatedBitmapDrawable getWalkDrawable() {
		return walkDrawable;
	}
	public void setWalkDrawable(AnimatedBitmapDrawable walkDrawable) {
		this.walkDrawable = walkDrawable;
	}
	
	
	public AnimatedBitmapDrawable getJumpDrawable() {
		return jumpDrawable;
	}
	public void setJumpDrawable(AnimatedBitmapDrawable jumpDrawable) {
		this.jumpDrawable = jumpDrawable;
	}
	public AnimatedBitmapDrawable getFallDrawable() {
		return fallDrawable;
	}
	public void setFallDrawable(AnimatedBitmapDrawable fallDrawable) {
		this.fallDrawable = fallDrawable;
	}
	public void draw(Canvas canvas) {
		background.setBounds(rect.rect);
		background.draw(canvas);
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
