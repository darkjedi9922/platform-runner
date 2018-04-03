package lab.game;

import android.graphics.Bitmap;

public abstract class Level {
	
	private Bitmap gameBckgBitmap;
	private Bitmap startPlatformBitmap;
	private Bitmap platformBitmap;
	private Bitmap playerStandBitmap;
	private Bitmap playerWalk1Bitmap;
	private Bitmap playerWalk2Bitmap;
	private Bitmap playerJumpBitmap;
	private Bitmap playerFallBitmap;
	
	public Level(Bitmap gameBckg,
			Bitmap startPlatform,
			Bitmap platform,
			Bitmap playerStand,
			Bitmap playerWalk1,
			Bitmap playerWalk2,
			Bitmap playerJump,
			Bitmap playerFall) 
	{
		gameBckgBitmap = gameBckg;
		startPlatformBitmap = startPlatform;
		platformBitmap = platform;
		playerStandBitmap = playerStand;
		playerWalk1Bitmap = playerWalk1;
		playerWalk2Bitmap = playerWalk2;
		playerJumpBitmap = playerJump;
		playerFallBitmap = playerFall;
	}
	
	public Bitmap getGameBackground() {
		return gameBckgBitmap;
	}
	public Bitmap getStartPlatformBitmap() {
		return startPlatformBitmap;
	}
	public Bitmap getPlatformBitmap() {
		return platformBitmap;
	}
	public Bitmap getPlayerStandBitmap() {
		return playerStandBitmap;
	}
	public Bitmap getPlayerWalk1Bitmap() {
		return playerWalk1Bitmap;
	}
	public Bitmap getPlayerWalk2Bitmap() {
		return playerWalk2Bitmap;
	}
	public Bitmap getPlayerJumpBitmap() {
		return playerJumpBitmap;
	}
	public Bitmap getPlayerFallBitmap() {
		return playerFallBitmap;
	}
}
