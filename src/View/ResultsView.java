package View;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import Controller.InputController;
import Model.PlayerData;
import Utils.Constants;

/**
 * 
 * Part of the View responsible for all actions associated with results gui.
 * 
 * @author Antek
 *
 */
public class ResultsView
{
	private InputController inputController;
	private JFrame frame;
	private ResultsPanel resultsPanel;
	private NameWritingPanel nameWritingPanel;

	public ResultsView()
	{
		setFrame(new JFrame("Hall of Fame"));
		getFrame().setLayout(new BoxLayout(getFrame().getContentPane(), BoxLayout.Y_AXIS));
		getFrame().setSize(600, 600);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setNameWritingPanel(new NameWritingPanel());
		setResultsPanel(new ResultsPanel());

		getFrame().add(getNameWritingPanel());
		getFrame().add(Box.createRigidArea(new Dimension(0, 20)));
		getFrame().add(getResultsPanel());
	}

	/**
	 * Types all best results from given list into resultsPanel's text area.
	 * 
	 * @param playersList
	 *            - list with best results.
	 */
	public void showPlayers(ArrayList<PlayerData> playersList)
	{
		if (playersList.size() == 0 || playersList == null)
			getResultsPanel().getTextArea().setText("No results so far.");
		else
		{
			getResultsPanel().getTextArea().setText(String.format("%-25s %-15s %-10s %n", "Name", "Points", "Date"));
			getResultsPanel().getTextArea().append("----------------------------------------------------" + '\n');
			for (PlayerData pd : playersList)
				getResultsPanel().getTextArea().append(pd.toString());
		}
	}

	/**
	 * Shows another JFrame with best results.
	 * 
	 * @param playersList
	 *            - list with best results.
	 */
	public void showHallOfFame(ArrayList<PlayerData> playersList)
	{
		showPlayers(playersList);

		getNameWritingPanel().setVisible(false);
		getResultsPanel().setVisible(true);
		getResultsPanel().getTextArea().setCaretPosition(0);

		getFrame().setVisible(true);
	}

	/**
	 * Shows best results and oslo gives player possibility to save his own
	 * result.
	 * 
	 * @param playersList
	 */
	public void endOfGame(ArrayList<PlayerData> playersList)
	{
		showPlayers(playersList);

		getNameWritingPanel().setVisible(true);
		getResultsPanel().setVisible(true);
		getResultsPanel().getTextArea().setCaretPosition(0);

		getFrame().setVisible(true);
	}

	public InputController getInputController()
	{
		return inputController;
	}

	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
		getNameWritingPanel().setInputController(inputController);
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public void setFrame(JFrame frame)
	{
		this.frame = frame;
	}

	public ResultsPanel getResultsPanel()
	{
		return resultsPanel;
	}

	public void setResultsPanel(ResultsPanel resultsPanel)
	{
		this.resultsPanel = resultsPanel;
	}

	public NameWritingPanel getNameWritingPanel()
	{
		return nameWritingPanel;
	}

	public void setNameWritingPanel(NameWritingPanel nameWritingPanel)
	{
		this.nameWritingPanel = nameWritingPanel;
	}

}
