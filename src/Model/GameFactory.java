package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import Model.Upgrade.BiggerRacketUpgrade;
import Model.Upgrade.FallingUpgrade;
import Model.Upgrade.Upgrade;
import Utils.Constants;
import Utils.FileManager;

/**
 *
 *
 * Class, where all of objects are made.
 *
 * @author Antek
 *
 */
public class GameFactory implements PropertyChangeListener
{

	private ArrayList<GameObject> GameObjectList;
	private ArrayList<MovableObject> MovableObjectList;
	private ArrayList<StillObject> StillObjectList;

	private PropertyChangeSupport pcs;

	/**
	 * @return the gameObjectList
	 */
	public ArrayList<GameObject> getGameObjectList()
	{
		return GameObjectList;
	}

	/**
	 * @param gameObjectList
	 *            the gameObjectList to set
	 */
	public void setGameObjectList(ArrayList<GameObject> gameObjectList)
	{
		GameObjectList = gameObjectList;
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

	public PropertyChangeSupport getPcs()
	{
		return pcs;
	}

	public void setPcs(PropertyChangeSupport pcs)
	{
		this.pcs = pcs;
	}

	/**
	 * Contructor of GamaFactory. Parameters below are lists, where newly
	 * created object will be placed.
	 *
	 * @param GameObjectList
	 * @param MovableObjectList
	 * @param StillObjectList
	 */
	public GameFactory(ArrayList<GameObject> GameObjectList, ArrayList<MovableObject> MovableObjectList,
			ArrayList<StillObject> StillObjectList, PropertyChangeSupport prc)
	{
		this.GameObjectList = GameObjectList;
		this.MovableObjectList = MovableObjectList;
		this.StillObjectList = StillObjectList;
		setPcs(prc);
		getPcs().addPropertyChangeListener("AdditionalBallsUpgrade", this);
	}

	/**
	 * This function creates new ball on top of the racket (ball is NOT moving),
	 * and adds it to the proper lists.
	 *
	 * @param racket,
	 *            wchich coordinates are used to place new Ball
	 * @return new Ball
	 */
	public Ball createBall(Racket racket)
	{
		Ball ball = new Ball(randomColor(),
				(int) (racket.getX() + (racket.getWidth() / 2)) - (Constants.STANDARD_BALL_RADIUS),
				(int) (racket.getY() - 2 * Constants.STANDARD_BALL_RADIUS), Constants.STANDARD_BALL_RADIUS * 2,
				Constants.STANDARD_BALL_RADIUS * 2, Constants.STANDARD_BALL_RADIUS,
				(1 - (2 * ((int) (Math.random() * 2)))), -1, Math.random(), Constants.STANDARD_BALL_SPEED);
		getGameObjectList().add(ball);
		getMovableObjectList().add(ball);

		// getPcs().addPropertyChangeListener("AdditionalBallsUpgrade", ball);

		return ball;
	}

	/**
	 * Creates additional balls when AdditionalBallsUpgrade is picked. Newly
	 * created balls appear on top of the origin ball and have sligty different
	 * X nad Y velocities.
	 *
	 * @param ob
	 *            - origin ball
	 * @param numBalls
	 *            - number of new balls
	 */
	public void createBalls(Ball ob, int numBalls)
	{
		double dispersionZone = 0.40;
		System.out.println(
				"ratio pilki-matki " + ob.getRatio() + "X_dir" + ob.getDirectionX() + " Y_dir " + ob.getDirectionY());
		for (int i = 1; i <= numBalls; i++)
		{
			System.out.println("additional ball no. " + i + " created! whoa");
			int dirX = ob.getDirectionX();
			int dirY = ob.getDirectionY();

			double ratio = ob.getRatio()
					+ (Math.pow(-1, i) * ((dispersionZone / numBalls) * (1 + ((int) (i - 1) / 2))));
			System.out.println("pilka nr " + i + ", moje ratio to!" + ratio);
			if (ratio > 1)
			{
				ratio = 2 - ratio;
				dirY = ob.getDirectionY() * (-1);
			}
			if (ratio < 0)
			{
				ratio = -ratio;
				dirX = ob.getDirectionX() * (-1);
			}
			System.out.println("pilka nr " + i + ",A moje nowe ratio to!" + ratio + "X_dir" + dirX + " Y_dir " + dirY);
			FileManager.sleep(100);
			Ball ball = new Ball(Color.white, ob.getX(), ob.getY(), Constants.STANDARD_BALL_RADIUS * 2,
					Constants.STANDARD_BALL_RADIUS * 2, Constants.STANDARD_BALL_RADIUS, dirX, dirY, ratio,
					Constants.STANDARD_BALL_SPEED);
			ball.setMoving(true);
			getGameObjectList().add(ball);
			getMovableObjectList().add(ball);

			getPcs().addPropertyChangeListener("AdditionalBallsUpgrade", ball);

		}
	}

	/**
	 * Called at the beginning of the game, when game board is created. Creates
	 * the racket at the middle bottom of the game area.
	 *
	 * @return newly created racket
	 */
	public Racket createRacket()
	{
		Dimension dim = new Dimension(Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_ARENA_HEIGHT);
		Racket racket = new Racket(Color.orange, ((int) (dim.getWidth() - Constants.STANDARD_RACKET_WIDTH) / 2),
				((int) (dim.getHeight() - Constants.STANDARD_RACKET_HEIGHT)), Constants.STANDARD_RACKET_WIDTH,
				Constants.STANDARD_RACKET_HEIGHT, Constants.STANDARD_RACKET_SPEED);
		getGameObjectList().add(racket);

		getPcs().addPropertyChangeListener("BiggerRacketUpgrade", racket);
		getPcs().addPropertyChangeListener("SmallerRacketUpgrade", racket);

		return racket;
	}

	/**
	 * Called at the beginning of the game, when game board is created. Creates
	 * walls, which surround game board on the sides and on the top, and adds
	 * these 3 objects into proper lists.
	 *
	 *
	 */
	public void createWalls()
	{
		Dimension dim = new Dimension(Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_ARENA_HEIGHT);

		Wall wallLeft = new Wall(new Color(178, 34, 34), 0, 0, 10, (int) dim.getHeight(), false);
		Wall wallUp = new Wall(new Color(178, 34, 34), 0, 0, (int) dim.getWidth(), 10, false);
		Wall wallRight = new Wall(new Color(178, 34, 34), ((int) dim.getWidth() - 10), 0, 10, (int) dim.getHeight(),
				false);

		getGameObjectList().add(wallLeft);
		getGameObjectList().add(wallUp);
		getGameObjectList().add(wallRight);

		getStillObjectList().add(wallUp);
		getStillObjectList().add(wallRight);
		getStillObjectList().add(wallLeft);

	}

	/**
	 * Called when creating EditorModel. Creates walls, which surround game
	 * board on the sides and on the top and add them to the editorModel list.
	 *
	 *
	 */
	public void createWalls(ArrayList<StillObject> list)
	{
		Dimension dim = new Dimension(Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_ARENA_HEIGHT);
		Wall wallLeft = new Wall(new Color(178, 34, 34), 0, 0, 10, (int) dim.getHeight(), false);
		Wall wallUp = new Wall(new Color(178, 34, 34), 0, 0, (int) dim.getWidth(), 10, false);
		Wall wallRight = new Wall(new Color(178, 34, 34), ((int) dim.getWidth() - 10), 0, 10, (int) dim.getHeight(),
				false);

		list.add(wallLeft);
		list.add(wallUp);
		list.add(wallRight);

	}

	/**
	 * @return newly created Payer
	 */
	public Player createPlayer()
	{
		Player p = new Player(Constants.STANDARD_HP);
		return p;
	}

	/**
	 * Creates brick. Color depends on health parameter.
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param health
	 * @return Brick created with given parameters.
	 */
	public Brick createBrick(int x, int y, int width, int height, int health)
	{
		Color color = Color.white;
		if (health == 1)
			color = Color.cyan;// color = new Color(150, 255, 150);// green
		if (health == 2)
			color = new Color(150, 150, 255);// blue
		if (health == 3)
			color = new Color(200, 80, 250);// purple
		if (health == 4)
			color = new Color(255, 150, 150);// red

		return new Brick(color, x, y, width, height, health);

	}

	/**
	 * Create unmovableBrick with given parameters. Color is always the same -
	 * light gray.
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public UnmovableBrick createUnmovableBrick(int x, int y, int width, int height)
	{
		return new UnmovableBrick(Color.lightGray, x, y, width, height);
	}

	/**
	 * Function used to create random game board, when there is no files with
	 * levels to load.
	 *
	 * @param cols
	 *            number of colums
	 * @param rows
	 *            number of rows
	 */
	public void createBricks(int cols, int rows)
	{
		int brickWidth = (int) ((Constants.STANDARD_ARENA_WIDTH - 2 * Constants.STANDARD_WALL_WIDTH) / cols);
		int brickHeight = (int) brickWidth / 5;
		for (int i = 0; i < rows; i = i + 1)
		{
			int r = (int) (Math.random() * 4) + 1;
			for (int j = 0; j < cols; j = j + 1)
			{
				Brick b = createBrick(j * brickWidth + Constants.STANDARD_WALL_WIDTH,
						i * brickHeight + Constants.STANDARD_WALL_WIDTH, brickWidth, brickHeight, r);

				getGameObjectList().add(b);
				getStillObjectList().add(b);
			}
		}
	}

	/**
	 * Class representing newly created upgrade, which is falling down from
	 * destroyed brick. If Racket collects it, some bonus will be fired.
	 *
	 * Bonus is choosen randomly.
	 *
	 * @param b
	 *            - destroyed brick: the origin of fallingUpgrade
	 */
	public void createFallingUpdate(Brick b)
	{
		double r = Math.random();
		Color color;
		Upgrade upgr;
		/*
		 * if (r > 0.5) { color = Color.green; upgr = new
		 * BiggerRacketUpgrade(Constants.BIGGER_RACKET_UPGRADE_TIME,
		 * "BiggerRacketUpgrade"); } else { color = Color.cyan; upgr = new
		 * BiggerRacketUpgrade(Constants.BIGGER_RACKET_UPGRADE_TIME,
		 * "SmallerRacketUpgrade"); }
		 */
		color = Color.pink;
		upgr = new BiggerRacketUpgrade(Constants.BIGGER_RACKET_UPGRADE_TIME, "AdditionalBallsUpgrade");
		FallingUpgrade fu = new FallingUpgrade(color, b.getX() + (b.getWidth() - Constants.STANDARD_UPGRADE_SIZE) / 2,
				b.getY(), Constants.STANDARD_UPGRADE_SIZE, Constants.STANDARD_UPGRADE_SIZE, 0, 1, 0.0,
				Constants.STANDARD_UPGRADE_SPEED, upgr);
		fu.setMoving(true);
		getMovableObjectList().add(fu);
		getGameObjectList().add(fu);
	}

	/**
	 * @return new Color object with random red, green adn blue colors
	 *         saturation.
	 */
	public Color randomColor()
	{
		int b = (int) (Math.random() * 145);
		int r = (int) (Math.random() * 145);
		int g = (int) (Math.random() * 145);

		Color color = new Color(b, r, g);
		return color;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName() == "AdditionalBallsUpgrade")
		{
			System.out.println("sure!");
			if ((Boolean) evt.getNewValue() == true)
			{
				int k = getMovableObjectList().size();
				for (int i = 0; i < k; i++)
				{
					MovableObject mo = getMovableObjectList().get(i);
					if (mo instanceof Ball || mo.isMoving() || (mo.getColor() != Color.white))
					{
						if (mo instanceof FallingUpgrade)
							continue;
						System.out.println("what?");
						createBalls((Ball) mo, 2);

					}
				}
			}
		}

	}

}
