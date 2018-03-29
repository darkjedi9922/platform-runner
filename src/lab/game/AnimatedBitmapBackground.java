package lab.game;

import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimatedBitmapBackground implements Background {
	
	private List<Bitmap> bmps;
	private Iterator<Bitmap> it;
	private Bitmap current = null;
	private int currentIteration = 0;
	private int frequency = 0;
	
	public AnimatedBitmapBackground(List<Bitmap> bmps, int frequency) {
		this.bmps = bmps;
		this.frequency = frequency;
		it = bmps.iterator();
		next();
	}
	public Bitmap getCurrentBitmap() {
		return current;
	}
	private void next() {
		if (!it.hasNext()) it = bmps.iterator();
		current = it.next();
	}
	@Override
	public void draw(Canvas canvas, Rect rect) {
		if (current != null) canvas.drawBitmap(current, null, rect, null);
		if (currentIteration == frequency) {
			next();
			currentIteration = 0;
		} else currentIteration++;
	}
}
