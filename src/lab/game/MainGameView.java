package lab.game;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class MainGameView extends GameView {
	
	private boolean playerFalling = false;
	private boolean playerJumping = false;
	private double speedup = 0;
	private double speedupDelta = 0.005;
	private MainGameListener listener = null;
	private Block lastWalkedBlock = null;
	
	public MainGameView(Context context) {
		super(context);
		constructor();
	}
	public MainGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		constructor();
	}
	public MainGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		constructor();
	}
	
	private void constructor()
	{
		Game.map = new Map(this);
	}
	
	public void setLevel(Level level) {
		
	}
	public void setListener(MainGameListener l) {
		listener = l;
	}
	public void topButtonDown() {
		if (!playerFalling) {
			playerJumping = true;
			Game.map.getPlayer().setBackground(Game.map.getPlayer().getJumpDrawable());
			listener.startedJumping();
		}
	}
	public void topButtonUp() {
		playerJumping = false;
	}
	
	@Override
	public void start() {
		Game.map.onStart();
		playerJumping = false;
		playerFalling = false;
		speedup = 0;
		Block startBlock = Game.map.getStartBlock();
		Game.map.getPlayer().setPosition(startBlock.getLeft() + 10, startBlock.getTop() - 1);
		Game.map.getPlayer().setBackground(Game.map.getPlayer().getStandDrawable());
		updatePlayerSize();
		lastWalkedBlock = startBlock;
		listener.gameStarted();
	}
	@Override
	protected void onInit() {
		Game.map.setPlayer(new Player());
		Game.map.getPlayer().setStandDrawable(new AnimatedBitmapDrawable(1000));
		Game.map.getPlayer().setWalkDrawable(new AnimatedBitmapDrawable(8));
		Game.map.getPlayer().setJumpDrawable(new AnimatedBitmapDrawable(1000));
		Game.map.getPlayer().setFallDrawable(new AnimatedBitmapDrawable(1000));
		
		Game.map.nextLevel();
		
		Game.map.onStart();
		start();
		listener.gameInitialized();
	}
	@Override
	protected void onRun() {
		try {
			if (Game.map.getPlayer().getBackground() == Game.map.getPlayer().getStandDrawable()) {
				Game.map.getPlayer().setBackground(Game.map.getPlayer().getWalkDrawable());
			}
			Game.map.moveBlocks((int) (Game.map.getPlayer().getWalkSpeed() + speedup));
			speedup += speedupDelta;
		} catch (Exception e) {
			// На телефоне почему-то ошибка
		}
		
		checkPlayerFalling();
		checkPlayerJumping();
		updatePlayerSize();
	}
	@Override
	protected void onPaint(Canvas canvas) {
		Game.map.draw(canvas);
		Game.map.getPlayer().draw(canvas);
	}
	private void checkPlayerFalling() {
		if (playerJumping) return;
		int floor = findPlayerFloor();
		if (floor != -1) {
			if (Game.map.getPlayer().getBottom() < floor) {
				if (Game.map.getPlayer().getBottom() + Game.map.getPlayer().getFallSpeed() < floor) {
					// Еще есть куда падать
					Game.map.getPlayer().move(0, Game.map.getPlayer().getFallSpeed());
					playerFalling = true;
					Game.map.getPlayer().setBackground(Game.map.getPlayer().getFallDrawable());
				} else {
					Game.map.getPlayer().moveTo(Game.map.getPlayer().getLeft(), floor);
					stopFalling();
				}
			} else {
				Game.map.getPlayer().moveTo(Game.map.getPlayer().getLeft(), floor);
				stopFalling();
			}
		} else if (Game.map.getPlayer().getTop() <= Game.map.getRect().bottom) {
			// Падаем прямо до конца
			Game.map.getPlayer().move(0, Game.map.getPlayer().getFallSpeed());
			playerFalling = true;
			Game.map.getPlayer().setBackground(Game.map.getPlayer().getFallDrawable());
		} else {
			// Полностью упал
			listener.gameFailed();
		}
	}
	
	private void checkPlayerJumping() {
		if (!playerJumping || playerFalling) return;	
		Game.map.getPlayer().move(0, -Game.map.getPlayer().getJumpSpeed());
	}
	private int findPlayerFloor() {
		int floor = Game.map.findFloor(Game.map.getPlayer().getLeftBottom());
		if (floor == -1) floor = Game.map.findFloor(Game.map.getPlayer().getRightBottom());
		return floor;
	}
	private void stopFalling() {
		if (!playerFalling) return;
		playerFalling = false;
		Game.map.getPlayer().setBackground(Game.map.getPlayer().getWalkDrawable());
		// Добавляем очко если приземлились на новый блок
		Block block = Game.map.findBlock(Game.map.getPlayer().getLeft(), Game.map.getPlayer().getBottom() + 1);
		if (block == null) block = Game.map.findBlock(Game.map.getPlayer().getRight(), Game.map.getPlayer().getBottom() + 1);
		if (block != null && block != lastWalkedBlock) {
			lastWalkedBlock = block;
			listener.groundedOnNewBlock();
		}
		Game.sounds.playGrounded();
	}
	private void updatePlayerSize() {
		AnimatedBitmapDrawable playerBackground = (AnimatedBitmapDrawable) Game.map.getPlayer().getBackground();
		Game.map.getPlayer().setSize(playerBackground.getCurrentBitmap().getWidth(), playerBackground.getCurrentBitmap().getHeight());
	}
}
