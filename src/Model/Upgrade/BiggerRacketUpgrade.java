package Model.Upgrade;

import java.util.Date;

public class BiggerRacketUpgrade extends Upgrade
{
	private int lenght;// how long the racket is wider
	private long currentTime;

	public BiggerRacketUpgrade(int lenght,String name)
	{
		setName(name);
		setLenght(lenght);
	}

	public int getLenght()
	{
		return lenght;
	}

	public void setLenght(int time)
	{
		this.lenght = time;
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
		if (System.currentTimeMillis() - currentTime > lenght * 1000)
			return true;
		else
			return false;
	}

	@Override
	public void start()
	{
		setCurrentTime(System.currentTimeMillis());
	}
}
