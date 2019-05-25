package character;

import java.util.HashMap;
import java.util.Map;

import item.*;
import main.InvalidMove;

/**
 * A shop that sells a variety of items including both equipments and consumables.
 * @author stella
 *
 */
public class Shop {
	//a list of items that the shop sells, with their corresponding prices
	private Map<Item, Integer> items;

	public Shop() {
		initialize();
	}
	//================================================= initialize ================================================================
	/**
	 * generates a random item with random price for the shop to sell, each shop will sell three items
	 */
	public void initialize() {
		items = new HashMap<>();
		for(int i = 0; i < 3;i++){
			int item = (int) (Math.random()*9);
			int price = (int) (Math.random()*60) + 10;
			if(item == 0) {
				items.put(new Key("gold"), (int) (price*1.5));
			}else if(item == 1) {
				items.put(new Key("silver"), (int) (price*1.2));
			}else if(item == 2) {
				items.put(new Key("purple"), (int) (price*2));
			}else if(item == 3) {
				items.put(new Key("cyan"), (int) (price*1.8));
			}else if(item == 4) {
				items.put(new Key("bronze"), price);
			}else if(item == 5) {
				items.put(new BloodVial("small"), (int) (price*1.2));
			}else if(item == 6){
				items.put(new BloodVial("big"), (int) (price*1.5));
			}else {
				items.put(new Bomb(), (int) (price*1.5));
			}
		}
	}
	//================================================= use method ================================================================
	/**
	 * player buys an item from shop, throws an exception if doesn't have enough money.
	 * @param item
	 * @param player
	 * @throws InvalidMove
	 */
	public void buyItem(Item item, Player player) throws InvalidMove{
		if(!items.containsKey(item))
			throw new InvalidMove("No such item in shop");
		
		int price = items.get(item);
		System.out.println("price: "+price);
		if(player.getGold() >= price) {
			//player buys the item, deduct money and equip/add item to inventory.
			player.setGold(player.getGold() - price);
			if(item instanceof ConsumableItem) {
				player.addItem((ConsumableItem) item);
			}else if(item instanceof WearableItem) {
				player.equip((WearableItem) item);
			}

			//remove this item from the shop
			items.remove(item);
		}else {
			throw new InvalidMove("Cannot buy this item, you don't have enough gold.");
		}
	}
	
	//================================================= get method ================================================================
	public Map<Item, Integer> showShop(){return this.items;}
	public Map<Item, Integer> getItems() {
		return items;
	}
}
