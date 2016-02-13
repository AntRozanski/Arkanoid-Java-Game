package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Model.Model;
import View.GlassPanel;
import View.View;

/**
 * Class for mouse/keyboard managment.
 * 
 * @author Antek
 *
 */

public class InputController
{
	/**
	 * When true, blocks all functions. Changing when 'P' key is clicked.
	 * 
	 */
	private boolean blockade;
	private Model model;
	private View view;
	private Controller controller;

	public View getView()
	{
		return view;
	}

	public void setView(View view)
	{
		this.view = view;
	}

	/**
	 * @return the model
	 */
	public Model getModel()
	{
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Model model)
	{
		this.model = model;
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
	 * @return the blockade
	 */
	public boolean isBlockade()
	{
		return blockade;
	}

	/**
	 * @param blockade
	 *            the blockade to set
	 */
	public void setBlockade(boolean blockade)
	{
		this.blockade = blockade;
	}

	/**
	 * Constructor of InputController.
	 * 
	 * @param model
	 *            to set
	 * @param controller
	 *            to set
	 * @param view
	 *            to set
	 */
	public InputController(Model model, Controller controller, View view)
	{
		this.setModel(model);
		this.setController(controller);
		this.view = view;
		this.blockade = false;
	}

	/**
	 * Function for EditorView use. It add or delete Brick, depending on fields
	 * od Mouse Event me.
	 * 
	 * @param me
	 *            MouseEvent, contains info about desirable action.
	 */
	public void handleMouseInput(MouseEvent me)
	{
		if (me.getButton() == MouseEvent.BUTTON1)
		{
			addEditorBrick(me);
		}
		if (me.getButton() == MouseEvent.BUTTON3)
		{
			deleteEditorBrick(me);
		}
	}

	/**
	 * Funtion, which processes players inputs. It may change fields of
	 * controller and model.
	 * 
	 * @param ke
	 *            KeyEvent. Contains information about Key on which action
	 *            occured.
	 */
	public void handleInput(KeyEvent ke)
	{
		if (ke.getKeyCode() == KeyEvent.VK_P)
		{
			if ((getController().isRunning() == true))
			{
				blockade = true;
				getController().setPause(true);
			}
			else
			{
				blockade = false;
				getView().getLayeredPanel().getGlassPanel().stopPause();
				getController().setRunning(true);
			}
		}

		if (!blockade)
		{
			if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_LEFT)
			{

				if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
					getModel().setRacketDirection(1);
				else
					getModel().setRacketDirection(-1);

			}
			if (ke.getKeyCode() == KeyEvent.VK_DOWN)
			{
				getModel().shootBall();
			}
			if (ke.getKeyCode() == KeyEvent.VK_H)
			{
				showHallOfFame();
			}
			if (ke.getKeyCode() == KeyEvent.VK_E)
			{
				showEditor();
			}
			if (ke.getKeyCode() == KeyEvent.VK_UP)
			{
				getModel().setNewBall(true);
			}
			if (ke.getKeyCode() == KeyEvent.VK_S)
			{
				getModel().getPcs().firePropertyChange("AdditionalBallsUpgrade", false, true);
			}
			if (ke.getKeyCode() == KeyEvent.VK_X)
			{
				getModel().getPcs().firePropertyChange("AdditionalBallsUpgrade", true, false);
			}
		}

	}

	/**
	 * Function called from EditorsView, when player enters his name in
	 * TextField. It passes name to resultModel, where it is saved, and make
	 * ResultsView referesh list of players.
	 * 
	 * @param name
	 *            to save
	 */
	public void saveResult(String name)
	{
		getModel().getResultsModel().savePlayer(name);
		getView().getResultsView().getNameWritingPanel().getButton().setEnabled(false);

		getView().getResultsView().showPlayers(getModel().getResultsModel().getPlayersList());// renderuje
																								// liste
																								// //
																								// wynikow
	}

	/**
	 * Function shows best results. It is called during the game, when Player
	 * hits proper shortcut and at the end of the game.
	 */
	public void showHallOfFame()
	{
		getView().getResultsView().showHallOfFame(getModel().getResultsModel().getPlayersList());
	}

	/**
	 * Function shows editor. It is called during the game, when Player hits
	 * proper shortcut.
	 */
	public void showEditor()
	{
		getView().getEditorView().getEditorFrame().setVisible(true);
	}

	/**
	 * Adds brick to the editorModel's list and refreshes editorView.
	 * 
	 * @param me
	 *            MouseEvent passed to editorModel.
	 */
	public void addEditorBrick(MouseEvent me)
	{
		if (getModel().getEditorModel().addStillObject(me))
			getView().getEditorView().show(getModel().getEditorModel().getEditorObjects());
	}

	/**
	 * Deletes brick from the editorModel's list and refreshes editorView.
	 * 
	 * @param me
	 *            MouseEvent passed to editorModel.
	 */
	public void deleteEditorBrick(MouseEvent me)
	{
		if (getModel().getEditorModel().deleteStillObject(me))
			getView().getEditorView().show(getModel().getEditorModel().getEditorObjects());
	}

	/**
	 * Deletes all bricks from the editorModel's list and refreshes editorView.
	 */
	public void clearEditorList()
	{
		getModel().getEditorModel().clearList();

		getView().getEditorView().show(getModel().getEditorModel().getEditorObjects());
	}

	/**
	 * Fill all free space in the editorModel's list with bricks and refreshes
	 * editorView.
	 */
	public void makeEditoListFull()
	{
		getModel().getEditorModel().makeFull();
		getView().getEditorView().show(getModel().getEditorModel().getEditorObjects());
	}

	/**
	 * Passes new editorView's selected brick to editorModel.
	 * 
	 * @param i
	 *            HP of new brick, passed to editorModel.
	 */
	public void changeBrick(int i)
	{
		getModel().getEditorModel().setBrickHealth(i + 1);
	}

	/**
	 * Function called when player hits "Save" button in editorView. Passes
	 * number of level to editorModel and, if needed, increases list of levels
	 * in editorView.
	 * 
	 * @param level
	 *            level number to pass to editorModel.
	 */
	public void saveLevel(int level)
	{
		if (getModel().getEditorModel().saveLevel(level))
			getView().getEditorView().getChoicePanel().changeMaxLevel(level);
	}

	/**
	 * Function called when playe using the mouse change level in editorView.
	 * 
	 * Passes seleccted level to editorModel in order to load new list of
	 * bricks. Shows new bricks in editorView.
	 * 
	 * @param selectedIndex
	 *            to pass
	 */
	public void changeLevel(int selectedIndex)
	{

		getModel().getEditorModel().loadEditorLevel(selectedIndex);
		getView().getEditorView().show(getModel().getEditorModel().getEditorObjects());
	}

	/**
	 * Function called when user stops pressing left or right arrow and
	 * therefore racket should stop moving.
	 */
	public void keyReleased(KeyEvent ke)
	{
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_LEFT)
			getModel().setRacketDirection(0);
	}
}
