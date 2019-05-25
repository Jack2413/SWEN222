package item;

import character.Player;
import commonPackage.usefor.test.RealPlayer;

/***
 * This class is represent the wing. it will appear somewhere in the game map.
 * Meantime, the player can pick up the item The player can put on or take off
 * the wing. Once the player wear the wing, the speed of movement will be
 * changed
 */
public class Wing extends WearableItem {
	private int increaseSpeed = 1;
	private int increasedDamage = 2;
	private int increasedDefense = 3;

	private boolean isOn = false;
	private String name = "76";
	private int x, y;
	private int cost = 1000;
	private int level = 0;

	/**
	 * level could be 0,1,2 when level is 0, the name and defence keep as default
	 * Otherwise, they will be changed in setAttribute method
	 * 
	 * @param x
	 * @param y
	 * @param level
	 */
	public Wing(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		setAttribute(level);
	}
	
	public Wing(int level) {
		this.level = level;
		setAttribute(level);
	}

	/**
	 * This method is used to put on the wing, then player's attributes would be
	 * changed
	 *
	 * @param player
	 **/
	@Override
	public void putOn(RealPlayer player) {
		if (!isOn) {

			player.setSpeed(player.getSpeed() + increaseSpeed);
			player.setDamage(player.getDamage() + increasedDamage);
			player.setDefence(player.getDefence() + increasedDefense);
			isOn = true;
		}

	}

	/**
	 * This method is used to take off the wing, then player's attributes would be
	 * changed
	 *
	 * @parmater player
	 **/
	@Override
	public void takeOff(RealPlayer player) {
		if (isOn) {
			player.setSpeed(player.getSpeed() - increaseSpeed);
			player.setDamage(player.getDamage() - increasedDamage);
			player.setDefence(player.getDefence() - increasedDefense);

			isOn = false;
		}

	}

	/**
	 * level could be 0,1,2 when level is 0, the name and defence keep as default
	 * Otherwise, they will be changed in setAttribute method
	 * 
	 * @param level
	 */
	public void setAttribute(int level) {
		switch (level) {
		case 1:
			increaseSpeed = 3;
			increasedDamage = 5;
			increasedDefense = 10;

			name = "77";
			break;
		case 2:
			increaseSpeed = 6;
			increasedDamage = 26;
			increasedDefense = 20;

			name = "78";
			break;
		}

	}

	/**
	 * all getter and setter
	 */
	public int getIncreasedDamage() {
		return increasedDamage;
	}

	public int getIncreasedDefense() {
		return increasedDefense;
	}

	public int getIncreaseSpeed() {
		return increaseSpeed;
	}

	@Override
	public void fix(int amount) {

	}

	@Override
	public String toString() {
		return name;
	}

	public boolean getIsOn() {
		return isOn;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	public String getName() {
		return name;
	}

}
