package lab.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
	
	private Thread thread;
	private boolean isFirst = true;
	private boolean suspended = false;
	
	public GameView(Context context) {
		super(context);
		initialize();
	}
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}
	
	public void start() {
		// To overriding
	}
	public void suspend() {
		suspended = true;
	}
	public void resume() {
		suspended = false;
	}
	public void restart() {
		// To overriding
	}
	
	private void initialize() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while (true && !suspended) {
			draw();
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void onInit() {
		// To overriding
	}
	protected void onRun() {
		// To overriding
	}
	protected void onPaint(Canvas canvas) {
		// To overriding
	}
	
	private void draw() {
		if (isFirst) {
			if (getWidth() != 0 && getHeight() != 0) {
				//System.out.println("onstart");
				onInit();
				isFirst = false;
			}
		} else {
			//System.out.println("run");
			onRun();
			SurfaceHolder holder = this.getHolder();
			if (!holder.getSurface().isValid()) return;
			Canvas canvas = holder.lockCanvas();
			if (canvas == null) return;
			this.onPaint(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
    }
}
