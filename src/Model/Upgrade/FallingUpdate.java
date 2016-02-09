package Model.Upgrade;

import java.awt.Color;
import java.awt.Graphics;

import Model.GameObject;
import Model.MovableObject;
import Model.Racket;
import Utils.Constants;

public class FallingUpdate extends MovableObject
{
	private String name;

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

	/**
	 * Constructor for falling upgrades.
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
	public FallingUpdate(Color color, int x, int y, int width, int height, int directionX, int directionY, double ratio,
			double velocity, String name)
	{
		super(color, x, y, width, height, directionX, directionY, ratio, velocity);
		this.name = name;

	}

	@Override
	public void processCollision(GameObject go)
	{
		if (go instanceof Racket)
		{
			System.out.println("Got upgrade! " + name);
			setDead(true);

		}

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
