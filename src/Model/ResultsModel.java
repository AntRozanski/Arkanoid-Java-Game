package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import Utils.FileManager;

/**
 * Class for all activities related to result managment. It stores all
 * results in playerList and decides how ResultsView renders them.
 * 
 * @author Antek
 *
 */
public class ResultsModel
{
	private Player player;// player passed from Model while initializing
	private ArrayList<PlayerData> playersList = new ArrayList<PlayerData>();

	/**
	 * Constructor, where current Player is set, all results are being loaded to
	 * the playerList and sorted.
	 * 
	 * @param player
	 *            to set
	 */
	public ResultsModel(Player player)// , ResultsView view)
	{
		setPlayer(player);
		loadPlayers();
		Collections.sort(getPlayersList());
	}

	/**
	 * Function fired at the end of the game. It extracts all needed information
	 * from Player that just finished the game to create PlayerData instance.
	 * This object is added to list of results to show immidiately in
	 * ResultsView and also passed to FileManager to save.
	 * 
	 * @param name
	 */
	public void savePlayer(String name)
	{
		int points = getPlayer().getPoints();
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		PlayerData playerData = new PlayerData(name, points, date);
		getPlayersList().add(playerData);

		FileManager.saveResult(playerData);
		Collections.sort(getPlayersList());

	}

	/**
	 * This function loads, using FileManager, all results to earlier cleared
	 * list od results.
	 */
	public void loadPlayers()
	{
		getPlayersList().clear();
		FileManager.loadResults(getPlayersList());
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public ArrayList<PlayerData> getPlayersList()
	{
		return playersList;
	}

	public void setPlayersList(ArrayList<PlayerData> playersList)
	{
		this.playersList = playersList;
	}

}
