package Model;

import java.awt.Color;
import java.awt.Graphics;

import Utils.Constants;

/**
 * 
 * Class for racket - the object that is used to bounce the ball.
 * 
 * @author Antek
 *
 */
public class Racket extends GameObject
{
	private int x_speed;

	/**
	 * @return the x_speed
	 */
	public int getX_speed()
	{
		return x_speed;
	}

	/**
	 * @param x_speed
	 *            the x_speed to set
	 */
	public void setX_speed(int x_speed)
	{
		this.x_speed = x_speed;
	}

	public Racket(Color color, int x, int y, int width, int height, int x_speed)
	{
		super(color, x, y, width, height);
		this.x_speed = x_speed;
	}

	/**
	 * Moves Racket right or left, accordingly to dir parameter. If after move
	 * racket would protrude from the game board, function sets its coordinates to the
	 * maximum possible right/left position.
	 * 
	 * @param dir
	 *            - direction to move.
	 */
	public void move(int dir)
	{

		setX(getX() + dir * getX_speed());

		if (getX() > Constants.STANDARD_ARENA_WIDTH - Constants.STANDARD_WALL_WIDTH - getWidth())
			setX(Constants.STANDARD_ARENA_WIDTH - Constants.STANDARD_WALL_WIDTH - getWidth());

		if (getX() < 10)
			setX(10);

	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
