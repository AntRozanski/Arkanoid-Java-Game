package Model.Upgrade;

import java.awt.Color;
import java.awt.Graphics;

import Model.GameObject;
import Model.MovableObject;
import Model.Racket;
import Utils.Constants;

/**
 * This class represents those rectangles fallinf from destroed brick. If one of
 * them is picked, player is rewaded with some upgrade!
 * 
 * @author Antek
 *
 */
public class FallingUpgrade extends MovableObject
{
	private String name;
	private Upgrade upgrade;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public Upgrade getUpgrade()
	{
		return upgrade;
	}

	public void setUpgrade(Upgrade upgrade)
	{
		this.upgrade = upgrade;
	}

	/**
	 * Constructor for falling upgrades.
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param directionX
	 * @param directionY
	 * @param ratio
	 * @param velocity
	 * @param name
	 */
	public FallingUpgrade(Color color, int x, int y, int width, int height, int directionX, int directionY,
			double ratio, double velocity, Upgrade upgr)
	{
		super(color, x, y, width, height, directionX, directionY, ratio, velocity);
		setUpgrade(upgr);

	}

	@Override
	public void processCollision(GameObject go)
	{
		if (go instanceof Racket)
			setDead(true);
	}

	public boolean toDelete()
	{
		if (super.toDelete())
			return true;
		if (getY() > Constants.STANDARD_ARENA_HEIGHT)
			return true;
		else
			return false;

	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
