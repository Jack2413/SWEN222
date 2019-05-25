package item;

import character.Player;
import commonPackage.usefor.test.RealPlayer;

/***
 * This class is used to present the key. The key has different type. The
 * specific type of key could be used to open the corresponding door
 * 
 * @author yangminp
 */

public class Key extends ConsumableItem {
	private int x, y;
	private String name = "30";
	private String color;

	public Key(int x, int y, String color) {
		this.x = x;
		this.y = y;
		this.color = color;
		generateName(color);
	}

	/**
	 * This method is used to that generate name,according to its color
	 */
	public void generateName(String color) {
		switch (color) {
		case "gold":
			name = "30";
			break;
		case "silver":
			name = "34";
			break;
		case "purple":
			name = "33";
			break;
		case "cyan":
			name = "31";
			break;
		case "bronze":
			name = "32";
			break;
		}
	}

	public Key(String color) {
		this.color = color;
		generateName(color);
	}

	@Override
	public void use(RealPlayer player) {
	}

	@Override
	public boolean on(int x, int y) {
		return this.x == x && this.y == y;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}
}
