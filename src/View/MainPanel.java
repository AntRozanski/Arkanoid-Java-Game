package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Model.GameObject;
import Model.Player;
import Utils.Constants;

/**
 * 
 * Main Panel of the game - it contains GamePanel, where game board is and
 * PlayerPanel, where remaining lifes and points are visible.
 * 
 * @author Antek
 *
 */
public class MainPanel extends JPanel
{
	private GamePanel gamePanel;
	private PlayerPanel playerPanel;

	Dimension area;

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel)
	{
		this.gamePanel = gamePanel;
	}

	public PlayerPanel getPlayerPanel()
	{
		return playerPanel;
	}

	public void setPlayerPanel(PlayerPanel playerPanel)
	{
		this.playerPanel = playerPanel;
	}

	public MainPanel()
	{
		this.setGamePanel(new GamePanel());
		this.setPlayerPanel(new PlayerPanel());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		area = (new Dimension(Constants.STANDARD_ARENA_WIDTH,
				Constants.STANDARD_PLAYER_AREA_HEIGHT + Constants.STANDARD_ARENA_HEIGHT));

		add(getPlayerPanel());
		add(getGamePanel());

		setSize(area);
		setBackground(Color.yellow);

	}

	public void setListOfObjectsToRender(ArrayList<GameObject> ListOfObjects)
	{
		getGamePanel().setListOfObjectsToRender(ListOfObjects);
	}

	public void setPlayer(Player player)
	{ 
		getPlayerPanel().setPlayer(player);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
