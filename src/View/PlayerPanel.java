package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import Model.Player;
import Utils.Constants;

/**
 * JPanel with actual player standings.
 * 
 * @author Antek
 *
 */
public class PlayerPanel extends JPanel {
	private Player player;
	private Dimension playerPanelArea;
	private MainPanel mainPanel;
	Font f = new Font("monospaced", Font.BOLD, 16);

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;

	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the playerArea
	 */
	public Dimension getPlayerPanelArea() {
		return playerPanelArea;
	}

	/**
	 * @param playerArea
	 *            the playerArea to set
	 */
	public void setPlayerPanelArea(Dimension playerArea) {
		this.playerPanelArea = playerArea;
	}

	public PlayerPanel() {
		setPlayerPanelArea(new Dimension(Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_PLAYER_AREA_HEIGHT));
		setPreferredSize(getPlayerPanelArea());
	}

	public void addFather(MainPanel mp) {
		setMainPanel(mp);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.pink);
		g.fillRect(0, 0, Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_PLAYER_AREA_HEIGHT);
		g.setColor(Color.black);
		g.drawRect(0, 0, Constants.STANDARD_ARENA_WIDTH - 1, Constants.STANDARD_PLAYER_AREA_HEIGHT);

		String numBalls = new String("HPs left: " + getPlayer().getHealth());
		String points = new String("Points: " + getPlayer().getPoints());

		g.setFont(f);
		g.drawString(numBalls, 10, 25);
		g.drawString(points, Constants.STANDARD_ARENA_WIDTH - 150, 25);
		g.drawString(getPlayer().getTimeAsString(), 220, 25);

	}
}
