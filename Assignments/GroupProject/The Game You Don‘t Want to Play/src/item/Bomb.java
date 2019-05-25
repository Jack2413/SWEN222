package item;

import character.Player;
import commonPackage.usefor.test.RealPlayer;

/**
 * This class is used to represent bomb. It is used to destroy the breakable
 * wall Created by minpingyang on 16/09/17.
 */
public class Bomb extends ConsumableItem {
	private int x, y;
	private String name = "43";

	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This method is used to check if the player on the item position
	 **/
	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public void use(RealPlayer player) {
	}

	/**
	 * This method is used to save/load function
	 */
	@Override
	public String toString() {
		return name;
	}

	public Bomb() {
	}

	@Override
	public String getName() {
		return name;
	}
}
