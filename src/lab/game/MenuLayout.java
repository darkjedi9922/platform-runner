package lab.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MenuLayout extends LinearLayout {
	
	private Paint paint;
	
	public MenuLayout(Context context) {
		super(context);
		init();
	}
	public MenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public MenuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	@Override
	public void dispatchDraw(Canvas canvas) {
		RectF rect = new RectF(0, 0, getWidth() - 1, getHeight() - 1);
		canvas.drawRoundRect(rect, 10, 10, paint);
		super.dispatchDraw(canvas);
	}
	
	private void init() {
		this.setWillNotDraw(false);
		paint = new Paint();
		paint.setColor(Color.parseColor("#e1e1e1"));
	}
}
