package Model.Upgrade;

import java.awt.Color;
import java.awt.Graphics;

import Model.GameObject;
import Model.MovableObject;

public class Missile extends MovableObject
{
	private int x_coor[];
	private int y_coor[];

	public int[] getX_coor()
	{
		return x_coor;
	}

	public void setX_coor(int x_coor[])
	{
		this.x_coor = x_coor;
	}

	public int[] getY_coor()
	{
		return y_coor;
	}

	public void setY_coor(int y_coor[])
	{
		this.y_coor = y_coor;
	}

	public Missile(Color color, int x, int y, int width, int height, int directionX, int directionY, double ratio,
			double velocity)
	{
		super(color, x, y, width, height, directionX, directionY, ratio, velocity);
		int x_co[] = { getX(), (getX() + getWidth()) / 2, getX() + getWidth() };
		int y_co[] = { getY() + getHeight(), getY(), getY() + getHeight() };
		setX_coor(x_co);
		setY_coor(y_co);
		System.out.println("im alive!");
	}

	@Override

	public void processCollision(GameObject go)
	{
		setDead(true);
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getWidth(), getHeight());
//		g.fillPolygon(getX_coor(), getY_coor(), 3);
	}

}
