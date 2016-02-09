package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Model.GameObject;
import Utils.Constants;

/**
 * JPanel with game board. Used also in editorView.
 * 
 * @author Antek
 *
 */
public class GamePanel extends JPanel
{
	private Dimension gamePanelArea;
	private ArrayList<GameObject> listOfObjectsToRender;
	private Color backgroundColor;

	/**
	 * @return the gameArea
	 */
	public Dimension getGamePanelArea()
	{
		return gamePanelArea;
	}

	/**
	 * @param gameArea
	 *            the gameArea to set
	 */
	public void setGamePanelArea(Dimension gameArea)
	{
		this.gamePanelArea = gameArea;
	}

	/**
	 * @return the listofObjectsToRender
	 */
	public ArrayList<GameObject> getListOfObjectsToRender()
	{
		return listOfObjectsToRender;
	}

	/**
	 * @param listOfObjectsToRender
	 *            the listofObjectsToRender to set
	 */
	public <T extends GameObject> void setListOfObjectsToRender(ArrayList<T> listOfObjectsToRender)
	{
		this.listOfObjectsToRender = (ArrayList<GameObject>) listOfObjectsToRender;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public GamePanel()
	{
		setGamePanelArea(new Dimension(Constants.STANDARD_ARENA_WIDTH, Constants.STANDARD_ARENA_HEIGHT));
		setPreferredSize(getGamePanelArea());
		setBackgroundColor(new Color(150, 199, 240));
	}

	@Override
	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		g.setColor(getBackgroundColor());
		g.fillRect(0, 0, getGamePanelArea().width, getGamePanelArea().height);

		if (!(getListOfObjectsToRender() == null))
			for (int i = 0; i < getListOfObjectsToRender().size(); i++)
			{
				GameObject go = getListOfObjectsToRender().get(i);
				go.draw(g);
			}
		g.setColor(Color.black);
		g.drawRect(0, 0, getGamePanelArea().width - 1, getGamePanelArea().height - 1);
	}

	public Color randomColor()
	{
		int r = 0;
		int b = 0;
		int g = 0;
		r = (int) (Math.random() * 256);
		b = (int) (Math.random() * 256);
		g = (int) (Math.random() * 256);
		return new Color(r, g, b);
	}
}
