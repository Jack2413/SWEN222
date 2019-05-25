package item;


import character.Player;
import commonPackage.usefor.test.MockWearableItem;
import commonPackage.usefor.test.RealPlayer;

public abstract class WearableItem implements Item,MockWearableItem {

	public abstract void putOn(RealPlayer player);
	public abstract void takeOff(RealPlayer player);
	public abstract void fix(int amount);
	public abstract int getCost();
	
}
