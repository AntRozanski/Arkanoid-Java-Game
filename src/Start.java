import Controller.Controller;
import Model.Model;
import View.View;

/**
 *
 *
 * Starting class of the game. Passes Model and View to the Controller and
 * starts the whole game using Controller's function startGame().
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
		
		mainController.showMenu();
	}

}
