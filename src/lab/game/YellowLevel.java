package lab.game;

import android.content.Context;
import android.graphics.BitmapFactory;

public class YellowLevel extends Level {
	
	public YellowLevel(Context c) {
		super(BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_background), 
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_start_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_player_stand),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_player_walk1),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_player_walk2),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_player_jump),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.yellow_player_fall)
		);
	}
}
