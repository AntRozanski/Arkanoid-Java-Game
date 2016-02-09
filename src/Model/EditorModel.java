package Model;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Utils.Constants;
import Utils.FileManager;

/**
 *
 * Class for all activities related to level editor. It stores all bricks for
 * currently chosen level in a list - editorObjects.
 *
 * @author Antek
 *
 */
public class EditorModel
{
	private int maxLevel;
	private int brickHealth;
	private GameFactory gameFatory;
	private ArrayList<StillObject> editorObjects = new ArrayList<StillObject>();
	// TODO: put together with EditorView's constants!
	int cols = 10;
	int rows = 25;
	int brickWidth = (int) ((Constants.STANDARD_ARENA_WIDTH - 2 * Constants.STANDARD_WALL_WIDTH) / cols);
	int brickHeight = (int) brickWidth / 5;

	/**
	 * Constructor for EditorModel. Requires gameFactory, which will be used to
	 * create bricks.
	 *
	 * @param gameFactory
	 */
	public EditorModel(GameFactory gameFactory)
	{
		setGameFatory(gameFactory);
		setMaxLevel(FileManager.readMaxLevel());
		setBrickHealth(1);
	}

	public ArrayList<StillObject> getEditorObjects()
	{
		return editorObjects;
	}

	public void setEditorObjects(ArrayList<StillObject> editorObjects)
	{
		this.editorObjects = editorObjects;
	}

	public GameFactory getGameFatory()
	{
		return gameFatory;
	}

	public void setGameFatory(GameFactory gameFatory)
	{
		this.gameFatory = gameFatory;
	}

	public int getMaxLevel()
	{
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel)
	{
		this.maxLevel = maxLevel;
	}

	public int getBrickHealth()
	{
		return brickHealth;
	}

	public void setBrickHealth(int brickHealth)
	{
		this.brickHealth = brickHealth;
	}

	/**
	 * Method to process mouse clicks. Its task is to decide whether user can
	 * create/delete Brick in place specified by MouseEvent me passed to it or
	 * not. If user clicked inside of editor board, funtion returs Point with x
	 * and y values corresponding x and y of Brick which would lay there.
	 *
	 * @param me
	 *            - MouseEvent with useful information
	 * @return Point, if user clicked inside editor area, null otherwise
	 */
	public Point processClick(MouseEvent me)
	{
		int d = Constants.STANDARD_WALL_WIDTH;
		int x_clicked = me.getX();
		int y_clicked = me.getY();
		if (x_clicked <= d || x_clicked >= cols * brickWidth + d || y_clicked <= d
				|| y_clicked >= rows * brickHeight + d)
			return null;
		int col = (x_clicked - d) / brickWidth;
		int row = (y_clicked - d) / brickHeight;
		int x = col * brickWidth + d;
		int y = row * brickHeight + d;

		return new Point(x, y);

	}

	/**
	 * Creates StillObject using passed coordinates (if it is not null). The
	 * number of brick's HPs (and its color) depends on actual value of field
	 * brickHealth. *
	 *
	 * @param coordinates
	 *            of new Object
	 * @return new StillObject, if passed Point is not null and null otherwise.
	 */
	public StillObject createStillObject(Point coordinates)
	{
		if (coordinates == null)
			return null;
		int x = coordinates.x;
		int y = coordinates.y;

		if (getBrickHealth() != 5)
			return getGameFatory().createBrick(x, y, brickWidth, brickHeight, getBrickHealth());
		else
			return getGameFatory().createUnmovableBrick(x, y, brickWidth, brickHeight);

	}

	/**
	 * Tries to create StillObject using passed MouseEvent as parameter. If it
	 * ends with success and there is no the same object in the editorObjects,
	 * this object is added to the editorObjects list.
	 *
	 * @param me
	 *            - MouseEvent to process
	 * @return true, if function managed to add StillObject to editorObjects,
	 *         false otherwise.
	 */
	public boolean addStillObject(MouseEvent me)
	{

		StillObject b = createStillObject(processClick(me));

		if (b != null && !getEditorObjects().contains(b))
		{
			getEditorObjects().add(b);
			return true;
		}
		return false;
	}

	/**
	 * Tries to create StillObject using passed MouseEvent as parameter. If it
	 * ends with success and there is such an object in the editorObjects, this
	 * object is removed from the editorObjects.
	 *
	 * @param me
	 *            - MouseEvent to process
	 * @return true, if function managed to remove StillObject from the
	 *         editorObjects, false otherwise.
	 */
	public boolean deleteStillObject(MouseEvent me)
	{
		StillObject b = createStillObject(processClick(me));
		if (b != null && getEditorObjects().contains(b))
		{
			getEditorObjects().remove(b);
			return true;
		}
		return false;

	}

	/**
	 * Deletes all objects from editor's objects list (editorObjects) leaving
	 * only walls in the list.
	 */
	public void clearList()
	{
		getEditorObjects().subList(3, getEditorObjects().size()).clear();
	}

	/**
	 * Clears the editorObjects, fill it with new objects until there is no free
	 * space on editor board.
	 */
	public void makeFull()
	{
		clearList();
		int d = Constants.STANDARD_WALL_WIDTH;
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
			{
				int x = col * brickWidth + d;
				int y = row * brickHeight + d;
				getEditorObjects().add(createStillObject(new Point(x, y)));
			}
	}

	/**
	 * Orders FileManaager to save actual list (editorObjects) as a level with
	 * number passed as a parameter.
	 * 
	 * @param level
	 *            number of level to save
	 * @return true, if number of levels has changed.
	 */
	public boolean saveLevel(int level)
	{
		FileManager.saveLevel(getEditorObjects(), level);
		if (level == getMaxLevel() + 1)
		{
			setMaxLevel(getMaxLevel() + 1);
			return true;
		}
		return false;
	}

	/**
	 * Loads with help of FileManager specified level to Editor. 
	 * Chosen level from EditorView is passed as a paraeter.
	 * 
	 * @param selectedIndex - selected index of combobox in view, 
	 * posing as a number of level to load
	 */
	public void loadEditorLevel(int selectedIndex)
	{
		if (selectedIndex == getMaxLevel())
		{
			clearList();
			return;
		}
		setEditorObjects(FileManager.loadLevel(selectedIndex + 1));// level 1 is
																	// on index
																	// 0, level2
																	// on index
																	// 1 e.t.c
				

	}

}
