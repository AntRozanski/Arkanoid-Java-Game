package Model;

import java.io.Serializable;

/**
 * Class for ResultsModel use. Represents all data that will be show in
 * HallOfFame window.
 *
 *
 * @author Antek
 *
 */
public class PlayerData implements Serializable, Comparable
{
	private String name;
	private int points;
	private String date;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public PlayerData(String name, int points, String date)
	{
		this.setName(name);
		this.setPoints(points);
		this.setDate(date);

	}

	@Override
	public String toString()
	{
		return String.format("%-25s %-15d %-10s %n", getName(), getPoints(), getDate());
	}


	@Override
	public int compareTo(Object pd)
	{
		PlayerData playerData = (PlayerData) pd;
		if (this.getPoints() > playerData.getPoints())
			return -1;
		else if (this.getPoints() < playerData.getPoints())
			return 1;
		else
			return 0;
	}
}
