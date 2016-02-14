package Model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import Controller.Controller;
import Model.Upgrade.FallingUpgrade;
import Model.Upgrade.Missile;
import Utils.Constants;
import Utils.FileManager;

/**
 * Main class in Model package. Here all calculation are done and all data is
 * stored.Has no direct connection with View, everything happens through
 * Controller.
 *
 *
 * @author Antek
 *
 */
public class Model
{
	private boolean newBall = false;
	private int racketDirection;

	private Controller controller;
	private EditorModel editorModel;
	private ResultsModel resultsModel;

	private ArrayList<GameObject> GameObjectList;
	private ArrayList<MovableObject> MovableObjectList;
	private ArrayList<StillObject> StillObjectList;

	private GameFactory factory;
	private Racket racket;
	private Player player;

	private int currentLevel;

	private PropertyChangeSupport pcs;

	public boolean isNewBall()
	{
		return newBall;
	}

	public void setNewBall(boolean newBall)
	{
		this.newBall = newBall;
	}

	public int getRacketDirection()
	{
		return racketDirection;
	}

	public void setRacketDirection(int racketDirection)
	{
		this.racketDirection = racketDirection;
	}

	public ArrayList<GameObject> getGameObjectList()
	{
		return GameObjectList;
	}

	public void setGameObjectList(ArrayList<GameObject> objectList)
	{
		this.GameObjectList = objectList;
	}

	public ArrayList<MovableObject> getMovableObjectList()
	{
		return MovableObjectList;
	}

	public void setMovableObjectList(ArrayList<MovableObject> movableObjectList)
	{
		MovableObjectList = movableObjectList;
	}

	public ArrayList<StillObject> getStillObjectList()
	{
		return StillObjectList;
	}

	public void setStillObjectList(ArrayList<StillObject> stillObjectList)
	{
		StillObjectList = stillObjectList;
	}

	public GameFactory getFactory()
	{
		return factory;
	}

	public void setFactory(GameFactory factory)
	{
		this.factory = factory;
	}

	public Controller getController()
	{
		return controller;
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	public Racket getRacket()
	{
		return racket;
	}

	public void setRacket(Racket racket)
	{
		this.racket = racket;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public ResultsModel getResultsModel()
	{
		return resultsModel;
	}

	public void setResultsModel(ResultsModel resultsModel)
	{
		this.resultsModel = resultsModel;
	}

	public EditorModel getEditorModel()
	{
		return editorModel;
	}

	public void setEditorModel(EditorModel editorModel)
	{
		this.editorModel = editorModel;
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel)
	{
		this.currentLevel = currentLevel;
	}

	public PropertyChangeSupport getPcs()
	{

		return pcs;
	}

	public void setPcs(PropertyChangeSupport pcs)
	{
		this.pcs = pcs;
	}

	/**
	 * Constructor for Model class. Uses GameFactory class for creating some
	 * instances and add those to newly created lists of GameObjects.
	 */
	public Model()
	{
		setCurrentLevel(0);
		setGameObjectList(new ArrayList<GameObject>());
		setMovableObjectList(new ArrayList<MovableObject>());
		setStillObjectList(new ArrayList<StillObject>());

		setPcs(new PropertyChangeSupport(this));
		setFactory(new GameFactory(GameObjectList, MovableObjectList, StillObjectList, getPcs()));

		setPlayer(getFactory().createPlayer());
		getPlayer().setPcs(getPcs());
		setRacket(getFactory().createRacket());
		getFactory().createWalls();
		loadNextLevel();
		setResultsModel(new ResultsModel(this.getPlayer()));
		setEditorModel(new EditorModel(getFactory()));
		getFactory().createWalls(getEditorModel().getEditorObjects());

		newBall = true;

	}

	/**
	 * Function used when all of remaining bricks at the board are gone. It uses
	 * help of FileManager class from Utils package to load next level, if there
	 * is no next level to load first level avalible, and finally when there is
	 * no level files at all - to create random board using GameFactory class.
	 */
	public void loadNextLevel()
	{
		ArrayList<StillObject> nextLevel = FileManager.loadLevel(getCurrentLevel() + 1);
		if (nextLevel == null) // if there is no next level file
		{
			nextLevel = FileManager.loadLevel(1);// start loading levels from
													// begining
			setCurrentLevel(0);// below it will be ioncreased to level no 1
			if (nextLevel == null) // if there is not files at all, createrandom
									// level
			{
				getFactory().createBricks(10, 15);
				setCurrentLevel(0);
				getGameObjectList().removeAll(getMovableObjectList());
				getMovableObjectList().clear();
				return;
			}
		}
		getGameObjectList().removeAll(getMovableObjectList());
		getGameObjectList().removeAll(getStillObjectList());
		setStillObjectList(nextLevel);
		getGameObjectList().addAll(nextLevel);
		setCurrentLevel(getCurrentLevel() + 1);
		getMovableObjectList().clear();
		getPlayer().resetUpgrades();

	}

	/**
	 * Function called every controller's main loop course. It is the place
	 * where all calulations are made and objects are being moved, deleted or
	 * created. If there is no visible change, method return false to stop View
	 * from pointless refreshing.
	 *
	 * @return true, when visible change occurs
	 */
	public boolean update()
	{
		boolean isChanged = false;
		getPlayer().updateUpgrades();

		if (getRacket().getWidth() != getRacket().getDesiredWidth())
		{
			getRacket().grow();
			isChanged = true;
		}
		if (getRacketDirection() != 0)
		{
			getRacket().move(getRacketDirection());
			moveItemOnRacket();
			isChanged = true;
		}

		if (isNewBall())
		{
			 getFactory().createBall(racket);
			setNewBall(false);
			isChanged = true;
		}
		// PART FOR THE MOVABLE OBJECTS
		for (int i = 0; i < getMovableObjectList().size(); i++)
		{
			MovableObject movOb = getMovableObjectList().get(i);
			if (movOb.isMoving())
			{
				isChanged = true;
				if (movOb.toDelete())
				{
					remove(movOb);
					i--;
					if (movOb instanceof Ball || movOb.getY() > Constants.STANDARD_ARENA_HEIGHT)
						if (getPlayer().decreaseHP())
						{
							getController().setOver(true);
							break;
						}
					continue;
				}
				if (movOb.collide(getRacket()))
				{
					movOb.processCollision(getRacket());
					if (movOb instanceof FallingUpgrade)
					{
						getPlayer().addUpgrade(((FallingUpgrade) movOb).getUpgrade());
						getPlayer().addPoints(10);
						isChanged=true;
					}

				}
				if (levelEmpty())
				{
					loadNextLevel();
					getController().setNextLevel(true);
					return true;
				}
				for (int k = 0; k < getStillObjectList().size(); k++)
				{
					StillObject stillOb = getStillObjectList().get(k);
					if (movOb.collide(stillOb))
					{
						movOb.processCollision(stillOb);

						if (stillOb.gotHit(movOb))
						{
							remove(stillOb);
							getPlayer().addPoints(20);
							if (true)
							{
								factory.createFallingUpdate((Brick) stillOb);
							}
							k--;
						}
					}
				}
				movOb.move();
			}
		}
		return isChanged;
	}

	/**
	 * Check if there are some bricks left.
	 *
	 * @return true, there is no more bricks to smash.
	 */
	private boolean levelEmpty()
	{
		if (getStillObjectList().size() <= 3) // there are allways 3 objects in
												// list - walls.
			return true;
		for (StillObject so : getStillObjectList())
			if (so instanceof Brick)
				return false;

		return true;
	}

	/**
	 * Function called when specific gamObejct is being killed and therefore
	 * should be deleted from list, which contains it.
	 *
	 * @param go
	 *            - object to remove
	 */
	public void remove(GameObject go)
	{
		if (go instanceof MovableObject)
		{
			getMovableObjectList().remove(go);
			getGameObjectList().remove(go);

		}
		else if (go instanceof StillObject)
		{
			getStillObjectList().remove(go);
			getGameObjectList().remove(go);

		}

	}

	/**
	 * Changes position of all movableObject that weren't fired from racket when
	 * racket moves, so they're still at the center of racket.
	 */
	public void moveItemOnRacket()
	{
		for (MovableObject movOb : getMovableObjectList())
		{
			if (!movOb.isMoving())
			{
				// if (movOb instanceof Ball)
				movOb.setCoordinates((int) (getRacket().getX() + (getRacket().getWidth() / 2)) - (movOb.getWidth() / 2),
						(int) (getRacket().getY() - movOb.getHeight()));
			}
		}

	}

	/**
	 * Fires one ball from the racket, if there is any ball.
	 */
	public void shootBall()
	{
		for (int i = 0; i < getMovableObjectList().size(); i++)
		{
			MovableObject movOb = getMovableObjectList().get(i);
			if (movOb.getClass() == Ball.class && !movOb.isMoving())
			{
				movOb.setMoving(true);
				break;
			}
		}
	}

	public void fireMissile()
	{
		for (int i = 0; i < getMovableObjectList().size(); i++)
		{
			MovableObject movOb = getMovableObjectList().get(i);
			if (movOb.getClass() == Missile.class && !movOb.isMoving())
			{
				movOb.setMoving(true);
				break;
			}
		}
	}
}
