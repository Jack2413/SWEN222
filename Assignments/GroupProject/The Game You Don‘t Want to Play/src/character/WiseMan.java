package character;

import commonPackage.usefor.test.MockWiseMan;
import item.ConsumableItem;
import item.Item;
import item.WearableItem;
import main.InvalidMove;

/**
 * A wise old man that teaches our hero lessons as well as gives him good stuff.
 * @author stella
 *
 */
public class WiseMan implements MockWiseMan{
	private Item item;
	
	public WiseMan(Item item) {
		this.item = item;
	}
	
	public void give(Player player) throws InvalidMove {
		if(item instanceof WearableItem) player.equip((WearableItem)item);
		else if(item instanceof ConsumableItem) player.addItem((ConsumableItem)item);
	}
}
