package lab.game;

import java.io.Console;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Menu implements OnTouchListener {
	
	private MenuLayout layout;
	private MenuButton startButton;
	private MenuButton continueButton;
	private MenuButton switchSoundButton;
	private MenuButton emptyRecordButton;

	public Menu(MenuLayout layout) {
		this.layout = layout;
	}

	public MenuLayout getLayout() {
		return layout;
	}

	public void setLayout(MenuLayout layout) {
		this.layout = layout;
	}

	public MenuButton getStartButton() {
		return startButton;
	}

	public void setStartButton(MenuButton startButton) {
		this.startButton = startButton;
		startButton.setOnTouchListener(this);
	}

	public MenuButton getContinueButton() {
		return continueButton;
	}

	public void setContinueButton(MenuButton button) {
		this.continueButton = button;
	}

	public MenuButton getSwitchSoundButton() {
		return switchSoundButton;
	}

	public void setSwitchSoundButton(MenuButton button) {
		this.switchSoundButton = button;
	}

	public MenuButton getEmptyRecordButton() {
		return emptyRecordButton;
	}

	public void setEmptyRecordButton(MenuButton button) {
		this.emptyRecordButton = button;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.menu_start: {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (Game.startTimes != 0) Game.map.nextLevel();
					layout.setVisibility(View.INVISIBLE);
					Game.pauseButton.setVisibility(View.VISIBLE);
					Game.cycle.start();
					Game.cycle.resume();
					Game.startTimes++;
				}
				break;
			}
		}
		return false;
	}
}