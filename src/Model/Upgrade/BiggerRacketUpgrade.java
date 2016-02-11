package Model.Upgrade;

import java.util.Date;

/**
 * Class that represents Bigger racket upgrade. When player picks up Falling
 * Upgrade containing it, it starts to measure time.
 * 
 * 
 * @author Antek
 *
 */
public class BiggerRacketUpgrade extends Upgrade
{
	private int duration;// how long the racket is wider
	private long currentTime;

	public BiggerRacketUpgrade(int duration, String name)
	{
		setName(name);
		setDuration(duration);
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int time)
	{
		this.duration = time;
	}

	public long getCurrentTime()
	{
		return currentTime;
	}

	public void setCurrentTime(long currentTime)
	{
		this.currentTime = currentTime;
	}

	@Override
	public boolean isOver()
	{
		return (System.currentTimeMillis() - currentTime > duration * 1000);
	}

	@Override
	public void start()
	{
		setCurrentTime(System.currentTimeMillis());
	}
}
