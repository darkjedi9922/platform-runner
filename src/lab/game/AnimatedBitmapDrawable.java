package lab.game;

import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class AnimatedBitmapDrawable extends Drawable {
	
	private List<Bitmap> bmps;
	private Iterator<Bitmap> it;
	private Bitmap current = null;
	private int currentIteration = 0;
	private int frequency = 0;
	
	public AnimatedBitmapDrawable(List<Bitmap> bmps, int frequency) {
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
	public void draw(Canvas canvas) {
		if (current != null) canvas.drawBitmap(current, null, getBounds(), null);
		if (currentIteration == frequency) {
			next();
			currentIteration = 0;
		} else currentIteration++;
	}
	@Override
	public void setAlpha(int alpha) {}
	@Override
	public void setColorFilter(ColorFilter cf) {}
	@Override
	public int getOpacity() {return 0;}
}
