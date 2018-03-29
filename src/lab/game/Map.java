package lab.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class Map {

	private View parent;
	private List<Block> blocks = new LinkedList<Block>();
	private Random rand = new Random(1);
	int lastBlockRight = 0;
	Block blockToDelete = null;
	Bitmap platform;
	Bitmap startPlatform;
	MovingBackground background;
	TripleBitmapBackground blockBackground;
	Rect rect;
	
	public Map(View parent) {
		this.parent = parent;
	}
	public View getView() {
		return parent;
	}
	public Rect getRect() {
		return rect;
	}
	public void onStart() {
		rect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());
		Bitmap bBitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.background_blue);
		background = new MovingBackground(bBitmap, 1);
		background.setBitmapSize(bBitmap.getWidth() * rect.height() / bBitmap.getHeight(), rect.height());
		Bitmap start = BitmapFactory.decodeResource(parent.getResources(), R.drawable.blue_platform_start);
		Bitmap center = BitmapFactory.decodeResource(parent.getResources(), R.drawable.blue_platform_center);
		Bitmap end = BitmapFactory.decodeResource(parent.getResources(), R.drawable.blue_platform_end);
		blockBackground = new TripleBitmapBackground(start, center, end);
		
		generateStartBlock();
		generateBlock();
	}
	public void onRestart() {
		blocks.clear();
		lastBlockRight = 0;
		rand.setSeed(1);
		generateStartBlock();
		generateBlock();
	}
	public void draw(Canvas canvas) {
		background.draw(canvas, getRect());
		for (Block block : blocks) block.draw(canvas);
	}
	public void generateStartBlock() {
		int startWidth = blockBackground.getStartBitmap().getWidth();
		int centerWidth = blockBackground.getCenterBitmap().getWidth();
		int endWidth = blockBackground.getEndBitmap().getWidth();
		int height = blockBackground.getCenterBitmap().getHeight();
		Block block = new Block(blockBackground, startWidth + centerWidth * 25 + endWidth, height);
		block.setGamePosition(200, rect.height() / 2);
		blocks.add(block);
		lastBlockRight = block.getGameRect().rect.right;
	}
	public void generateBlock() {
		int startWidth = blockBackground.getStartBitmap().getWidth();
		int centerWidth = blockBackground.getCenterBitmap().getWidth();
		int endWidth = blockBackground.getEndBitmap().getWidth();
		int height = blockBackground.getCenterBitmap().getHeight();
		Block block = new Block(blockBackground, startWidth + centerWidth * 4 + endWidth, height);
		try {
			int x = rand.nextInt(1) + lastBlockRight + 200;
			int y = rand.nextInt(rect.height() - block.getWidth() * 2) + block.getWidth() * 2;
			block.setGamePosition(x, y);
			blocks.add(block);
			lastBlockRight = block.getGameRect().rect.right;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	public void checkGeneratingBlock() {
		if (lastBlockRight < rect.width()) {
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
