package Model;

/**
 * 
 * Class that represent player. It stores health (number of balls that can be
 * destroyed before game is over) and points.
 * 
 * @author Antek
 *
 */
public class Player
{
	private int health;
	private int points;

	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return health;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health)
	{
		this.health = health;
	}

	/**
	 * @return the points
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	public Player(int h)
	{
		this.health = h;
		this.points = 0;
	}

	/**
	 * Decreases health of Player by one. Checks if player is dead.
	 * 
	 * @return true, if helath == 0
	 */
	public boolean decreaseHP()
	{
		setHealth(getHealth() - 1);
		if (getHealth() == 0)
			return true;
		return false;
	}

	/**
	 * Add given points to the point pool.
	 * 
	 * @param num
	 *            - points to add
	 */
	public void addPoints(int num)
	{
		setPoints(getPoints() + num);
	}
}