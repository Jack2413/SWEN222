package item;

import commonPackage.usefor.test.RealPlayer;

/**
 * This class is used to represent the armor equipment which the player can put
 * on to increase defence of player
 *
 * @author minpingyang
 */
public class Armor extends WearableItem {
	private int defence = 2;
	private double factor= 2;
	private int x, y;
	private boolean isOn = false; // the flag is used to indicate if player has wear the Armor
	private String name = "70";
	public static final int defence4 = 8,defence7=14;
	private int level = 0;
	private int cost = 800;

	@Override
	public String toString() {
		return name;
	}

	/**
	 *
	 * level could be 0,1,2 when level is 0, the name and defence keep as default
	 * Otherwise, they will be changed in setAttribute method
	 * @param x, y, level
	 **/
	public Armor(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		setAttribute(level);
	}
	
	public Armor(int level) {
		this.level = level;
		setAttribute(level);
	}

	/**
	 * According to the level of armor, set up the armor code name and defence
	 *@param level
	 **/
	public void setAttribute(int level) {
		switch (level) {
		case 1:
			defence = (int)(defence4*factor);
			
			name = "71";
			break;
		case 2:
			defence = (int)(defence7*factor);
			name = "72";
			break;
		}

	}
	/**
	 * This method is used to put on the armor, then player's attributes would be changed
	 * @param player
	 * **/
	@Override
	public void putOn(RealPlayer player) {
		if (!isOn) {
			player.setDefence(player.getDefence() + defence);
			isOn = true;
		}

	}
	/**
	 * This method is used to take off the armor, then player's attributes would be changed
	 * @param player
	 * **/
	@Override
	public void takeOff(RealPlayer player) {
		if (isOn) {
			player.setDefence(player.getDefence() - defence);
			isOn = false;
		}

	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	public int getCost() {
		return cost;
	}

	boolean getIsOn() {
		return isOn;
	}

	public int getDefence() {
		return defence;
	}

	@Override
	public void fix(int amount) {

	}
}
