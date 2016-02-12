/**
 *
 */
package Model;

import java.awt.Color;

/**
 * Class for gameObjects that can move, e.g. balls, bullets.
 *
 * @author Antek
 *
 */
public abstract class MovableObject extends GameObject
{
	private double velocity;
	/**
	 * this field, comprising in the range 0 - 1, provides information about
	 * part of velocity vector laying on x-axis.
	 */
	private double ratio;

	/**
	 * the exact x position of movableObject. Every step, this field is rounded
	 * to nearest int value, to draw the object.
	 */
	private double x_pos;
	/**
	 * the exact y position of movableObject. Every step, this field is rounded
	 * to nearest int value, to draw the object.
	 */
	private double y_pos;
	/**
	 * speed on x - axis.
	 */
	private double x_speed;
	/**
	 * speed on y - axis.
	 */
	private double y_speed;
	/**
	 * speed on x - axis. 1 is right, -1 - left.
	 */
	private int directionX;
	/**
	 * speed on y - axis. 1 is up, -1 - bottom.
	 */
	private int directionY;
	private boolean isMoving;
	private boolean isDead;

	/**
	 * @return the velocity
	 */
	public double getVelocity()
	{
		return velocity;
	}

	/**
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(double velocity)
	{
		this.velocity = velocity;
	}

	/**
	 * @return the ratio
	 */
	public double getRatio()
	{
		return ratio;
	}

	/**
	 * @param ratio
	 *            the ratio to set
	 */
	public void setRatio(double ratio)
	{
		this.ratio = ratio;
	}

	/**
	 * @return the x_pos
	 */
	public double getX_pos()
	{
		return x_pos;
	}

	/**
	 * @param x_pos
	 *            the x_pos to set
	 */
	public void setX_pos(double x_pos)
	{
		this.x_pos = x_pos;
	}

	/**
	 * @return the y_pos
	 */
	public double getY_pos()
	{
		return y_pos;
	}

	/**
	 * @param y_pos
	 *            the y_pos to set
	 */
	public void setY_pos(double y_pos)
	{
		this.y_pos = y_pos;
	}

	/**
	 * @return the x_speed
	 */
	public double getX_speed()
	{
		return x_speed;
	}

	/**
	 * @param x_speed
	 *            the x_speed to set
	 */
	public void setX_speed(double x_speed)
	{
		this.x_speed = x_speed;
	}

	/**
	 * @return the y_speed
	 */
	public double getY_speed()
	{
		return y_speed;
	}

	/**
	 * @param y_speed
	 *            the y_speed to set
	 */
	public void setY_speed(double y_speed)
	{
		this.y_speed = y_speed;
	}

	/**
	 * @return the directionX
	 */
	public int getDirectionX()
	{
		return directionX;
	}

	/**
	 * @param directionX
	 *            the directionX to set
	 */
	public void setDirectionX(int directionX)
	{
		this.directionX = directionX;
	}

	/**
	 * @return the directionY
	 */
	public int getDirectionY()
	{
		return directionY;
	}

	/**
	 * @param directionY
	 *            the directionY to set
	 */
	public void setDirectionY(int directionY)
	{
		this.directionY = directionY;
	}

	/**
	 * @return the isMoving
	 */
	public boolean isMoving()
	{
		return isMoving;
	}

	/**
	 * @param isMoving
	 *            the isMoving to set
	 */
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}

	/**
	 * @return the isDead
	 */
	public boolean isDead()
	{
		return isDead;
	}

	/**
	 * @param isDead
	 *            the isDead to set
	 */
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}

	/**
	 * Function called to check if movableObject should be destroyed.
	 *
	 * @return true, if movableObject should be deleted.
	 */
	public boolean toDelete()
	{
		return isDead();
	}

	/**
	 * Sets direction on x-axis, depending on which side object collide with go.
	 *
	 * @param go
	 *            - gameObject hit by this object
	 */
	public void changeDirectionX(GameObject go)
	{
		if (leftCollide(go))
			setDirectionX(-1);
		else if (rightCollide(go))
			setDirectionX(1);
		else
			System.out.println("Error! Cant change direction on X axis!");

	}

	/**
	 * Sets direction on y-axis, depending on which side object collide with go.
	 *
	 * @param go
	 *            - gameObject hit by this object
	 */
	public void changeDirectionY(GameObject go)
	{
		if (upperCollide(go))
			setDirectionY(-1);
		else if (lowerCollide(go))
			setDirectionY(1);
		else
			System.out.println("Error! Cant change direction on Y axis!");

	}

	/**
	 * inverses directionY
	 */
	public void changeDirectionY()
	{
		setDirectionY(getDirectionY() * (-1));

	}

	/**
	 * inverses directionX
	 */
	public void changeDirectionX()
	{
		setDirectionY(getDirectionX() * (-1));

	}

	public MovableObject(Color color, int x, int y, int width, int height, int directionX, int directionY, double ratio,
			double velocity)
	{
		super(color, x, y, width, height);

		this.directionX = directionX;
		this.directionY = directionY;
		this.velocity = velocity;
		calculateSpeed(ratio);
		this.x_pos = x;
		this.y_pos = y;
		this.isMoving = false;// TODO: for balls only?
		this.setDead(false);

	}

	/**
	 * Implementation of this abstract function specifies what movableObject do
	 * after collision with GameObject go.
	 *
	 * @param go
	 *            - gameObject, which collide with movableObject
	 */
	public abstract void processCollision(GameObject go);

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with go
	 */
	public boolean collide(GameObject go)
	{
		return getBounds().intersects(go.getBounds());
	}

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with top edge of go
	 */
	public boolean upperCollide(GameObject go)
	{
		return (getBounds().intersects(go.getUpperBound()));
	}

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with bottom edge of go
	 */
	public boolean lowerCollide(GameObject go)
	{
		return (getBounds().intersects(go.getLowerBound()));
	}

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with right edge of go
	 */
	public boolean rightCollide(GameObject go)
	{
		return (getBounds().intersects(go.getRightBound()));
	}

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with left edge of go
	 */
	public boolean leftCollide(GameObject go)
	{
		return (getBounds().intersects(go.getLeftBound()));
	}

	/**
	 * @param go
	 *            - gameObject
	 * @return result of the test for intersection with left or right edge go
	 */
	public boolean sideCollide(GameObject go)
	{
		return ((getBounds().intersects(go.getRightBound())) || (getBounds().intersects(go.getLeftBound())));
	}

	@Override
	public void setCoordinates(int x, int y)
	{
		super.setCoordinates(x, y);
		this.x_pos = x;
		this.y_pos = y;

	}

	/**
	 * Moves movableObject. Sets X and Y coordinates using current position,
	 * x-axis and y-axis direction and speed.
	 */
	public void move()
	{
		if (isMoving)
		{
			setX_pos(getX_pos() + (getX_speed() * getDirectionX()));
			setX((int) Math.round(getX_pos()));
			setY_pos((getY_pos() + (getY_speed() * getDirectionY())));
			setY((int) Math.round(getY_pos()));

		}

	}

	/**
	 * Function calculate x_speed and y_speed using given ratio.
	 *
	 * @param ratio
	 */
	public void calculateSpeed(double ratio)
	{

		setRatio(Math.abs(ratio));

		setX_speed(Math.sqrt(getRatio()) * getVelocity());
		setY_speed(Math.sqrt(1 - getRatio()) * getVelocity());

	}

}
