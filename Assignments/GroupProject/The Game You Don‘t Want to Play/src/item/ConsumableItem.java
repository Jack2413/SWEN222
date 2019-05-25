package item;

import character.Player;
import commonPackage.usefor.test.MockConsumableItem;
import commonPackage.usefor.test.RealPlayer;

/**
 * This abstract class is used to represent a kind of items. If consumable item
 * was used, then it was just simply disappear.
 *
 */
public abstract class ConsumableItem implements Item,MockConsumableItem {

	public abstract void use(RealPlayer player);

}
