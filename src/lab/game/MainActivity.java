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
		ImageView pauseButton = (ImageView) findViewById(R.id.pauseButton);
		
		gameView.setListener(this);
		tButton.setOnTouchListener(this);
		pauseButton.setOnTouchListener(this);
		
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
			case R.id.pauseButton:
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (!gameView.isSuspended()) gameView.suspend();
						else gameView.resume();
						break;
				}
				break;
		}
		return true;
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
}
