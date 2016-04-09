/**
 * 
 */
package Model;

import java.awt.Color;
import java.awt.Graphics;

import Model.Upgrade.FallingUpgrade;

/**
 * Class for object to shoot at with the ball. When health == 0, it gets
 * destroyed and may release the upgrade.
 * 
 * @author Antek
 *
 */
public class Brick extends StillObject
{
	private int health;

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public Brick(Color color, int x, int y, int width, int height, int health)
	{

		super(color, x, y, width, height, true);
		setHealth(health);
	}

	@Override
	public boolean gotHit(GameObject go)
	{
		if (go instanceof FallingUpgrade)
			return false;
		setHealth(getHealth() - 1);
		if (getHealth() == 0)
			return true;
		else
		{
			setColor(getColor().darker());
			return false;
		}

	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
	}
}
