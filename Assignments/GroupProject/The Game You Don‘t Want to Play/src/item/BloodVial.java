package item;

import character.Player;
import commonPackage.usefor.test.RealPlayer;

/**
 * This class is used to represent the boold vial. It will randomly appear in
 * the map, then player can pick it up into bag.
 *
 * @author yangminp
 */
public class BloodVial extends ConsumableItem {

	private int x, y;
	private int amount = 250; //
	private String type;// small ,big
	private String name = "41";

	public BloodVial(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type; // either small or big
		if (type.equals("big")) {
			this.name = "40";
			this.amount = 500;
		}
	}

	// the constructor without passing the coordinator of the blood vial
	public BloodVial(String type) {
		this.type = type; // either small or big
		if (type.equals("big")) {
			this.name = "40";
			this.amount = 500;
		}
	}

	/**
	 * This method is used to change the player's attributes, when the player use
	 * the Blood Vial
	 * 
	 * @param player
	 */
	@Override
	public void use(RealPlayer player) {
		if (type == "big") {
			player.setHealth(player.getHealth() + amount);
		} else {
			player.setHealth(player.getHealth() + amount);
		}

		this.amount = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getAmount() {
		return amount;
	}
}
