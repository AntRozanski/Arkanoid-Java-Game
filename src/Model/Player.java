package Model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import Model.Upgrade.TemporaryUpgrade;
import Model.Upgrade.Upgrade;

/**
 *
 * Class that represent player. It stores health (number of balls that can be
 * destroyed before game is over), points and upgrades in the list, if there
 * any.
 *
 * @author Antek
 *
 */
public class Player {
	private PropertyChangeSupport pcs;
	private int health;
	private int points;
	private ArrayList<TemporaryUpgrade> ListOfUpgrades;
	private long upgradeOverTime;
	private long pauseStartTime;

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	public ArrayList<TemporaryUpgrade> getListOfUpgrades() {
		return ListOfUpgrades;
	}

	public void setListOfUpgrades(ArrayList<TemporaryUpgrade> listOfUpgrades) {
		ListOfUpgrades = listOfUpgrades;
	}

	/**
	 * @return the pcs
	 */
	public PropertyChangeSupport getPcs() {
		return pcs;
	}

	/**
	 * @param pcs
	 *            the pcs to set
	 */
	public void setPcs(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}

	public long getPauseStartTime() {
		return pauseStartTime;
	}

	public void setPauseStartTime(long pauseStartTime) {
		this.pauseStartTime = pauseStartTime;
	}

	public long getUpgradeOverTime() {
		return upgradeOverTime;
	}

	public void setUpgradeOverTime(long upgradeOverTime) {
		this.upgradeOverTime = upgradeOverTime;
	}

	public Player(int h) {
		this.health = h;
		this.points = 0;
		setListOfUpgrades(new ArrayList<TemporaryUpgrade>());
		setUpgradeOverTime(0);
	}

	/**
	 * Decreases health of Player by one. Checks if player is dead.
	 *
	 * @return true, if helath == 0
	 */
	public boolean decreaseHP() {
		setHealth(getHealth() - 1);
		if (getHealth() == 0)
			return true;
		return false;
	}

	/**
	 * Add given points to the point pool.
	 *
	 * @param num
	 *            - points to add
	 */
	public void addPoints(int num) {
		setPoints(getPoints() + num);
	}

	/**
	 * Ads given upgrade to the list. If there is already an instance of the
	 * upgrade of the same class as given upgrade, old instance is replaced.
	 *
	 * It also trigger start() method on the upgrade and PropertyChangeSupport
	 * to notify its listeners about new upgrade in the game.
	 *
	 * @param upgr
	 */
	public void addUpgrade(Upgrade upgr) {

		if ((upgr instanceof TemporaryUpgrade)) {

			int i = 0;
			if (!getListOfUpgrades().isEmpty()) {
				for (i = 0; i < getListOfUpgrades().size(); i++) {
					if (getListOfUpgrades().get(i).inTypeOf(upgr)) {
						getListOfUpgrades().set(i, (TemporaryUpgrade) upgr);// replace
																			// the
																			// upgrade
																			// with
																			// the
																			// new
																			// one
																			// of
																			// his
																			// kind
						break;
					}
				}
			}
			if (i == getListOfUpgrades().size()) // if there is no upgr of that
													// type
				getListOfUpgrades().add((TemporaryUpgrade) upgr);

			setUpgradeOverTime(System.currentTimeMillis() + ((TemporaryUpgrade) upgr).getDuration());
			getPcs().firePropertyChange(upgr.getName(), false, true);
			((TemporaryUpgrade) upgr).start();
		}

		getPcs().firePropertyChange(upgr.getName(), false, true);

	}

	/**
	 * Checks if some of the upgrades are not dead and therefore shouldn't be
	 * removed from the list. Also ensures if there is any active temporary
	 * upgrades, to refresh view.
	 * 
	 * @return true, if there is any temporaryUpgrade in the list.
	 */
	public boolean updateUpgrades() {
		if (!getListOfUpgrades().isEmpty()) {
			int i;
			for (i = 0; i < getListOfUpgrades().size(); i++) {
				TemporaryUpgrade upgr = getListOfUpgrades().get(i);
				if (upgr.isOver()) {
					getPcs().firePropertyChange(upgr.getName(), true, false);
					getListOfUpgrades().remove(i);
					i--;
				}
			}
			if (i != 0)
				return true;
		}
		return false;
	}

	/**
	 * Function called when new level is being loaded.
	 */
	public void resetUpgrades() {
		for (Upgrade upgr : getListOfUpgrades())
			getPcs().firePropertyChange(upgr.getName(), true, false);

		getListOfUpgrades().clear();
		setUpgradeOverTime(0);

	}

	public void startPause() {
		if (!getListOfUpgrades().isEmpty())
			for (Upgrade upgr : getListOfUpgrades()) {
				if (upgr instanceof TemporaryUpgrade) {
					((TemporaryUpgrade) upgr).startPause();
					setPauseStartTime(System.currentTimeMillis());
				}
			}
	}

	public void stopPause() {
		if (!getListOfUpgrades().isEmpty()) {
			for (Upgrade upgr : getListOfUpgrades()) {
				if (upgr instanceof TemporaryUpgrade)
					((TemporaryUpgrade) upgr).stopPause();
			}
			setUpgradeOverTime(getUpgradeOverTime() + System.currentTimeMillis() - getPauseStartTime());
		}
		setPauseStartTime(0);

	}
	/**
	 * Used by PlayerPanel to show Upgrade time left. 
	 */
	public String getTimeAsString() {
		long someTime;
		if (getPauseStartTime() == 0) // not in pause
		{
			someTime = getUpgradeOverTime() - System.currentTimeMillis();
		} else {
			someTime = getUpgradeOverTime() - getPauseStartTime();
		}
		if (someTime < 0)
			return "";
		else
			return ("Upgrade time left: " + someTime / 1000 + "." + someTime / 100);
	}

}