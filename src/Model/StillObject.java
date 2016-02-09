package Model;

import java.awt.Color;

/**
 *
 * Class for every obejct at the game, that can't move - e.g. bricks, walls.
 *
 * @author Antek
 *
 */
public abstract class StillObject extends GameObject
{
	boolean isRemovable;

	/**
	 * @return the isRemovable
	 */
	public boolean isRemovable()
	{
		return isRemovable;
	}

	/**
	 * @param isRemovable
	 *            the isRemovable to set
	 */
	public void setRemovable(boolean isRemovable)
	{
		this.isRemovable = isRemovable;
	}

	@Override
	public int hashCode()
	{

		return 5;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o != null && o instanceof StillObject)
		{
			return (getX() == ((StillObject) o).getX() && getY() == ((StillObject) o).getY());

		}
		return false;
	}

	public StillObject(Color color, int x, int y, int width, int height, boolean isRemovable)
	{
		super(color, x, y, width, height);

		this.isRemovable = isRemovable;

	}

	/**
	 *
	 * Function called when stillOject was hit by something. Should be
	 * overwritten when inheriting object actually do something when it is hit
	 * or it is removable object. Depending on object, which hit it, reactions
	 * may vary.Should return true, when life of stillObject is reduced to zero
	 * and stillObject should be destroyed.
	 *
	 * @param go
	 *            - object which hit stillObject
	 * @return by default always false. Should be overwritten by inheriting
	 *         object so it returns true if object dies.
	 */
	public boolean gotHit(GameObject go)
	{
		return false;
	}

}
