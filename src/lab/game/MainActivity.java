package lab.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener, MainGameListener {

	private Menu menu = new Menu(null);
	private TextView scoreNumber;
	private TextView scoreRecord;
	private int points = 0;
	private int record = 0;
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Game.cycle = (MainGameView) this.findViewById(R.id.game);
		Button tButton = (Button) this.findViewById(R.id.gameTButton);
		TextView scoreText = (TextView) findViewById(R.id.score_text);
		TextView scoreSlash = (TextView) findViewById(R.id.score_slash);
		scoreNumber = (TextView) findViewById(R.id.score_number);
		scoreRecord = (TextView) findViewById(R.id.score_record);
		Game.pauseButton = (ImageView) findViewById(R.id.pauseButton);
		menu.setLayout((MenuLayout) findViewById(R.id.menu));
		menu.setStartButton((MenuButton) findViewById(R.id.menu_start));
		menu.setContinueButton((MenuButton) findViewById(R.id.menu_continue));
		menu.setSwitchSoundButton((MenuButton) findViewById(R.id.menu_switchSounds));
		menu.setEmptyRecordButton((MenuButton) findViewById(R.id.menu_emptyRecord));
		MenuButton menuExit = (MenuButton) findViewById(R.id.menu_exit);
		
		Game.sounds = new Sounds(this);
		Game.map.addLevel(new BlueLevel(this));
		Game.map.addLevel(new YellowLevel(this));
		Game.map.addLevel(new GreenLevel(this));
		Game.map.addLevel(new PinkLevel(this));
		
		Game.cycle.setListener(this);
		tButton.setOnTouchListener(this);
		Game.pauseButton.setOnTouchListener(this);
		
		menu.getContinueButton().setOnTouchListener(this);
		menu.getSwitchSoundButton().setOnTouchListener(this);
		menu.getEmptyRecordButton().setOnTouchListener(this);
		menuExit.setOnTouchListener(this);
		
		Typeface comicFont = Typeface.createFromAsset(getAssets(), "comicb.ttf");
		scoreText.setTypeface(comicFont);
		scoreSlash.setTypeface(comicFont);
		scoreNumber.setTypeface(comicFont);
		scoreRecord.setTypeface(comicFont);
		
		settings = this.getSharedPreferences("save", MODE_PRIVATE);
		loadRecord();
		Game.sounds.setEnabled(loadSoundSetting());
		if (record == 0) menu.getEmptyRecordButton().setEnabled(false);
		if (!Game.sounds.isEnabled()) menu.getSwitchSoundButton().setText(R.string.on_sounds);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.gameTButton: {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						Game.cycle.topButtonDown();
						break;
					}
					case MotionEvent.ACTION_UP: {
						Game.cycle.topButtonUp();
						break;
					}
				}
				break;
			}
			case R.id.pauseButton: {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Game.sounds.playClick();
					Game.cycle.suspend();
					Game.pauseButton.setVisibility(View.INVISIBLE);
					menu.getLayout().setVisibility(View.VISIBLE);
					menu.getContinueButton().setEnabled(true);
					menu.getEmptyRecordButton().setEnabled(false);
				}
				break;
			}
			case R.id.menu_continue: {
				if (event.getAction() == MotionEvent.ACTION_UP && menu.getContinueButton().isEnabled()) {
					menu.getContinueButton().onTouchEvent(event);
					menu.getContinueButton().setEnabled(false);
					menu.getLayout().setVisibility(View.INVISIBLE);
					Game.pauseButton.setVisibility(View.VISIBLE);
					Game.cycle.resume();
				}
				break;
			}
			case R.id.menu_switchSounds: {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (Game.sounds.isEnabled()) {
						Game.sounds.setEnabled(false);
						menu.getSwitchSoundButton().setText(R.string.on_sounds);
						saveSoundSetting(false);
					} else {
						Game.sounds.setEnabled(true);
						menu.getSwitchSoundButton().setText(R.string.off_sounds);
						saveSoundSetting(true);
						Game.sounds.playClick();
					}
				}
				break;
			}
			case R.id.menu_emptyRecord: {
				if (event.getAction() == MotionEvent.ACTION_UP && menu.getEmptyRecordButton().isEnabled()) {
					emptyRecord();
					menu.getEmptyRecordButton().onTouchEvent(event);
					menu.getEmptyRecordButton().setEnabled(false);
				}
				break;
			}
			case R.id.menu_exit: {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					this.finish();
				}
				break;
			}
		}
		return false;
	}
	@Override
	public void gameStarted() {
		points = 0;
		updatePointsLabel();
	}
	@Override
	public void startedJumping() {
		// Here is nothing to do
	}
	@Override
	public void groundedOnNewBlock() {
		points += 1;
		if (points > record) {
			record = points;
			saveRecord();
		}
		updatePointsLabel();
	}
	@Override
	public void gameInitialized() {
		Game.cycle.suspend();
	}
	@Override
	public void gameFailed() {
		Game.sounds.playFall();
		this.runOnUiThread(new Runnable() {
			public void run() {
				Game.pauseButton.setVisibility(View.INVISIBLE);
				menu.getLayout().setVisibility(View.VISIBLE);
				menu.getContinueButton().setEnabled(false);
				if (record != 0) menu.getEmptyRecordButton().setEnabled(true);
			}
		});
		Game.cycle.suspend();
	}
	
	private void updatePointsLabel() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				scoreNumber.setText(String.valueOf(points));
				scoreRecord.setText(String.valueOf(record));
			}
		});
	}
	private void loadRecord() {
		record = settings.getInt("record", 0);
	}
	private void saveRecord() {
		Editor editor = settings.edit();
		editor.putInt("record", record);
		editor.apply();
	}
	private boolean loadSoundSetting() {
		return settings.getBoolean("sound", true);
	}
	private void saveSoundSetting(boolean value) {
		Editor editor = settings.edit();
		editor.putBoolean("sound", value);
		editor.apply();
	}
	private void emptyRecord() {
		Editor editor = settings.edit();
		editor.putInt("record", 0);
		editor.apply();
		points = 0;
		record = 0;
		scoreNumber.setText("0");
		scoreRecord.setText("0");
	}
}
