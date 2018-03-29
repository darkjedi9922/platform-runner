package lab.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Block {
	private Bitmap bitmap;
	private QGameRect rect;
	
	public Block(Bitmap bitmap) {
		this.bitmap = bitmap;
		rect = new QGameRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, null, rect.rect, null);
	}
	public void setGamePosition(int x, int y) {
		rect.setPosition(x, y);
	}
	public QGameRect getGameRect() {
		return rect;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public int getWidth() {
		return bitmap.getWidth();
	}
	public int getHeight() {
		return bitmap.getHeight();
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
