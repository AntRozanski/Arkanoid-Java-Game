package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;

import Utils.Constants;

/**
 *
 * Class for balls - object that bounces off the racket and destroys bricks. It
 * has additional field - radius to make calculations more clear. Boolean filed
 * 'rampage' indicates if the ball in under influence of the 'RampageUpgrade'
 * (in that mode ball don't bounces off the bricks).
 *
 * @author Antek
 *
 */
public class Ball extends MovableObject
{

	private int radius;
	private boolean rampage;

	/**
	 * @return the radius
	 */
	public int getRadius()
	{
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	public boolean isRampage()
	{
		return rampage;
	}

	public void setRampage(boolean rampage)
	{
		this.rampage = rampage;
	}

	public Ball(Color color, int x, int y, int width, int height, int radius, int directionX, int directionY,
			double ratio, double velocity)
	{
		super(color, x, y, width, height, directionX, directionY, ratio, velocity);
		this.setRadius(radius);
		this.setRampage(false);
	}

	@Override
	public Rectangle2D getBounds()
	{
		return new Rectangle((int) (getX() + 0.1 * getX_speed() * getDirectionX()),
				(int) (getY() + 0.1 * getY_speed() * getDirectionY()), getWidth(), getHeight());
	}

	/**
	 * Function for balls only. Changes X and Y directions and speeds of ball
	 * depending on the place when racket got hit. The closer to the middle of
	 * racket ball has hit, the bigger speed o y_axis is. Falling on the right
	 * half of the racket will result with ball bouncing to the right, falling
	 * on the left side will result with ball bouncing to the left.
	 *
	 * @param racket
	 */
	public void bounce(Racket racket)
	{
		Rectangle2D racketBounds = racket.getBounds();

		double punkt_uderz = getX() + (getWidth()) / 2;
		double punkt_œr = racketBounds.getX() + racketBounds.getWidth() / 2;
		double roznica = punkt_uderz - punkt_œr;
		double ratio = roznica / (racketBounds.getWidth() / 2 + 2 * getRadius());
		if (ratio < 0)
			setDirectionX(-1);
		else
			setDirectionX(1);
		calculateSpeed(ratio);

	}

	public void processCollision(GameObject go)
	{
		if (isRampage() && !(go instanceof UnmovableBrick || go.getClass() == Racket.class))
			return;

		if (sideCollide(go)) // if collison is at side of GameObject
		{
			if (upperCollide(go) || lowerCollide(go)) // and also(!)on
														// top or bottom
			{
				if (moreAtSide(go)) // if bigger part of ball is on the
									// side
				{

					changeDirectionX(go);

				}
				else// if bigger part of ball is on the top / bottom
				{
					if (go.getClass() == Racket.class)
						bounce((Racket) (go));

					changeDirectionY(go);

				}

			}
			else// only on the side
			{
				changeDirectionX(go);

			}
		}
		else// not on the side
		{
			if (go instanceof Racket)
				bounce((Racket) (go));

			changeDirectionY(go);
		}

	}

	/**
	 * Function called when ball collide at the same time with 2 edges of given
	 * Gameobject(most probably brick): on the side and top/bottom edge of the
	 * GameObject. It determinates whether bigger part of ball is at side or
	 * not.
	 *
	 * @param go
	 *            - gameObject that was hit by the ball at more than one side
	 * @return true, if ball is more at the side of gameObject than above/below
	 *         it.
	 */
	public boolean moreAtSide(GameObject go)
	{
		Point stillObjectPoint;
		Point ballCenterPoint = new Point(getX() + getRadius(), getY() + getRadius());
		if (leftCollide(go))
			stillObjectPoint =
					new Point((go.getX() + (go.getHeight() / 2)), (go.getY() + (go.getHeight() / 2)));
		else
			stillObjectPoint = new Point((go.getX() + go.getWidth() - (go.getHeight() / 2)),
					(go.getY() + (go.getHeight() / 2)));
		if (Math.abs(ballCenterPoint.x - stillObjectPoint.x) < Math
				.abs(ballCenterPoint.y - stillObjectPoint.y))
			return false;
		else
			return true;

	}

	@Override
	public boolean toDelete()
	{
		if (super.toDelete() || getY() > Constants.STANDARD_ARENA_HEIGHT)
			return true;
		else
			return false;

	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName() == "AdditionalBallsUpgrade")
		{
			if ((Boolean) evt.getNewValue() == false)
				setDead(true);
		}
		if (evt.getPropertyName() == "RampageUpgrade")
		{

			if ((Boolean) evt.getNewValue() == true)
			{
				setRampage(true);
				setColor(Color.red);
			}
			else
			{
				setRampage(false);
				int b = (int) (Math.random() * 145);
				int r = (int) (Math.random() * 145);
				int g = (int) (Math.random() * 145);
				setColor(new Color(b, r, g));
			}

		}
	}

}
