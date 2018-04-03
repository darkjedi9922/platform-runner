package lab.game;

import android.content.Context;
import android.graphics.BitmapFactory;

public class GreenLevel extends Level {
	
	public GreenLevel(Context c) {
		super(BitmapFactory.decodeResource(c.getResources(), R.drawable.green_background), 
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_start_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_platform),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_player_stand),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_player_walk1),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_player_walk2),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_player_jump),
				BitmapFactory.decodeResource(c.getResources(), R.drawable.green_player_fall)
		);
	}
}
