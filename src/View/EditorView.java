package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Controller.InputController;
import Model.StillObject;
import Utils.Constants;

/**
 * Class for graphic part of editor.
 * 
 * @author Antek
 *
 */
public class EditorView

{
	private InputController inputController;
	private JFrame editorFrame;
	private GamePanel editorPanel;
	private ChoicePanel choicePanel;

	/**
	 * Creates the editorView. Adds mouse listeners.
	 */
	public EditorView()
	{

		setEditorFrame(new JFrame("Level Editor"));
		getEditorFrame().setSize(Constants.STANDARD_FRAME_WIDTH + 230, Constants.STANDARD_FRAME_HEIGHT);
		getEditorFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getEditorFrame().setLayout(new BoxLayout(getEditorFrame().getContentPane(), BoxLayout.X_AXIS));
		int cols = 10;
		int rows = 25;
		int brickWidth = (int) ((Constants.STANDARD_ARENA_WIDTH - 2 * Constants.STANDARD_WALL_WIDTH) / cols);
		int brickHeight = (int) brickWidth / 5;

		setEditorPanel(new GamePanel()
		{
			
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.white);
				for (int i = 0; i < cols + 1; i = i + 1)
				{
					g.drawLine(i * brickWidth + Constants.STANDARD_WALL_WIDTH, Constants.STANDARD_WALL_WIDTH,
							i * brickWidth + Constants.STANDARD_WALL_WIDTH,
							Constants.STANDARD_WALL_WIDTH + rows * brickHeight);
				}
				for (int i = 0; i < rows + 1; i = i + 1)
				{
					g.drawLine(Constants.STANDARD_WALL_WIDTH, Constants.STANDARD_WALL_WIDTH + i * brickHeight,
							Constants.STANDARD_ARENA_WIDTH - Constants.STANDARD_WALL_WIDTH,
							Constants.STANDARD_WALL_WIDTH + i * brickHeight);
				}
			}

		});
		getEditorPanel().setBackgroundColor(Color.black);
		Box editorBox = new Box(BoxLayout.Y_AXIS);
		editorBox.add(getEditorPanel());
		editorBox.setAlignmentY(Component.TOP_ALIGNMENT);
		editorBox.setBorder(BorderFactory.createTitledBorder("Design your level"));
		getEditorFrame().add(editorBox);
		
		getEditorPanel().setBorder(BorderFactory.createLineBorder(Color.black));
		getEditorPanel().setMaximumSize(getEditorPanel().getGamePanelArea());
		getEditorPanel().setAlignmentY(Component.TOP_ALIGNMENT);
		getEditorPanel().addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent ke)
			{
				getInputController().handleMouseInput(ke);

			}
		});
		getEditorFrame().add(Box.createHorizontalGlue());
		setChoicePanel(new ChoicePanel());
		
		getEditorFrame().add(getChoicePanel());
		
		getEditorFrame().add(Box.createHorizontalGlue());

	}

	public JFrame getEditorFrame()
	{
		return editorFrame;
	}

	public void setEditorFrame(JFrame editorFrame)
	{
		this.editorFrame = editorFrame;
	}

	public GamePanel getEditorPanel()
	{
		return editorPanel;
	}

	public void setEditorPanel(GamePanel editorPanel)
	{
		this.editorPanel = editorPanel;
	}

	public ChoicePanel getChoicePanel()
	{
		return choicePanel;
	}

	public void setChoicePanel(ChoicePanel choicePanel)
	{
		this.choicePanel = choicePanel;
	}

	public InputController getInputController()
	{
		return inputController;
	}

	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
		getChoicePanel().setInputController(inputController);
	}

	/**
	 * Draws grid and passed objects in editorView. 
	 * @param editorObjects - objects to rendes
	 */
	public void show(ArrayList<StillObject> editorObjects)
	{
		getEditorPanel().setListOfObjectsToRender(editorObjects);
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				getEditorPanel().repaint();
			}
		});
		
	}

}
