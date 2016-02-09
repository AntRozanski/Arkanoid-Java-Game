package Model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * Class for Bricks that can't be destroyed.
 * 
 * @author Antek
 *
 */
public class UnmovableBrick extends StillObject
{

	public UnmovableBrick(Color color, int x, int y, int width, int height)
	{
		super(color, x, y, width, height, false);
	}

	@Override
	public boolean gotHit(GameObject go)
	{
		return false;
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.red);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}

}
