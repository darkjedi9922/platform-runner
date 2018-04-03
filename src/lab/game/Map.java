package lab.game;

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
	private List<Block> blocks = new LinkedList<Block>();
	private Random rand = new Random(1);
	int lastBlockRight = 0;
	Block blockToDelete = null;
	Level level = null;
	MovingBackground background;
	
	public Map(View parent) {
		this.parent = parent;
	}
	public void setLevel(Level l) {
		level = l;
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
		generateBlock();
	}
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		background.draw(canvas);
		for (Block block : blocks) block.draw(canvas);
	}
	public void generateStartBlock() {
		Block block = new Block(level.getStartPlatformBitmap());
		block.setGamePosition(200, getHeight() / 2);
		blocks.add(block);
		lastBlockRight = block.getGameRect().rect.right;
	}
	public void generateBlock() {
		Block block = new Block(level.getPlatformBitmap());
		try {
			int x = rand.nextInt(1) + lastBlockRight + 200;
			int y = rand.nextInt(getHeight() - block.getWidth() * 2) + block.getWidth() * 2;
			block.setGamePosition(x, y);
			blocks.add(block);
			lastBlockRight = block.getGameRect().rect.right;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	public void checkGeneratingBlock() {
		if (lastBlockRight < getWidth()) {
			generateBlock();
		}
	}
	public void moveBlocks(int speed) {
		for (Block block : blocks) {
			block.getGameRect().rect.offset(-speed, 0);
			if (block.getGameRect().rect.right < 0 && blockToDelete != null) blockToDelete = block;
		}
		if (blockToDelete != null) {
			blocks.remove(blockToDelete);
			blockToDelete = null;
		}
		lastBlockRight -= speed;
	}
	public int findFloor(Point from) {
		for (Block block : blocks) {
			if (block.getLeft() <= from.x && block.getRight() >= from.x && from.y <= block.getTop()) {
				return block.getTop();
			}
		}
		return -1;
	}
	public Block getStartBlock() {
		return blocks.get(0);
	}
	public Block findBlock(int x, int y) {
		for (Block block : blocks) {
			if (block.getGameRect().rect.contains(x, y)) return block;
		}
		return null;
	}
}
