package lab.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class TripleBitmapBackground implements Background {
	
	private Bitmap start;
	private Bitmap center;
	private Bitmap end;

	public TripleBitmapBackground(Bitmap start, Bitmap center, Bitmap end) {
		setBitmaps(start, center, end);
	}
	@Override
	public void draw(Canvas canvas, Rect rect) {
		Rect dst = new Rect(rect);
		dst.right = dst.left + start.getWidth();
		canvas.drawBitmap(start, null, dst, null);
		while (dst.right <= rect.right - end.getWidth() * 2) {
			dst.left = dst.right;
			dst.right = dst.left + center.getWidth();
			canvas.drawBitmap(center, null, dst, null);
		}
		dst.right = rect.right;
		dst.left = dst.right - end.getWidth();
		canvas.drawBitmap(end, null, dst, null);
	}

	public void setBitmaps(Bitmap start, Bitmap center, Bitmap end) {
		this.start = start;
		this.center = center;
		this.end = end;
	}
	public Bitmap getStartBitmap() {
		return start;
	}
	public Bitmap getCenterBitmap() {
		return center;
	}
	public Bitmap getEndBitmap() {
		return end;
	}
}
