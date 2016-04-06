package Model.Upgrade;

/**
 * Class that represents Bigger racket upgrade. When player picks up Falling
 * Upgrade containing it, it starts to measure time.
 *
 *
 * @author Antek
 *
 */
public class TemporaryUpgrade extends Upgrade
{
	private int duration; // how long effect remains active (in miliseconds)
	private long upgrStartTime; // when upgr starts
	private long pauseStartTime; // when pause starts
	private long pauseTime; // amount of time spent in pause mode

	public TemporaryUpgrade(int duration, String name)
	{
		super(name);
		setDuration(duration);
		setPauseTime(0);
		setPauseStartTime(0);
		setUpgrStartTime(0);
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int time)
	{
		this.duration = time;
	}

	public long getUpgrStartTime()
	{
		return upgrStartTime;
	}

	public void setUpgrStartTime(long startTime)
	{
		this.upgrStartTime = startTime;
	}

	public long getPauseStartTime()
	{
		return pauseStartTime;
	}

	public void setPauseStartTime(long pauseStartTime)
	{
		this.pauseStartTime = pauseStartTime;
	}

	public long getPauseTime()
	{
		return pauseTime;
	}

	public void setPauseTime(long pauseTime)
	{
		this.pauseTime = pauseTime;
	}


	public boolean isOver()
	{

		return (System.currentTimeMillis() - getUpgrStartTime() - getPauseTime() > duration);
	}

	
	public void start()
	{
		setUpgrStartTime(System.currentTimeMillis());
	}

	public void startPause()
	{
		setPauseStartTime(System.currentTimeMillis());
	}

	public void stopPause()
	{
		setPauseTime(getPauseTime() + System.currentTimeMillis() - getPauseStartTime());
	}
}
