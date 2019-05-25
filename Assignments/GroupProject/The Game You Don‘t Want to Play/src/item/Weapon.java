package item;

import Board.Level;
import character.Monster;
import character.Player;
import commonPackage.usefor.test.RealPlayer;

/**
 * This class is used to represent the armor equipment which the player can put
 * on to increase defence of player
 *
 * @author minpingyang
 */
public class Weapon extends WearableItem {
	private int attack = 2;
	private int x, y;
	private double factor = 0.66;
	private boolean isOn = false;
	private String name = "73";
	public static final int attack4= 30,attack7=53;
	
	private int cost = 600;
	private int level = 0;

	boolean getIsoN() {
		return isOn;
	}

	/**
	 * level could be 0,1,2 when level is 0, the name and defence keep as default
	 * Otherwise, they will be changed in setAttribute method
	 **/
	public Weapon(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		setAttribute(level);
	}
	
	public Weapon(int level) {
		this.level = level;
		setAttribute(level);
	}

	/**
	 * level could be 0,1,2 when level is 0, the name and defence keep as default
	 * Otherwise, they will be changed in setAttribute method
	 **/
	public void setAttribute(int level) {
		switch (level) {
		case 1:
			attack = (int) ( attack4* factor);
			name = "74";
			break;
		case 2:
			attack = (int) (attack7* factor);
			name = "75";
			break;
		}

	}

	/**
	 * This method is used to put on the weapon, then player's attributes would be
	 * changed
	 * 
	 * @param player
	 **/
	@Override
	public void putOn(RealPlayer player) {
		if (!isOn) {
			player.setDamage(player.getDamage() + attack);
			isOn = true;
		}

	}

	/**
	 * This method is used to take off the weapon, then player's attributes would be
	 * changed
	 *
	 **/
	@Override
	public void takeOff(RealPlayer player) {
		if (isOn) {
			player.setDamage(player.getDamage() - attack);
			isOn = false;
		}
	}

	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getAttack() {
		return attack;
	}

	@Override
	public void fix(int amount) {

	}
}
