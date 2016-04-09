package View;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Controller.Controller;
import Model.GameObject;
import Model.Player;
import Utils.Constants;

/**
 * MainView class of the game. It is responsible for everything the user sees
 * during the the game. Has no direct connection with Model, everything happens
 * through Controller.
 *
 * It contains JFrame, LayeredPanel with game board inside it and other views
 * instances: ResultView and EditorView.
 *
 * @author Antek
 *
 */
public class View
{
	private Controller controller;

	private JFrame mainFrame;
	private LayeredPanel layeredPanel;

	private ResultsView resultsView;
	private EditorView editorView;
	private MenuView menuView;

	public ResultsView getResultsView()
	{
		return resultsView;
	}

	public void setResultsView(ResultsView resultsView)
	{
		this.resultsView = resultsView;
	}

	public LayeredPanel getLayeredPanel()
	{
		return layeredPanel;
	}

	public void setLayeredPanel(LayeredPanel layeredPanel)
	{
		this.layeredPanel = layeredPanel;
	}

	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame()
	{
		return mainFrame;
	}

	/**
	 * @param mainFrame
	 *            the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}

	/**
	 * @return the controller
	 */
	public Controller getController()
	{
		return controller;
	}

	/**
	 * Sets the controller for its own use and passes it to all interested minor
	 * views.
	 *
	 * @param controller
	 *            the controller to set
	 */
	public void setControllerAndPassIt(Controller controller)
	{
		this.controller = controller;
		getLayeredPanel().setController(getController());
		getResultsView().setInputController(getController().getInputController());
		getEditorView().setInputController(getController().getInputController());
		getMenuView().setInputController(getController().getInputController());

	}

	public EditorView getEditorView()
	{
		return editorView;
	}

	public void setEditorView(EditorView editorView)
	{
		this.editorView = editorView;
	}

	public MenuView getMenuView()
	{
		return menuView;
	}

	public void setMenuView(MenuView menuView)
	{
		this.menuView = menuView;
	}

	public View()
	{
		setMainFrame(new JFrame("Arkanoid v7.0"));
		getMainFrame().setSize(Constants.STANDARD_FRAME_WIDTH, Constants.STANDARD_FRAME_HEIGHT);
		getMainFrame().setLocationRelativeTo(null);
		getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayeredPanel(new LayeredPanel());

		getMainFrame().add(getLayeredPanel());

		setResultsView(new ResultsView());
		setEditorView(new EditorView());
		setMenuView(new MenuView());

	}

	/**
	 * Most commonly used function right beside Model's update(). Orders minor
	 * views to render themself.
	 *
	 * @param ListOfObjects
	 *            - list of all objects in game to render.
	 * @param player
	 *            - player to show its points and lifes.
	 */
	public void render(ArrayList<GameObject> ListOfObjects, Player player)
	{
		getLayeredPanel().render(ListOfObjects, player);

	}

}
