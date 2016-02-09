package Model;

import java.awt.Dimension;
import java.util.ArrayList;

import Controller.Controller;
import Utils.Constants;
import Utils.FileManager;

/**
 * Main class in Model package. Here all calculation are done and all data is
 * stored.Has no direct connection with View, everything happens
 * through Controller.
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

	/**
	 * @return the newBall
	 */
	public boolean isNewBall()
	{
		return newBall;
	}

	/**
	 * @param newBall
	 *            the newBall to set
	 */
	public void setNewBall(boolean newBall)
	{
		this.newBall = newBall;
	}

	/**
	 * @return the racketDirection
	 */
	public int getRacketDirection()
	{
		return racketDirection;
	}

	/**
	 * @param racketDirection
	 *            the racketDirection to set
	 */
	public void setRacketDirection(int racketDirection)
	{
		this.racketDirection = racketDirection;
	}

	/**
	 * @return the objectList
	 */
	public ArrayList<GameObject> getGameObjectList()
	{
		return GameObjectList;
	}

	/**
	 * @param objectList
	 *            the objectList to set
	 */
	public void setGameObjectList(ArrayList<GameObject> objectList)
	{
		this.GameObjectList = objectList;
	}

	/**
	 * @return the movableObjectList
	 */
	public ArrayList<MovableObject> getMovableObjectList()
	{
		return MovableObjectList;
	}

	/**
	 * @param movableObjectList
	 *            the movableObjectList to set
	 */
	public void setMovableObjectList(ArrayList<MovableObject> movableObjectList)
	{
		MovableObjectList = movableObjectList;
	}

	/**
	 * @return the stillObjectList
	 */
	public ArrayList<StillObject> getStillObjectList()
	{
		return StillObjectList;
	}

	/**
	 * @param stillObjectList
	 *            the stillObjectList to set
	 */
	public void setStillObjectList(ArrayList<StillObject> stillObjectList)
	{
		StillObjectList = stillObjectList;
	}

	/**
	 * @return the factory
	 */
	public GameFactory getFactory()
	{
		return factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(GameFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * @return the controller
	 */
	public Controller getController()
	{
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	/**
	 * @return the racket
	 */
	public Racket getRacket()
	{
		return racket;
	}

	/**
	 * @param racket
	 *            the racket to set
	 */
	public void setRacket(Racket racket)
	{
		this.racket = racket;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
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

		setFactory(new GameFactory(GameObjectList, MovableObjectList, StillObjectList));

		setPlayer(getFactory().createPlayer());
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
					if (movOb instanceof Ball)
						if (getPlayer().decreaseHP())
						{
							getController().setOver(true);
							break;
						}
					continue;
				}
				if (movOb.collide(getRacket()))
					movOb.processCollision(getRacket());

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
							getPlayer().addPoints(10);
							if (true) // TODO
							{
								// TODO: factory.createFallingUpdate((Brick)
								// stillOb);

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
				if (movOb instanceof Ball)
					movOb.setCoordinates(
							(int) (getRacket().getX() + (getRacket().getWidth() / 2)) - (((Ball) movOb).getRadius()),
							(int) (getRacket().getY() - ((Ball) movOb).getRadius() * 2));
			}
		}

	}

	/**
	 * Fire one ball from racket, if there is any ball on the racket.
	 */
	public void shootBall()
	{
		for (int i = 0; i < getMovableObjectList().size(); i++)
		{
			MovableObject movOb = getMovableObjectList().get(i);
			if (!movOb.isMoving())
			{
				movOb.setMoving(true);
				break;
			}
		}
	}
}
