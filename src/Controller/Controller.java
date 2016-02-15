package Controller;

import Model.Model;
import Utils.Constants;
import Utils.FileManager;
import View.View;

/**
 * Class for main controller of the game. Iit supervises whole gameplay.
 *
 * @author Antek
 *
 */
public class Controller
{
	private View view;
	private Model model;
	private InputController inputController;

	private volatile boolean isRunning;
	private boolean isOver;

	private boolean nextLevel;
	private boolean isPause;

	public boolean isNextLevel()

	{
		return nextLevel;
	}

	public void setNextLevel(boolean nextLevel)
	{
		this.nextLevel = nextLevel;
	}

	/**
	 * @return the view
	 */
	public View getView()
	{
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
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
	 * @return the isRunning
	 */
	public boolean isRunning()
	{
		return isRunning;
	}

	/**
	 * @param isRunning
	 *            the isRunning to set
	 */
	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}

	/**
	 * @return the isOver
	 */
	public boolean isOver()
	{
		return isOver;
	}

	/**
	 * @param isOver
	 *            the isOver to set
	 */
	public void setOver(boolean isOver)
	{
		this.isOver = isOver;
	}

	/**
	 * @return the inputController
	 */
	public InputController getInputController()
	{
		return inputController;
	}

	/**
	 * @param inputController
	 *            the inputController to set
	 */
	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
	}

	public boolean isPause()
	{
		return isPause;
	}

	public void setPause(boolean isPause)
	{
		this.isPause = isPause;
	}

	/**
	 * Constructor initalizes itself and part of View, which may be visible
	 * before it will be rendered in models update() function.
	 *
	 * @param view
	 *            to set
	 * @param model
	 *            to set
	 */
	public Controller(View view, Model model)
	{
		this.model = model;
		this.view = view;
		setInputController(new InputController(model, this, view));
		setRunning(true);
		setPause(false);
		setOver(false);
		setNextLevel(false);

		getView().getLayeredPanel().getMainPanel().getPlayerPanel().setPlayer(getModel().getPlayer());
		getView().getEditorView().getEditorPanel()
				.setListOfObjectsToRender(getModel().getEditorModel().getEditorObjects());
		getView().getEditorView().getChoicePanel().createChoicePanel(getModel().getEditorModel().getMaxLevel());
	}

	/**
	 * Main function of the game. In main loop of the game, model is updating
	 * and view is rendering altenately, as long as there is no pause/new level
	 * screen called and player life amount is bigger than zero (if is less,
	 * funtion and game ends).
	 *
	 * Every cycle of game takes fixed amount od time. This amount can be
	 * changed in class Constants - part of Utils package.
	 *
	 *
	 */
	public void startGame()
	{
		view.getMainFrame().setVisible(true);
		long delta = 0l;
		while (!isOver())
		{
			while (isRunning())
			{
				if (isNextLevel())
					setRunning(false);
				if (isPause())
					setRunning(false);
				if (isOver())
					break;
				long lastTime = System.nanoTime();

				if (model.update())
					view.render(getModel().getGameObjectList(), getModel().getPlayer());

				delta = System.nanoTime() - lastTime;
				if (delta < Constants.GAME_LOOP_TIME)
					FileManager.sleep((int) (Constants.GAME_LOOP_TIME - delta) / 1000000);
			}
			if (isNextLevel())
			{
				showNextLevelDialog();
			}
			if (isPause())
			{
				getView().getLayeredPanel().getGlassPanel().startPause();
				getModel().getPlayer().startPause();
				setPause(false);
			}
		}
		endOfGame();
	}

	/**
	 * When level is finished, this function instructs View to render special
	 * dim Jpanel with info about new level.
	 */
	private void showNextLevelDialog()
	{
		getView().getLayeredPanel().getGlassPanel().startNextLevelDialog(getModel().getCurrentLevel());
		FileManager.sleep(3000);
		getView().getLayeredPanel().getGlassPanel().stopPause();
		setNextLevel(false);
		setRunning(true);

	}

	/**
	 * Function called at the end of the game. Instructs View to show 'goodbye'
	 * view and best results.
	 */
	public void endOfGame()
	{
		getView().getLayeredPanel().getGlassPanel().goodbye();
		FileManager.sleep(3000);
		getView().getResultsView().endOfGame(getModel().getResultsModel().getPlayersList());
	}
}