package lab.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class MovingBackground implements Background {

	private Bitmap bitmap;
	private int speed;
	private int currentX = 0;
	private int width;
	private int height;
	
	public MovingBackground(Bitmap b, int speed) {
		bitmap = b;
		this.speed = speed;
		width = b.getWidth();
		height = b.getHeight();
	}
	public void draw(Canvas canvas, Rect rect) {
		Rect dst = new Rect(rect.left + currentX, rect.top, rect.left + currentX + width, height + 1);
		canvas.drawBitmap(bitmap, null, dst, null);
		
		if (dst.right - speed < rect.left) currentX = 0;
		else currentX -= speed;
		
		while (dst.right < rect.right) {
			dst.set(dst.right - 1, dst.top, dst.right + width, dst.bottom);
			canvas.drawBitmap(bitmap, null, dst, null);
		}
	}
	public void setBitmapSize(int w, int h) {
		width = w;
		height = h;
	}
}
