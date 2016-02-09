package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.PlayerData;
import Model.StillObject;

/**
 * 
 * Auxiliary class to take care of all actions related to saving/loading to/from
 * a file. It has additionaly sleep() method, which is basically System.sleep()
 * method, placed here only for convienience and clarity of the code.
 * 
 * @author Antek
 *
 */
public abstract class FileManager
{

	/**
	 * Method that saves given PayerData instance to the Results.ser file. If
	 * there is no file with that name, new is created and result is savedusing
	 * AppendingObjectOutputStream.
	 * 
	 * @param pd
	 */
	public static void saveResult(PlayerData pd)
	{
		File f = new File("Results.ser");
		try
		{
			if (f.exists() && !f.isDirectory())
			{
				AppendingObjectOutputStream ous = new AppendingObjectOutputStream(
						new FileOutputStream("Results.ser", true));

				ous.writeObject(pd);
				ous.close();
			}
			else
			{
				ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("Results.ser"));
				ous.writeObject(pd);
				ous.close();
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Function loads all results stored in Results.ser file into given list.
	 * 
	 * @param playersList
	 *            - list to which resluts should be loaded.
	 */
	public static void loadResults(ArrayList<PlayerData> playersList)
	{
		try
		{
			FileInputStream fis = new FileInputStream("Results.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			while (fis.available() > 0)
			{
				PlayerData playerData = (PlayerData) ois.readObject();
				playersList.add(playerData);
			}

			ois.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Brak folderu!");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Function used to find out what is the biggest available level  in saved
	 * files.
	 * 
	 * @return
	 */
	public static int readMaxLevel()
	{
		int level = 0;
		File f;
		do
		{
			level++;
			String s = "Level_nr_" + level + ".ser";
			f = new File(s);
		} while (f.exists() && !f.isDirectory());
		return level - 1;
	}

	/**
	 * If there is a file for given level, this method will load level from it.
	 * 
	 * @param levelToLoad
	 *            - level to load... %)
	 * @return list of stilObjects for given level, if file exists or null
	 *         otherwise.
	 */
	public static ArrayList<StillObject> loadLevel(int levelToLoad)
	{
		ArrayList<StillObject> stillList = new ArrayList<StillObject>();
		File f = new File("Level_nr_" + levelToLoad + ".ser");
		if (f.exists() && !f.isDirectory())
		{
			try
			{
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				stillList = (ArrayList<StillObject>) ois.readObject();
				ois.close();
			}
			catch (FileNotFoundException ex)
			{
				System.out.println("Brak folderu!");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			return stillList;
		}
		else
			return null;
	}

	/**
	 * Saves level into new file.
	 * 
	 * @param stillObjectList
	 *            - list of level objects
	 * @param number
	 *            - number of level
	 */
	public static void saveLevel(ArrayList<StillObject> stillObjectList, int number)
	{
		try
		{
			ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("Level_nr_" + number + ".ser"));
			ous.writeObject(stillObjectList);
			ous.close();

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Method called whenever our game should wait a bit. Basically it is
	 * Thread.sleep() method, but because of lack of try - catch blocks it takes
	 * only one line :).
	 * 
	 * @param ms
	 *            number of miliseconds to sleep.
	 */
	public static void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
