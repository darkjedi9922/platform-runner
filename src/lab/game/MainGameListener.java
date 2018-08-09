package lab.game;

public interface MainGameListener {
	void groundedOnNewBlock();
	void gameStarted();
	void gameInitialized();
	void gameFailed();
	void setLevel();
	void startedJumping();
}
