package lab.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnTouchListener;

public class MenuButton extends Button {

	private int enabledTextColor = Color.parseColor("#37393b");
	private int disabledTextColor = Color.parseColor("#b0b0b0");
	private int touchedTextColor = Color.parseColor("#797979");
	private boolean isTouched = false;
	
	public MenuButton(Context context) {
		super(context);
		init();
	}
	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public MenuButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isEnabled()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isTouched = true;
				updateTextColor();
				break;
			case MotionEvent.ACTION_UP:
				isTouched = false;
				updateTextColor();
				break;
			}
		}
		return isTouched;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateTextColor(); 
	}
	
	private void init() {
		Typeface font = Typeface.createFromAsset(getContext().getAssets(), "comicb.ttf");
		
		this.setTypeface(font);
		this.setBackground(null);
		updateTextColor();
	}
	private void updateTextColor() {
		if (isTouched) setTextColor(touchedTextColor);
		else if (this.isEnabled()) setTextColor(enabledTextColor);
		else setTextColor(disabledTextColor);
	}
}
