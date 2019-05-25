package item;

import character.Player;
import commonPackage.usefor.test.RealPlayer;

/***
 * This class is used to represent the a lucky coin item when the fate coin is
 * used, the player's health is randomly either increased or decreased.
 *
 * @author yangminp
 */
public class FateCoin extends ConsumableItem {
	private int x, y;
	private int randomNumber = 0;
	private int amount = 100;
	private String name = "48";
	private int cost = 80;

	public FateCoin(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This method is used to use the fate coin, once the player want to use
	 **/
	@Override
	public void use(RealPlayer player) {
		randomNumber = (int) (10 * Math.random());
		if (randomNumber < 5) {
			player.setHealth(player.getHealth() - amount);
		} else {
			player.setHealth(player.getHealth() + amount);
		}
	}

	/**
	 * This method is used to check if the player on the item position
	 **/
	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getRandomNumber() {
		return randomNumber;
	}

	public int getAmount() {
		return amount;
	}
}
