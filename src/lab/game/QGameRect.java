package lab.game;

import android.graphics.Rect;

public class QGameRect {
	
	public Rect rect;
	
	public QGameRect(int left, int bottom, int width, int height) {
		rect = new Rect(left, bottom - height, left + width, bottom);
	}
	public void setPosition(int left, int bottom) {
		rect.offsetTo(left, bottom - rect.height());
	}
	public void set(int left, int bottom, int width, int height) {
		rect.set(left, bottom - height, left + width, bottom);
	}
	public void setSize(int width, int height) {
		set(rect.left, rect.bottom, width, height);
	}
}
