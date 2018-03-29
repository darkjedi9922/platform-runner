package lab.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class MovingBackground {

	private Bitmap bitmap;
	private Rect rect;
	private int speed;
	private int currentX = 0;
	private int width;
	private int height;
	
	public MovingBackground(Rect r, Bitmap b, int speed) {
		rect = r;
		bitmap = b;
		this.speed = speed;
		width = b.getWidth();
		height = b.getHeight();
	}
	public void draw(Canvas canvas) {
		Rect dst = new Rect(rect.left + currentX, rect.top, rect.left + currentX + width, height + 1);
		///*DEBUG*/  System.out.println("dst: left =" + dst.left + " top = " + dst.top + " right = " + dst.right + " bottom = " + dst.bottom);
		canvas.drawBitmap(bitmap, null, dst, null);
		
		if (dst.right - speed < rect.left) currentX = 0;
		else currentX -= speed;
		
		while (dst.right < rect.right) {
			dst.set(dst.right - 1, dst.top, dst.right + width, dst.bottom);
			canvas.drawBitmap(bitmap, null, dst, null);
		}
	}
	public void setSize(int w, int h) {
		width = w;
		height = h;
	}
}
