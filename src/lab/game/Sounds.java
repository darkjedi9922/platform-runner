package lab.game;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds 
{
	private SoundPool sounds;
	private boolean enabled = true;
	
	private int clickSoundId;
	private int groundingSoundId;
	private int fallSoundId;
	
	public Sounds(Context context)
	{
		sounds = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		clickSoundId = sounds.load(context, R.raw.click, 1);
		groundingSoundId = sounds.load(context, R.raw.grounding, 1);
		fallSoundId = sounds.load(context, R.raw.fall, 1);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void playGrounded()
	{
		if (enabled) sounds.play(groundingSoundId, 1, 1, 0, 0, 1);
	}
	
	public void playFall()
	{
		if (enabled) sounds.play(fallSoundId, 1, 1, 0, 0, 1);
	}
	
	public void playClick() 
	{
		if (enabled) sounds.play(clickSoundId, 1, 1, 0, 0, 1);
	}
}
