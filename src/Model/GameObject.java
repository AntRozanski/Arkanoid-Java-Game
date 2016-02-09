/** 
 * 
 */
package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Main class of the game. Every object inherits from this class. Contains basic
 * fields, which are required to properly draw and calculate place of objects
 * collision, e.g. x and y coordintes, width, height, bounds. x and y indicate
 * the upper left corner of object.
 * 
 * @author Antek
 *
 */
public abstract class GameObject implements Serializable
{

	private Color color;
	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	
	public void setCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * @return the full bounds of object.
	 */
	public Rectangle2D getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}

	/**
	 * @return a rectangle with a width of one, which pose as a left edge of the
	 *         object.
	 */
	public Rectangle2D getLeftBound()
	{
		return new Rectangle(x, y, 1, height);
	}

	/**
	 * @return a rectangle with a width of one, which pose as a right edge of
	 *         the object.
	 */
	public Rectangle2D getRightBound()
	{
		return new Rectangle(x + width - 1, y, 1, height);
	}

	/**
	 * @return a rectangle with a hight of one, which pose as a top edge of the
	 *         object.
	 */
	public Rectangle2D getUpperBound()
	{
		return new Rectangle(x, y, width, 1);
	}

	/**
	 * @return a rectangle with a hight of one, which pose as a bottom edge of
	 *         the object.
	 */
	public Rectangle2D getLowerBound()
	{

		return new Rectangle(x, y + height - 1, width, 1);
	}

	/**
	 * Constructor for GameObjects.
	 * 
	 * @param color
	 *            to set
	 * @param x
	 *            to set
	 * @param y
	 *            to set
	 * @param width
	 *            to set
	 * @param height
	 *            to set
	 */
	public GameObject(Color color, int x, int y, int width, int height)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	/**
	 * Provide information how that Gameobject should be drawn.
	 */
	public abstract void draw(Graphics g);
}
