package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.InputController;

/**
 * Part of ResultsView. Contains text field, where user can enter his name.
 * 
 * @author Antek
 *
 */
public class NameWritingPanel extends JPanel
{
	private InputController inputController;
	private JTextField textField;
	private JButton button;

	public NameWritingPanel()
	{

		setLayout(new BorderLayout());
		Font betterFont = new Font("monospaced", Font.BOLD, 18);
		setTextField(new JTextField(10));
		getTextField().setFont(betterFont);
		getTextField().setText("Your name");
		getTextField().selectAll();

		setButton(new JButton("OK!"));
		getButton().setEnabled(true);

		getButton().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				getInputController().saveResult(getTextField().getText());

			}
		});

		add(getTextField(), BorderLayout.CENTER);

		add(getButton(), BorderLayout.EAST);
		setBorder(BorderFactory.createTitledBorder("Enter your pathetic name below:"));

	}

	public InputController getInputController()
	{
		return inputController;
	}

	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
	}

	public JTextField getTextField()
	{
		return textField;
	}

	public void setTextField(JTextField textField)
	{
		this.textField = textField;
	}

	public JButton getButton()
	{
		return button;
	}

	public void setButton(JButton button)
	{
		this.button = button;
	}

}
