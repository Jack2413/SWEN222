package character;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import commonPackage.usefor.test.MockConsumableItem;
import commonPackage.usefor.test.MockWearableItem;
import item.Armor;
import item.BloodVial;
import item.Bomb;
import item.ConsumableItem;
import item.Item;
import item.Key;
import item.Weapon;
import item.WearableItem;
import item.Wing;
import main.InvalidMove;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {
	private Player player;
	private MockConsumableItem bomb = new Bomb();
	private MockConsumableItem key = new Key("yellow");
	private MockWearableItem armor = new Armor(1);
	private MockWearableItem weapon = new Weapon(1);
	private MockWearableItem wing = new Wing(1);
	private MockConsumableItem health = new BloodVial("small");
	private Monster monster = new Monster(1);
	
	@Test
	public void test01_playerPicksConsumables() {
		player = new Player();
		try {
			player.addItem((ConsumableItem) bomb);
			assertTrue(player.getInventory().contains(bomb));
		} catch (InvalidMove e) {
			fail("Should be able to pick up bomb.");
		}
	}
	
	@Test
	public void test02_playerPicksConsumbalesInventoryFull() {
		player = new Player();
		try {
			for(int i = 0; i < Player.INVENTORY_CAPACITY; i++) {
				player.addItem(new Bomb(0,0));
			}
			
			player.addItem((ConsumableItem) bomb);
			fail("Should not be able to pick up bomb since inventory is full.");
			assertFalse(player.getInventory().contains(bomb));
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test03_playerUseHealthPotion() throws InvalidMove {
		player = new Player();
		player.addItem((ConsumableItem) health);
		int healthBefore = player.getHealth();
		int amount = ((BloodVial) health).getAmount();
		player.useHealthPotion("small");
		assertEquals(healthBefore + amount, player.getHealth());
	}
	
	@Test
	public void test04_playerUseHealthPotionNotHave() {
		player = new Player();
		try {
			player.useHealthPotion("small");
			fail("Should not be able to use health potion.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test05_playerUseBomb() {
		player = new Player();
		try {
			player.addItem((ConsumableItem) bomb);
			player.useBomb();
			assertFalse(player.getInventory().contains(bomb));
			//ok
		} catch (InvalidMove e) {
			fail("fail to use bomb");
		}
	}
	
	@Test
	public void test06_playerUseBombNotHave() {
		player = new Player();
		try {
			player.useBomb();
			fail("Should not be able to use bomb.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test07_playerUseKey() {
		player = new Player();
		try {
			player.addItem((ConsumableItem) key);
			player.useKey("yellow");
			assertFalse(player.getInventory().contains(key));
			//ok
		} catch (InvalidMove e) {
			fail("fail to use key");
		}
	}
	
	@Test
	public void test08_playerUseKeyColorNotMatch() {
		player = new Player();
		try {
			player.addItem((ConsumableItem) key);
			player.useKey("cyan");
			fail("Should not be able to use key");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test09_playerEquipArmor() {
		player = new Player();
		int prevDefence = player.getDefence();
		int boostDefence = ((Armor) armor).getDefence();
		player.equip((WearableItem) armor);
		assertEquals(player.getDefence(), prevDefence + boostDefence);
		assertTrue(player.getCurrentArmor() == armor);
	}
	
	@Test
	public void test10_playerChangeArmor() {
		player = new Player();
		player.equip((WearableItem) armor);
		int prevDefence = player.getDefence();
		int boostDefence = ((Armor) armor).getDefence();
		int basicDefence = prevDefence - boostDefence;
		Armor newArmor = new Armor(2);
		int newBoostDefence = newArmor.getDefence();
		player.equip(newArmor);
		assertEquals(player.getDefence(), basicDefence + newBoostDefence);
		assertTrue(player.getCurrentArmor() == newArmor);
	}
	
	@Test
	public void test11_playerEquipWeapon() {
		player = new Player();
		int prevDamage = player.getDamage();
		int boostDamage = ((Weapon) weapon).getAttack();
		player.equip((WearableItem) weapon);
		assertEquals(player.getDamage(), prevDamage + boostDamage);
		assertTrue(player.getCurrentWeapon() == weapon);
	}
	
	@Test
	public void test12_playerChangeWeapon() {
		player = new Player();
		player.equip((WearableItem) weapon);
		int prevDamage = player.getDamage();
		int boostDamage = ((Weapon) weapon).getAttack();
		int basicDamage = prevDamage - boostDamage;
		Weapon newWeapon = new Weapon(2);
		int newBoostDamage = newWeapon.getAttack();
		player.equip(newWeapon);
		assertEquals(player.getDamage(), basicDamage + newBoostDamage);
		assertTrue(player.getCurrentWeapon() == newWeapon);
	}
	
	@Test
	public void test13_playerEquipWing() {
		player = new Player();
		int prevDamage = player.getDamage();
		int prevDefence = player.getDefence();
		int boostDamage = ((Wing) wing).getIncreasedDamage();
		int boostDefence = ((Wing) wing).getIncreasedDefense();
		player.equip((WearableItem) wing);
		assertEquals(player.getDamage(), prevDamage + boostDamage);
		assertEquals(player.getDefence(), prevDefence + boostDefence);
		assertTrue(player.getCurrentWing() == wing);
	}
	
	@Test
	public void test14_playerChangeWing() {
		player = new Player();
		player.equip((WearableItem) wing);
		int prevDamage = player.getDamage();
		int boostDamage = ((Wing) wing).getIncreasedDamage();
		int basicDamage = prevDamage - boostDamage;
		
		int prevDefence = player.getDefence();
		int boostDefence = ((Wing) wing).getIncreasedDefense();
		int basicDefence = prevDefence - boostDefence;
		
		Wing newWing = new Wing(2);
		int newBoostDamage = newWing.getIncreasedDamage();
		int newBoostDefence = newWing.getIncreasedDefense();
		player.equip(newWing);
		assertEquals(player.getDamage(), basicDamage + newBoostDamage);
		assertEquals(player.getDefence(), basicDefence + newBoostDefence);
		assertTrue(player.getCurrentWing() == newWing);
	}
	
	@Test
	public void test15_playerBuyItemOK() {
		player = new Player();
		player.setGold(1000);
		Shop shop = new Shop();
		Item item = null;
		
		for(Item i: shop.getItems().keySet()) {
			item = i;
			break;
		}
		
		try {
			shop.buyItem(item, player);
		} catch (InvalidMove e) {
			fail("fail to buy the item");
		}
	}
	
	@Test
	public void test16_playerBuyItemNotEnoughMoney() {
		player = new Player();
		Shop shop = new Shop();
		Item item = null;
		
		for(Item i: shop.getItems().keySet()) {
			item = i;
			break;
		}
		
		try {
			shop.buyItem(item, player);
			fail("should not be able to buy the item, not enough money");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test17_playerBuyItemNotHave() {
		player = new Player();
		Shop shop = new Shop();
		
		try {
			shop.buyItem(new Bomb(), player);
			fail("should not be able to buy the item, no such item in shop");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test18_playerUseTemple() {
		player = new Player();
		Temple temple = new Temple();
		int boost = temple.getBoosts().get("health");
		int health = player.getHealth();
		temple.boost("health", player);
		assertEquals(player.getHealth(), health + boost);
	}
}
