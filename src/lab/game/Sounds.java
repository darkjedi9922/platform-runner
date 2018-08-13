package lab.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds 
{
	private SoundPool sounds;
	private boolean enabled = true;
	
	private int clickSoundId;
	private int groundingSoundId;
	
	private List<Integer> fallSoundIds = new LinkedList<Integer>();
	private Iterator<Integer> fallSoundIt = null;
	
	public Sounds(Context context)
	{
		sounds = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		clickSoundId = sounds.load(context, R.raw.click, 1);
		groundingSoundId = sounds.load(context, R.raw.grounding, 1);
		fallSoundIds.add(new Integer(sounds.load(context, R.raw.fall1, 1)));
		fallSoundIds.add(new Integer(sounds.load(context, R.raw.fall4, 1)));
		fallSoundIds.add(new Integer(sounds.load(context, R.raw.fall2, 1)));
		fallSoundIds.add(new Integer(sounds.load(context, R.raw.fall3, 1)));
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
		if (enabled) {
			if (fallSoundIt == null || !fallSoundIt.hasNext()) fallSoundIt = fallSoundIds.iterator();
			sounds.play(fallSoundIt.next().intValue(), 1, 1, 0, 0, 1);
		}
	}
	
	public void playClick() 
	{
		if (enabled) sounds.play(clickSoundId, 1, 1, 0, 0, 1);
	}
}
