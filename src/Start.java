import Controller.Controller;
import Model.Model;
import View.View;

/**
 * 
 * 
 * Starting class of the game, for passing Model and View to the controller and
 * starting the game, using Controllers function startGame().
 * 
 * @author Antoni Ró¿añski
 */
public class Start
{

	public static void main(String[] args)
	{

		View view = new View();
		Model model = new Model();

		Controller mainController = new Controller(view, model);

		view.setControllerAndPassIt(mainController);
		model.setController(mainController);

		mainController.startGame();

	}

}
