package lab.game;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class MainGameView extends GameView {
	
	private Map map = new Map(this);
	private Player player = null;
	private boolean playerFalling = false;
	private boolean playerJumping = false;
	private double speedup = 0;
	private double speedupDelta = 0.005;
	private MainGameListener listener = null;
	private Block lastWalkedBlock = null;
	
	private AnimatedBitmapDrawable currentPlayerBackground;
	private AnimatedBitmapDrawable standDrawable;
	private AnimatedBitmapDrawable walkDrawable;
	private AnimatedBitmapDrawable jumpDrawable;
	private AnimatedBitmapDrawable fallDrawable;
	
	public MainGameView(Context context) {
		super(context);
	}
	public MainGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MainGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public void setListener(MainGameListener l) {
		listener = l;
	}
	public void topButtonDown() {
		if (!playerFalling) {
			playerJumping = true;
			player.setBackground(jumpDrawable);
			currentPlayerBackground = jumpDrawable;
		}
	}
	public void topButtonUp() {
		playerJumping = false;
	}
	
	@Override
	public void start() {
		Block startBlock = map.getStartBlock();
		player.setPosition(startBlock.getLeft() + 10, startBlock.getTop() - 1);
		player.setBackground(standDrawable);
		currentPlayerBackground = standDrawable;
		lastWalkedBlock = startBlock;
		listener.gameStarted();
	}
	@Override
	public void restart() {
		map.onRestart();
		playerJumping = false;
		playerFalling = false;
		speedup = 0;
		start();
	}
	@Override 
	protected void onInit() {
		List<Bitmap> stand = new LinkedList<Bitmap>();
		stand.add(BitmapFactory.decodeResource(getResources(), R.drawable.blue_player_stand));
		standDrawable = new AnimatedBitmapDrawable(stand, 1000);
		List<Bitmap> walk = new LinkedList<Bitmap>();
		walk.add(BitmapFactory.decodeResource(getResources(), R.drawable.blue_player_walk1));
		walk.add(BitmapFactory.decodeResource(getResources(), R.drawable.blue_player_walk2));
		walkDrawable = new AnimatedBitmapDrawable(walk, 8);
		List<Bitmap> jump = new LinkedList<Bitmap>();
		jump.add(BitmapFactory.decodeResource(getResources(), R.drawable.blue_player_jump));
		jumpDrawable = new AnimatedBitmapDrawable(jump, 1000);
		List<Bitmap> fall = new LinkedList<Bitmap>();
		fall.add(BitmapFactory.decodeResource(getResources(), R.drawable.blue_player_fall));
		fallDrawable = new AnimatedBitmapDrawable(fall, 1000);
		
		player = new Player(standDrawable, standDrawable.getCurrentBitmap().getWidth(), standDrawable.getCurrentBitmap().getHeight());
		currentPlayerBackground = standDrawable;
		updatePlayerSize();
		map.onStart();
		start();
		listener.gameInitialized();
	}
	@Override
	protected void onRun() {
		try {
			if (currentPlayerBackground == standDrawable) {
				player.setBackground(walkDrawable);
				currentPlayerBackground = walkDrawable;
			}
			map.moveBlocks((int) (player.getWalkSpeed() + speedup));
			map.checkGeneratingBlock();
			speedup += speedupDelta;
		} catch (Exception e) {
			// На телефоне почему-то ошибка
		}
		
		checkPlayerFalling();
		checkPlayerJumping();
		player.setSize(currentPlayerBackground.getCurrentBitmap().getWidth(), currentPlayerBackground.getCurrentBitmap().getHeight());
	}
	@Override
	protected void onPaint(Canvas canvas) {
		map.draw(canvas);
		player.draw(canvas);
	}
	private void checkPlayerFalling() {
		if (playerJumping) return;
		int floor = findPlayerFloor();
		if (floor != -1) {
			if (player.getBottom() < floor) {
				if (player.getBottom() + player.getFallSpeed() < floor) {
					// Еще есть куда падать
					player.move(0, player.getFallSpeed());
					playerFalling = true;
					player.setBackground(fallDrawable);
					currentPlayerBackground = fallDrawable;
				} else {
					player.moveTo(player.getLeft(), floor);
					stopFalling();
				}
			} else {
				player.moveTo(player.getLeft(), floor);
				stopFalling();
			}
		} else if (player.getTop() <= map.getRect().bottom) {
			// Падаем прямо до конца
			player.move(0, player.getFallSpeed());
			playerFalling = true;
			player.setBackground(fallDrawable);
			currentPlayerBackground = fallDrawable;
		} else {
			// Полностью упал
			listener.gameFailed();
		}
	}
	
	private void checkPlayerJumping() {
		if (!playerJumping || playerFalling) return;	
		player.move(0, -player.getJumpSpeed());
	}
	private int findPlayerFloor() {
		int floor = map.findFloor(player.getLeftBottom());
		if (floor == -1) floor = map.findFloor(player.getRightBottom());
		return floor;
	}
	private void stopFalling() {
		playerFalling = false;
		player.setBackground(walkDrawable);
		currentPlayerBackground = walkDrawable;
		// Добавляем очко если приземлились на новый блок
		Block block = map.findBlock(player.getLeft(), player.getBottom() + 1);
		if (block == null) block = map.findBlock(player.getRight(), player.getBottom() + 1);
		if (block != null && block != lastWalkedBlock) {
			lastWalkedBlock = block;
			listener.groundedOnNewBlock();
		}
	}
	private void updatePlayerSize() {
		player.setSize(currentPlayerBackground.getCurrentBitmap().getWidth(), currentPlayerBackground.getCurrentBitmap().getHeight());
	}
}
