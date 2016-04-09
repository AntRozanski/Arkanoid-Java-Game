package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import Controller.Controller;
import Model.GameObject;
import Model.Player;
import Utils.Constants;

/**
 * JLayeredPane that contains all JPanels on which game is rendered. It has
 * mainPanel, where actual game is happening and also glassPanel, which is
 * visible during all kinds of pauses.
 *
 * @author Antek
 *
 */
public class LayeredPanel extends JLayeredPane {
	private MainPanel mainPanel;
	private GlassPanel glassPanel;
	private Dimension layeredPaneArea;
	private Controller controller;

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public GlassPanel getGlassPanel() {
		return glassPanel;
	}

	public void setGlassPanel(GlassPanel glassPanel) {
		this.glassPanel = glassPanel;
	}

	public Dimension getLayeredPaneArea() {
		return layeredPaneArea;
	}

	public void setLayeredPaneArea(Dimension layeredPaneArea) {
		this.layeredPaneArea = layeredPaneArea;
	}

	/**
	 * Constructor for LayeredPanel. Initializes all minor JPanels and adds
	 * KeyListeners.
	 */
	public LayeredPanel() {
		setMainPanel(new MainPanel());
		setGlassPanel(new GlassPanel());
		setLayeredPaneArea(new Dimension(Constants.STANDARD_ARENA_WIDTH,
				Constants.STANDARD_PLAYER_AREA_HEIGHT + Constants.STANDARD_ARENA_HEIGHT));
		add(getMainPanel(), new Integer(0));

		add(getGlassPanel(), new Integer(1));

		setFocusable(true);
		requestFocusInWindow();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				getController().getInputController().handleInput(ke);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				getController().getInputController().keyReleased(arg0);
			}
		});
	}

	/**
	 * Sets mainPanel's fields with new values and call repaint() function.
	 *
	 * @param ListOfObjects
	 * @param player
	 */
	public void render(ArrayList<GameObject> ListOfObjects, Player player) {
		getMainPanel().setListOfObjectsToRender(ListOfObjects);
		getMainPanel().setPlayer(player);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				repaint();

			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.translate((getWidth() - (int) getLayeredPaneArea().getWidth()) / 2,
				(getHeight() - (int) getLayeredPaneArea().getHeight()) / 2);

		super.paintComponent(g);

	}
}
