package View;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controller.InputController;
import javafx.scene.layout.VBox;

/**
 * Class representing menu - first GUI to see after starting the app.
 *
 */
public class MenuView
{
	private InputController inputController;
	private JFrame menuFrame;
	private JPanel menuPanel;
	private JPanel aboutPanel;
	private Font buttonFont = new Font("Arial", Font.BOLD, 35);

	public InputController getInputController()
	{
		return inputController;
	}

	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
	}

	public JFrame getMenuFrame()
	{
		return menuFrame;
	}

	public void setMenuFrame(JFrame menuFrame)
	{
		this.menuFrame = menuFrame;
	}

	public JPanel getMenuPanel()
	{
		return menuPanel;
	}

	public void setMenuPanel(JPanel menuPanel)
	{
		this.menuPanel = menuPanel;
	}

	public JPanel getAboutPanel()
	{
		return aboutPanel;
	}

	public void setAboutPanel(JPanel aboutPanel)
	{
		this.aboutPanel = aboutPanel;
	}

	public MenuView()
	{
		setMenuFrame(new JFrame("Lets play the game!  o.o"));
		getMenuFrame().setSize(500, 700);
		getMenuFrame().setLocationRelativeTo(null);
		getMenuFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getMenuFrame().setLayout(new GridLayout(0, 1));

		setMenuPanel(new JPanel()
		{
			{
				setLayout(new GridLayout(0, 1));
				try
				{
					add(new JLabel(new ImageIcon((ImageIO.read(new File("arkanoid_logo3.png"))
							.getScaledInstance(500, 100, Image.SCALE_SMOOTH)))));
				}
				catch (IOException io)
				{
					System.out.println("cant load the image");
				}

				add(new JButton("Play")
				{
					{
						addActionListener(ae ->
						{
							new Thread()
							{
								public void run()
								{
									getInputController().menuPlay();
								}
							}.start();
						});
						setFont(buttonFont);

					}
				});
				add(new JButton("Instructions")
				{
					{
						addActionListener(ae ->
						{
							JFrame jf = new JFrame();

							jf.setSize(1000, 700);
							jf.setLocationRelativeTo(null);// size must be set
															// before this line
							jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							JPanel jp = new JPanel();
							jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
							JTextArea ta = new JTextArea();
							ta.setText(
									"Game has the same rules as the classic arkanoid game - prevent balls from leaving game area. "
											+ "Clear the level by destroying bricks - each color indicates different amount of brick's HPs. "
											+ "When all the bricks are destroyed, the game moves on to the next level. You can create more levels in Editor Mode! "
											+ '\n' + '\n'
											+ "Without any upgrades, each time the ball hits the brick its HP is being decremented. When HP becomes zero, brick is destroyed and sometimes upgrade is freed."
											+ " " + '\n' + '\n'
											+ "Use LEFT and RIGHT arrows to move the racket. press UP arrow to shoot the new ball. "
											+ "Catch falling squares to obtain special upgrades! Each color contains different bonus. "
											+ "Press SPACE to fire missiles if you possess some. " + '\n'
											+ '\n'
											+ "Hit P to pasue the game, E to enter the editor and H to see the best results."
											+ '\n' + '\n' + "Good luck!");
							ta.setLineWrap(true);
							ta.setAlignmentX(Component.CENTER_ALIGNMENT);
							ta.setWrapStyleWord(true);
							ta.setFont(new Font("Arial", Font.PLAIN, 25));
							JScrollPane js = new JScrollPane(ta);
							js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
							js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

							ta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

							jp.add(js);
							jf.add(jp);
							jf.setVisible(true);
						});
						setFont(buttonFont);
					}
				});
				add(new JButton("Editor")
				{
					{
						addActionListener(ae ->
						{
							getInputController().showEditor();
						});
						setFont(buttonFont);
					}
				});

				add(new JButton("Hall Of Fame")
				{
					{
						addActionListener(ae ->
						{
							getInputController().showHallOfFame();
						});
						setFont(buttonFont);
					}

				});
				add(new JButton("About")
				{
					{
						addActionListener(ae ->
						{
							JFrame jf = new JFrame();

							jf.setSize(500, 550);
							jf.setLocationRelativeTo(null);// size must be set
															// before this line
							jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							JPanel jp = new JPanel();
							jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
							JTextArea ta = new JTextArea();
							ta.setText("Hello there!" + '\n' + '\n'
									+ "Welcome to a simple Java app which imitates the classic game 'Arkanoid'. "
									+ '\n'
									+ "Game was created as a student project in the second year of studies at the "
									+ "Warsaw Univeristy of Technology, for learning purposes only." + '\n'
									+ '\n' + "A. Ró¿añski. " + '\n' + "(ant.rozanski@gmail.com).");
							ta.setLineWrap(true);
							ta.setAlignmentX(Component.CENTER_ALIGNMENT);
							ta.setWrapStyleWord(true);
							ta.setFont(new Font("Arial", Font.PLAIN, 25));
							JScrollPane js = new JScrollPane(ta);
							js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
							js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

							ta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
							try
							{
								jp.add(new JLabel(new ImageIcon((ImageIO.read(new File("arkanoid_logo3.png"))
										.getScaledInstance(500, 100, Image.SCALE_SMOOTH)))));
							}
							catch (IOException ioex)
							{
							}
							jp.add(js);
							jf.add(jp);
							jf.setVisible(true);
						});
						setFont(buttonFont);
					}
				});

			}
		});

		getMenuFrame().add(getMenuPanel());
	}
}
