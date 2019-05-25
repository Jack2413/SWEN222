package item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import character.Player;
import commonPackage.usefor.test.RealPlayer;
import main.InvalidMove;

/**
 * more tests for items.
 * @author stella
 *
 */
public class ExtrernalTests {
	RealPlayer player = new MockPlayer();
	private Bomb bomb = new Bomb();
	private Key key = new Key("yellow");
	private Armor armor = new Armor(1);
	private Weapon weapon = new Weapon(1);
	private Wing wing = new Wing(1);
	
	@Test
	public void test01_useHealthPotionSmall() throws InvalidMove {
		player = new Player();
		BloodVial health = new BloodVial("small");
		health.use(player);
		int healthBefore = player.getHealth();
		int amount = health.getAmount();
		assertEquals(healthBefore + amount, player.getHealth());
	}
	
	@Test
	public void test02_useHealthPotionBig() throws InvalidMove {
		player = new Player();
		BloodVial health = new BloodVial("big");
		health.use(player);
		int healthBefore = player.getHealth();
		int amount = health.getAmount();
		assertEquals(healthBefore + amount, player.getHealth());
	}
	
	@Test
	public void test03_playerEquipArmor() {
		player = new Player();
		int prevDefence = player.getDefence();
		int boostDefence = armor.getDefence();
		armor.putOn(player);
		assertEquals(player.getDefence(), prevDefence + boostDefence);
	}
	
	@Test
	public void test04_playerChangeArmor() {
		player = new Player();
		armor.putOn(player);
		int prevDefence = player.getDefence();
		int boostDefence = armor.getDefence();
		int basicDefence = prevDefence - boostDefence;
		Armor newArmor = new Armor(2);
		int newBoostDefence = newArmor.getDefence();
		armor.takeOff(player);
		newArmor.putOn(player);
		assertEquals(player.getDefence(), basicDefence + newBoostDefence);
	}
	
	@Test
	public void test05_playerEquipWeapon() {
		player = new Player();
		int prevDamage = player.getDamage();
		int boostDamage = weapon.getAttack();
		weapon.putOn(player);
		assertEquals(player.getDamage(), prevDamage + boostDamage);
	}
	
	@Test
	public void test06_playerChangeWeapon() {
		player = new Player();
		weapon.putOn(player);
		int prevDamage = player.getDamage();
		int boostDamage = weapon.getAttack();
		int basicDamage = prevDamage - boostDamage;
		Weapon newWeapon = new Weapon(2);
		int newBoostDamage = newWeapon.getAttack();
		weapon.takeOff(player);
		newWeapon.putOn(player);
		assertEquals(player.getDamage(), basicDamage + newBoostDamage);
	}

	@Test
	public void test07_playerEquipWing() {
		player = new Player();
		int prevDamage = player.getDamage();
		int prevDefence = player.getDefence();
		int boostDamage = wing.getIncreasedDamage();
		int boostDefence = wing.getIncreasedDefense();
		wing.putOn(player);
		assertEquals(player.getDamage(), prevDamage + boostDamage);
		assertEquals(player.getDefence(), prevDefence + boostDefence);
	}
	
	@Test
	public void test08_playerChangeWing() {
		player = new Player();
		wing.putOn(player);
		int prevDamage = player.getDamage();
		int boostDamage = wing.getIncreasedDamage();
		int basicDamage = prevDamage - boostDamage;
		
		int prevDefence = player.getDefence();
		int boostDefence = wing.getIncreasedDefense();
		int basicDefence = prevDefence - boostDefence;
		
		Wing newWing = new Wing(2);
		int newBoostDamage = newWing.getIncreasedDamage();
		int newBoostDefence = newWing.getIncreasedDefense();
		wing.takeOff(player);
		newWing.putOn(player);
		assertEquals(player.getDamage(), basicDamage + newBoostDamage);
		assertEquals(player.getDefence(), basicDefence + newBoostDefence);
	}
}
