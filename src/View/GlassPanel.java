package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Utils.Constants;

/**
 *
 * Tranlucent JPanel for every kind of pasue menus.
 *
 * @author Antek
 *
 */
public class GlassPanel extends JPanel
{
	private boolean translated;
	private JLabel text;

	public JLabel getText()
	{
		return text;
	}

	public void setText(JLabel text)
	{
		this.text = text;
	}

	public boolean isTranslated()
	{
		return translated;
	}

	public void setTranslated(boolean translated)
	{
		this.translated = translated;
	}

	public GlassPanel()
	{
		setTranslated(true);
		setLayout(new BorderLayout());
		text = new JLabel();

		Font f = new Font("monospaced", Font.BOLD, 24);
		text.setForeground(Color.white);
		text.setFont(f);
		add(text, BorderLayout.CENTER);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setText(
				"<html><center>***GAME PAUSED***<br><br>Hit 'P' to resume,'E' for Editor or 'H' to see best results.</html>");
		text.setVisible(false);

		setBackground(new Color(10, 10, 10, 180));

		setOpaque(false);
		setLocation(0, -1);
		setSize(new Dimension(Constants.STANDARD_ARENA_WIDTH,
				Constants.STANDARD_ARENA_HEIGHT + Constants.STANDARD_PLAYER_AREA_HEIGHT));
		setMaximumSize(new Dimension(Constants.STANDARD_ARENA_WIDTH,
				Constants.STANDARD_ARENA_HEIGHT + Constants.STANDARD_PLAYER_AREA_HEIGHT));
	}

	/**
	 * Function called when pause is about to start.
	 */
	public void startPause()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setOpaque(true);
				text.setText(
						"<html><center>***GAME PAUSED***<br><br>Hit 'P' to resume or 'Q' to go to the menu.</html>");
				text.setVisible(true);
				setTranslated(false);
			}
		});

	}

	/**
	 * Function called at the end of the pause. By setting opawue to false,
	 * LayeredPanel is forced to refresh itself.
	 */
	public void stopPause()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				text.setVisible(false);
				setOpaque(false);
				setTranslated(true);
			}
		});

	}

	/**
	 * Invoking pause screen before new level.
	 * 
	 * @param level
	 *            - number of level to wrote on glassPanel.
	 */
	public void startNextLevelDialog(int level)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setOpaque(true);
				if (level == 0)
					text.setText("No levels found. Randomizing bricks...");
				else if (level == 1)
					text.setText("LEVEL CLEARED! No more levels. Restarting from Level 1...");
				else
					text.setText("LEVEL CLEARED! Next level: " + level);
				text.setVisible(true);

				setTranslated(false);
			}
		});

	}

	/**
	 * Invoking glass panel at the end of the game.
	 */
	public void goodbye()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				setOpaque(true);
				text.setText("GAME OVER");
				text.setVisible(true);

				setTranslated(false);
			}
		});
	}

	@Override
	public void paintComponent(Graphics g)
	{
		LayeredPanel lp = (LayeredPanel) getParent();
		setSize(lp.getSize());

		if (isTranslated() == true)
			g.translate(-(lp.getWidth() - (int) lp.getLayeredPaneArea().getWidth()) / 2,
					-(lp.getHeight() - (int) lp.getLayeredPaneArea().getHeight()) / 2);

		super.paintComponent(g);
	}
}
