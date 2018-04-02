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

	private MainGameView gameView = null;
	private ImageView pauseButton = null;
	private MenuLayout menu = null;
	private MenuButton menuContinue;
	private MenuButton menuEmptyRecord;
	private TextView scoreNumber;
	private TextView scoreRecord;
	private int points = 0;
	private int record = 0;
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		gameView = (MainGameView) this.findViewById(R.id.game);
		Button tButton = (Button) this.findViewById(R.id.gameTButton);
		TextView scoreText = (TextView) findViewById(R.id.score_text);
		TextView scoreSlash = (TextView) findViewById(R.id.score_slash);
		scoreNumber = (TextView) findViewById(R.id.score_number);
		scoreRecord = (TextView) findViewById(R.id.score_record);
		pauseButton = (ImageView) findViewById(R.id.pauseButton);
		menu = (MenuLayout) findViewById(R.id.menu);
		MenuButton menuStart = (MenuButton) findViewById(R.id.menu_start);
		menuContinue = (MenuButton) findViewById(R.id.menu_continue);
		menuEmptyRecord = (MenuButton) findViewById(R.id.menu_emptyRecord);
		MenuButton menuExit = (MenuButton) findViewById(R.id.menu_exit);
		
		gameView.setListener(this);
		tButton.setOnTouchListener(this);
		pauseButton.setOnTouchListener(this);
		menuStart.setOnTouchListener(this);
		menuContinue.setOnTouchListener(this);
		menuEmptyRecord.setOnTouchListener(this);
		menuExit.setOnTouchListener(this);
		
		Typeface comicFont = Typeface.createFromAsset(getAssets(), "comicb.ttf");
		scoreText.setTypeface(comicFont);
		scoreSlash.setTypeface(comicFont);
		scoreNumber.setTypeface(comicFont);
		scoreRecord.setTypeface(comicFont);
		
		settings = this.getSharedPreferences("save", MODE_PRIVATE);
		loadRecord();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			// Кнопка самой игры
			case R.id.gameTButton: {
				switch (event.getAction()) {
					// Нажата
					case MotionEvent.ACTION_DOWN: {
						gameView.topButtonDown();
						break;
					}
					// Отпущена
					case MotionEvent.ACTION_UP: {
						gameView.topButtonUp();
						break;
					}
				}
				break;
			}
			case R.id.pauseButton: {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.suspend();
					pauseButton.setVisibility(View.INVISIBLE);
					menu.setVisibility(View.VISIBLE);
					menuContinue.setEnabled(true);
					menuEmptyRecord.setEnabled(false);
				}
				break;
			}
			case R.id.menu_start: {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					menu.setVisibility(View.INVISIBLE);
					pauseButton.setVisibility(View.VISIBLE);
					gameView.restart();
					gameView.resume();
				}
				break;
			}
			case R.id.menu_continue: {
				if (event.getAction() == MotionEvent.ACTION_UP && menuContinue.isEnabled()) {
					menuContinue.onTouchEvent(event); // событие action_up само почему-то не вызывается
					menuContinue.setEnabled(false);
					menu.setVisibility(View.INVISIBLE);
					pauseButton.setVisibility(View.VISIBLE);
					gameView.resume();
				}
				break;
			}
			case R.id.menu_emptyRecord: {
				if (event.getAction() == MotionEvent.ACTION_UP && menuEmptyRecord.isEnabled()) {
					emptyRecord();
				}
				break;
			}
			case R.id.menu_exit: {
				if (event.getAction() == MotionEvent.ACTION_UP) this.finish();
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
		gameView.suspend();
	}
	@Override
	public void gameFailed() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				pauseButton.setVisibility(View.INVISIBLE);
				menu.setVisibility(View.VISIBLE);
				menuContinue.setEnabled(false);
				menuEmptyRecord.setEnabled(true);
			}
		});
		gameView.suspend();
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
