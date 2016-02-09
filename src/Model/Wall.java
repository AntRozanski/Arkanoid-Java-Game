package Model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * Class for Wall -object that surround game board on the sides and on the top,
 * preventing ball from leaving game area.
 * 
 * @author Antek
 *
 */
public class Wall extends StillObject
{

	public Wall(Color color, int x, int y, int width, int height, boolean isRemovable)
	{
		super(color, x, y, width, height, isRemovable);

	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());

	}

}
