package lab.game;

import android.content.Context;
import android.graphics.BitmapFactory;

public class PinkLevel extends Level {
	
	public PinkLevel(Context c) {
		super(BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_background), 
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_start_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_player_stand),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_player_walk1),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_player_walk2),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_player_jump),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.pink_player_fall)
		);
	}
}
