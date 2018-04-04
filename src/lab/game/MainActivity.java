package lab.game;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
	private List<Level> levels = new LinkedList<Level>();
	private Iterator<Level> levelIterator;
	private boolean haveStarted = false;
	private MediaPlayer bckgMusicPlayer;
	
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
		bckgMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
		
		levels.add(new BlueLevel(this));
		levels.add(new YellowLevel(this));
		levels.add(new GreenLevel(this));
		levels.add(new PinkLevel(this));
		levelIterator = levels.iterator();
		
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
		if (record == 0) menuEmptyRecord.setEnabled(false);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			// ������ ����� ����
			case R.id.gameTButton: {
				switch (event.getAction()) {
					// ������
					case MotionEvent.ACTION_DOWN: {
						gameView.topButtonDown();
						break;
					}
					// ��������
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
					if (haveStarted) setLevel();
					menu.setVisibility(View.INVISIBLE);
					pauseButton.setVisibility(View.VISIBLE);
					gameView.start();
					gameView.resume();
					haveStarted = true;
				}
				break;
			}
			case R.id.menu_continue: {
				if (event.getAction() == MotionEvent.ACTION_UP && menuContinue.isEnabled()) {
					menuContinue.onTouchEvent(event); // ������� action_up ���� ������-�� �� ����������
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
					menuEmptyRecord.onTouchEvent(event);
					menuEmptyRecord.setEnabled(false);
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
		bckgMusicPlayer.start();
	}
	@Override
	public void gameFailed() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				pauseButton.setVisibility(View.INVISIBLE);
				menu.setVisibility(View.VISIBLE);
				menuContinue.setEnabled(false);
				if (record != 0) menuEmptyRecord.setEnabled(true);
			}
		});
		gameView.suspend();
	}
	@Override
	public void setLevel() {
		if (!levelIterator.hasNext()) levelIterator = levels.iterator();
		gameView.setLevel(levelIterator.next());
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
