package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Controller.InputController;

public class ChoicePanel extends JPanel
{
//	private int maxLevel;
	private InputController inputController;
	private JComboBox levelList;
	private JComboBox brickTypesList;

	public ChoicePanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentY(Component.TOP_ALIGNMENT);

		setPreferredSize(new Dimension(170, 1000));

		// setBackground(Color.blue);

	}

	public void changeMaxLevel(int lev)
	{
		getLevelList().removeItemAt(lev-1);
		
		getLevelList().addItem("Level nr " + lev);
		
		getLevelList().addItem("Level nr " + (lev + 1) + "  (Create new level)");
		getLevelList().setSelectedIndex(lev);
	}

	public void createChoicePanel(int maxLevel)
	{
//		setMaxLevel(maxLevel);
		String[] levelStrings = new String[maxLevel + 1];
		for (int i = 0; i <= maxLevel; i++)
		{
			levelStrings[i] = new String("Level nr " + (i + 1));
			if (i == maxLevel)
				levelStrings[i] = new String("Level nr " + (i + 1) + "  (Create new level)");
		}
		setLevelList(new JComboBox(levelStrings));
		getLevelList().setSelectedIndex(maxLevel);
		getLevelList().setMaximumRowCount(10);
		getLevelList().addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				getInputController().changeLevel(getLevelList().getSelectedIndex());

			}
		});
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(getLevelList());
		box.setBorder(BorderFactory.createTitledBorder("Choose level here"));
		getLevelList().setMaximumSize(new Dimension(200, 30));
		getLevelList().setPreferredSize(getLevelList().getMaximumSize());

		add(Box.createRigidArea(new Dimension(0, 10)));
		add(box);

		setBrickTypesList(new JComboBox(new String[] { "Green brick (1 hp)", "Blue brick (2 hp)", "Purple brick (3 hp)",
				"Red brick (4 hp)", "Unmovable brick" }));
		getBrickTypesList().setSelectedIndex(0);
		getBrickTypesList().setMaximumRowCount(10);
		getBrickTypesList().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				getInputController().changeBrick(getBrickTypesList().getSelectedIndex());
			}
		});
		Box nextBox = new Box(BoxLayout.Y_AXIS);
		nextBox.add(getBrickTypesList());
		nextBox.setBorder(BorderFactory.createTitledBorder("Choose brick here"));
		getBrickTypesList().setMaximumSize(new Dimension(200, 30));
		getBrickTypesList().setPreferredSize(getLevelList().getMaximumSize());
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(nextBox);
		add(Box.createRigidArea(new Dimension(0, 40)));

		Box buttonArea = new Box(BoxLayout.X_AXIS);
		// buttonArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30,
		// 30));

		JButton clear = new JButton("Clear All");
		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getInputController().clearEditorList();
			}
		});
		buttonArea.add(clear);
		buttonArea.add(Box.createRigidArea(new Dimension(50, 0)));
		JButton fillAll = new JButton("Fill All");
		fillAll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getInputController().makeEditoListFull();

			}
		});
		buttonArea.add(fillAll);

		add(buttonArea);
		add(Box.createRigidArea(new Dimension(0, 40)));
		Box boxSave = new Box(BoxLayout.X_AXIS);
		JButton save = new JButton("Save your level!");
		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getInputController().saveLevel(getLevelList().getSelectedIndex() + 1);

			}
		});
		boxSave.add(Box.createHorizontalGlue());
		boxSave.add(save);

		add(boxSave);

	}

	public InputController getInputController()
	{
		return inputController;
	}

	public void setInputController(InputController inputController)
	{
		this.inputController = inputController;
	}

//	public int getMaxLevel()
//	{
//		return maxLevel;
//	}
//
//	public void setMaxLevel(int maxLevel)
//	{
//		this.maxLevel = maxLevel;
//	}

	public JComboBox getLevelList()
	{
		return levelList;
	}

	public void setLevelList(JComboBox levelList)
	{
		this.levelList = levelList;
	}

	public JComboBox getBrickTypesList()
	{
		return brickTypesList;
	}

	public void setBrickTypesList(JComboBox brickTypesList)
	{
		this.brickTypesList = brickTypesList;
	}

}
