package Model.Upgrade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import Model.GameObject;
import Model.MovableObject;

/**
 * Class represents an object fired from the Racket when player collects
 * MissilesUpgrade and activate it (implicitly by pressing space). When missile
 * hits the brick, 'explosion' damages also bricks around it. After hit, area
 * covered by explosion stays visible for some amount of Model's update()
 * function cycles. *
 * 
 * @author Antek
 */
public class Missile extends MovableObject
{

	private boolean explosion;
	private int counter;

	public boolean isExplosion()
	{
		return explosion;
	}

	public void setExplosion(boolean explosion)
	{
		this.explosion = explosion;
	}

	public int getCounter()
	{
		return counter;
	}

	public void setCounter(int counter)
	{
		this.counter = counter;
	}

	public Missile(Color color, int x, int y, int width, int height, int directionX, int directionY, double ratio,
			double velocity)
	{
		super(color, x, y, width, height, directionX, directionY, ratio, velocity);
		setExplosion(false);
		setCounter(0);
	}

	@Override
	public void processCollision(GameObject go)
	{
		if (!isExplosion()) // after first collision 'explosion' is created
		{
			setX(getX() - 45);
			setY(getY() - 40);
			setWidth(110);
			setHeight(60);
			setExplosion(true);
		}

	}

	@Override
	public Rectangle2D getBounds()
	{
		if (getCounter() > 0) // to ensure that bricks around target will be
								// harmed only once by 'explosion'
			return new Rectangle(0, 0, 0, 0);
		else
			return super.getBounds();
	}

	@Override
	public void move()
	{
		if (!isExplosion())
		{
			if (isMoving())
			{
				setX_pos(getX_pos() + (getX_speed() * getDirectionX()));
				setX((int) Math.round(getX_pos()));
				setY_pos((getY_pos() + (getY_speed() * getDirectionY())));
				setY((int) Math.round(getY_pos()));

			}
		}
		else
		{
			if (getCounter() == 25)
				setDead(true);
			else
				setCounter(getCounter() + 1);
		}

	}

	@Override
	public void draw(Graphics g)
	{
		if (!isExplosion())
		{
			g.setColor(getColor());
			g.fillPolygon(new int[] { getX(), getX() + (getWidth()) / 2, getX() + getWidth() },
					new int[] { getY() + getHeight(), getY(), getY() + getHeight() }, 3);
		}
		else
		{
			g.setColor(Color.red);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}

}
