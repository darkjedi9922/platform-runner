package lab.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class Map {

	private View parent;
	private Block startBlock = null;
	private List<Block> blocks = new LinkedList<Block>();
	private Random rand = new Random(1);
	int lastBlockRight = 0;
	int startBlockRight = 0;
	Level level = null;
	
	MovingBackground background;
	private Player player = null;
	private List<Level> levels = new LinkedList<Level>();
	private Iterator<Level> levelIterator = null;
	
	public Map(View parent) {
		this.parent = parent;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setLevel(Level l) 
	{
		level = l;
		
		List<Bitmap> stand = new LinkedList<Bitmap>();
		stand.add(level.getPlayerStandBitmap());
		getPlayer().getStandDrawable().setBitmaps(stand);
		
		List<Bitmap> walk = new LinkedList<Bitmap>();
		walk.add(level.getPlayerWalk1Bitmap());
		walk.add(level.getPlayerWalk2Bitmap());
		getPlayer().getWalkDrawable().setBitmaps(walk);
		
		List<Bitmap> jump = new LinkedList<Bitmap>();
		jump.add(level.getPlayerJumpBitmap());
		getPlayer().getJumpDrawable().setBitmaps(jump);
		
		List<Bitmap> fall = new LinkedList<Bitmap>();
		fall.add(level.getPlayerFallBitmap());
		getPlayer().getFallDrawable().setBitmaps(fall);
	}
	public void addLevel(Level level)
	{
		levels.add(level);
	}
	public void nextLevel()
	{
		if (levelIterator == null || !levelIterator.hasNext()) levelIterator = levels.iterator();
		setLevel(levelIterator.next());
	}
	public View getView() {
		return parent;
	}
	public int getWidth() {
		return parent.getWidth();
	}
	public int getHeight() {
		return parent.getHeight();
	}
	public Rect getRect() {
		return new Rect(getLeft(), getTop(), getRight(), getBottom());
	}
	public int getLeft() {
		return 0;
	}
	public int getTop() {
		return 0;
	}
	public int getRight() {
		return getWidth() - 1;
	}
	public int getBottom() {
		return getHeight() - 1;
	}
	public void onStart() {
		Bitmap bBitmap = level.getGameBackground();
		background = new MovingBackground(bBitmap, 1);
		background.setBounds(getRect());
		background.setBitmapSize(bBitmap.getWidth() * getHeight() / bBitmap.getHeight(), getHeight());
		background.restart();
		blocks.clear();
		lastBlockRight = 0;
		rand.setSeed(1);
		generateStartBlock();
		generateBlocks();
	}
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		background.draw(canvas);
		if (startBlock != null) startBlock.draw(canvas);
		for (Block block : blocks) block.draw(canvas);
	}
	public void generateStartBlock() {
		startBlock = new Block(level.getStartPlatformBitmap());
		startBlock.setGamePosition(200, getHeight() / 2);
		startBlockRight = lastBlockRight = startBlock.getGameRect().rect.right;
	}
	public Block generateBlock() {
		Block block = new Block(level.getPlatformBitmap());
		try {
			Point coords = this.randBlockCoords(block);
			block.setGamePosition(coords.x, coords.y);
			blocks.add(block);
			lastBlockRight = block.getGameRect().rect.right;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return block;
	}
	private void generateBlocks() {
		while (generateBlock().getLeft() - startBlockRight <= this.getRight());
	}
	public void moveBlocks(int speed) {
		// стартовый блок
		if (startBlock != null) {
			startBlock.getGameRect().rect.offset(-speed, 0);
			if (startBlock.getGameRect().rect.right < 0) startBlock = null;
		}
		// остальные блоки
		for (Block block : blocks) {
			block.getGameRect().rect.offset(-speed, 0);
			if (block.getGameRect().rect.right < 0) {
				Point coords = this.randBlockCoords(block);
				block.setGamePosition(coords.x, coords.y);
				lastBlockRight = block.getRight();
			}
		}
		lastBlockRight -= speed;
	}
	private Point randBlockCoords(Block block) {
		int x = rand.nextInt(1) + lastBlockRight + 200;
		int y = rand.nextInt(getHeight() - block.getWidth() * 2) + block.getWidth() * 2;
		return new Point(x, y);
	}
	public int findFloor(Point from) {
		if (startBlock != null && startBlock.getLeft() <= from.x && startBlock.getRight() >= from.x && from.y <= startBlock.getTop())
			return startBlock.getTop();
		for (Block block : blocks) {
			if (block.getLeft() <= from.x && block.getRight() >= from.x && from.y <= block.getTop()) {
				return block.getTop();
			}
		}
		return -1;
	}
	public Block getStartBlock() {
		return startBlock;
	}
	public Block findBlock(int x, int y) {
		for (Block block : blocks) {
			if (block.getGameRect().rect.contains(x, y)) return block;
		}
		return null;
	}
}
