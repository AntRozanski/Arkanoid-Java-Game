package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ResultsPanel extends JPanel
{
	private JTextArea textArea;
	
	public ResultsPanel()
	{
		setLayout(new BorderLayout());
		
		setTextArea(new JTextArea(6,10));
		Font betterFont = new Font("monospaced", Font.BOLD, 16);
		//getTextArea().setText("No results so far.");
		getTextArea().setFont(betterFont);
		getTextArea().setLineWrap(true);
		getTextArea().setEditable(false);
		setPreferredSize(new Dimension (0,1000));
		JScrollPane scrollPane = new JScrollPane(getTextArea());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//add(getLabel(),BorderLayout.NORTH);TODO
		add(scrollPane,BorderLayout.CENTER);
		
		scrollPane.setBorder(BorderFactory.createTitledBorder("Hall of Fame!"));
	}
	
 
	public JTextArea getTextArea()
	{
		return textArea;
	}
	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
}
