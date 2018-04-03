package lab.game;

import android.content.Context;
import android.graphics.BitmapFactory;

public class BlueLevel extends Level {
	
	public BlueLevel(Context c) {
		super(BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_background), 
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_start_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_player_stand),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_player_walk1),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_player_walk2),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_player_jump),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.blue_player_fall)
		);
	}
}
