package item;

import static org.junit.Assert.*;

import org.junit.Test;


import commonPackage.usefor.test.RealPlayer;

/**
 * 
 * @author minping
 *
 */

public class Tests {

	// This test case is used to check the method of setAttributes for armor class
	@Test
	public void testSetAttributes1() {
		Armor armor = new Armor(0, 0, 0);
		assertEquals(armor.getName(), "70");
		assertEquals(armor.getDefence(), 2);
		armor = new Armor(0, 0, 1);
		assertEquals(armor.getName(), "71");
		assertEquals(armor.getDefence(),Armor.defence4 * 2);
		armor = new Armor(0, 0, 2);
		assertEquals(armor.getName(), "72");
		assertEquals(armor.getDefence(), Armor.defence7 * 2);
	}

	// This test case is used to check the method of setAttributes for Weapon class
	@Test
	public void testSetAttributes2() {
		Weapon weapon = new Weapon(0, 0, 0);
		assertEquals(weapon.getName(), "73");
		assertEquals(weapon.getAttack(), 2);
		weapon = new Weapon(0, 0, 1);
		assertEquals(weapon.getName(), "74");
		assertEquals(weapon.getAttack(),(int)( Weapon.attack4 * 0.66));
		weapon = new Weapon(0, 0, 2);
		assertEquals(weapon.getName(), "75");
		assertEquals(weapon.getAttack(), (int) (Weapon.attack7 * 0.66));
	}

	// This test case is used to check the method of setAttributes for Wing class
	@SuppressWarnings("deprecation")
	@Test
	public void testSetAttributes3() {
		Wing wing = new Wing(0, 0, 0);
		assertEquals(wing.getName(), "76");
		assertEquals(2, wing.getIncreasedDamage());
		wing = new Wing(0, 0, 1);
		assertEquals(wing.getName(), "77");
		assertEquals(wing.getIncreasedDamage(), 5);
		wing = new Wing(0, 0, 2);
		assertEquals(wing.getIncreasedDamage(), 26);
		assertEquals(wing.getName(), "78");
	}

	// This test case is used to check the method of use for BloodVial class
	@Test
	public void testUse1() {
		BloodVial bloodVial = new BloodVial(0, 0, "small");
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int amount = bloodVial.getAmount();
		int initialHealth = player.getHealth();
		bloodVial.use(player);
		assertEquals(initialHealth + amount, player.getHealth());
	}

	// This test case is used to check the method of use for BloodVial class
	@Test
	public void testUse2() {
		BloodVial bloodVial = new BloodVial(0, 0, "big");
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int amount = bloodVial.getAmount();
		int initialHealth = player.getHealth();
		bloodVial.use(player);
		assertEquals(initialHealth + amount, player.getHealth());
	}

	// This test case is used to check the genreateName method for key class
	@Test
	public void testGenerateName() {
		Key key = new Key("gold");
		assertEquals("30", key.getName());
		key = new Key("silver");
		assertEquals("34", key.getName());
		key = new Key("purple");
		assertEquals("33", key.getName());
		key = new Key("cyan");
		assertEquals("31", key.getName());
		key = new Key("bronze");
		assertEquals("32", key.getName());
	}

	// This test case is used to test if the player try to put on the weapon, then
	// the player's attribute should be changed
	@Test
	public void testValidPutOn() {
		RealPlayer player = new MockPlayer();
		int initalDamage = player.getDamage();
		Weapon weapon = new Weapon(0, 0, 0);
		weapon.putOn(player);
		assertEquals(true, weapon.getIsoN());
		assertEquals(initalDamage + weapon.getAttack(), player.getDamage());
	}

	// This case case is used test if the player already wear a weapon, the the
	// player try to take off the weapon.After
	// the player take off the weapon, the player' damages should also be decreased
	@Test
	public void testValidTakeOff() {
		RealPlayer player = new MockPlayer();
		int initalDamage = player.getDamage();
		Weapon weapon = new Weapon(0, 0, 0);
		weapon.putOn(player);
		assertEquals(true, weapon.getIsoN());
		assertEquals(initalDamage + weapon.getAttack(), player.getDamage());
		weapon.takeOff(player);
		assertEquals(false, weapon.getIsoN());
		assertEquals(initalDamage, player.getDamage());
	}

	// This test case is used to test if the player have not wear the weapon, then
	// the player can not take off the weapon
	@Test
	public void testInvalidTakeOff() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int initalDamage = player.getDamage();
		Weapon weapon = new Weapon(0, 0, 0);
		weapon.takeOff(player);
		assertEquals(false, weapon.getIsoN());
		assertEquals(initalDamage, player.getDamage());
	}

	// this test case is used to test if the player already wear weapon, the player
	// cannot wear two weapons at same time
	@Test
	public void testInvalidPutOn() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		Weapon weapon = new Weapon(0, 0, 0);
		weapon.putOn(player);
		assertEquals(true, weapon.getIsoN());
		int initalDamage = player.getDamage();
		weapon.putOn(player);
		assertEquals(true, weapon.getIsoN());
		assertEquals(initalDamage, player.getDamage());
	}

	// this test case is used to test the "use" method for FateCoin class
	@Test
	public void testUse3() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		FateCoin fateCoin = new FateCoin(0, 0);
		int initialHealth = player.getHealth();
		fateCoin.use(player);
		int randomNumber = fateCoin.getRandomNumber();
		if (randomNumber < 5) {
			assertEquals(initialHealth - fateCoin.getAmount(), player.getHealth());
		} else {
			assertEquals(initialHealth + fateCoin.getAmount(), player.getHealth());
		}
	}

	// this test case is used to test if putOn method for Wing class
	@Test
	public void testValidPutOn2() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int initalSpeed = player.getSpeed();
		Wing wing = new Wing(0, 0, 0);
		wing.putOn(player);
		assertEquals(true, wing.getIsOn());
		assertEquals(initalSpeed + wing.getIncreaseSpeed(), player.getSpeed());
	}

	// this test case is used to test if the player has not wear the wing, then just
	// directly want to take off the wing.Which is invalid
	// take off
	@Test
	public void testInValidTakeOff2() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int initialSpeed = player.getSpeed();
		Wing wing = new Wing(0, 0, 0);
		wing.takeOff(player);
		assertEquals(false, wing.getIsOn());
		assertEquals(initialSpeed, player.getSpeed());
	}

	// This case case is used to test if the player already wear a wing, then the
	// player try to take off the wing.After
	// the player take off the wing, the player's speed should also be decreased
	@Test
	public void testValidTakeOff2() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		Wing wing = new Wing(0, 0, 0);
		int initialSpeed = player.getSpeed();
		wing.putOn(player);
		assertEquals(true, wing.getIsOn());
		wing.takeOff(player);
		assertEquals(initialSpeed, player.getSpeed());
	}

	// This test case is used to test if the player has already wear wing, then the
	// speed can not increase anymore.
	@Test
	public void testInValidPutOn2() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		Wing wing = new Wing(0, 0, 0);
		int initialSpeed = player.getSpeed();
		wing.putOn(player);
		wing.putOn(player);
		assertEquals(true, wing.getIsOn());
		assertEquals(initialSpeed + wing.getIncreaseSpeed(), player.getSpeed());
	}

	// this test case is used to test if putOn method for Armor class
	@Test
	public void testValidPutOn3() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int initalDefence = player.getDefence();
		Armor armor = new Armor(0, 0, 0);
		armor.putOn(player);
		assertEquals(true, armor.getIsOn());
		assertEquals(initalDefence + armor.getDefence(), player.getDefence());
	}

	// this test case is used to test if the player has not wear the armor, then
	// just
	// directly want to take off the armor.Which is invalid
	// take off
	@Test
	public void testInValidTakeOff3() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		int initialDefence = player.getDefence();
		Armor armor = new Armor(0, 0, 0);
		armor.takeOff(player);
		assertEquals(false, armor.getIsOn());
		assertEquals(initialDefence, player.getDefence());
	}

	// This case case is used to test if the player already wear a armor, then the
	// player try to take off the armor. After
	// the player take off the armor, the player's defense should also be decreased
	@Test
	public void testValidTakeOff3() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		Armor armor = new Armor(0, 0, 0);
		int initialDefense = player.getDefence();
		armor.putOn(player);
		assertEquals(true, armor.getIsOn());
		armor.takeOff(player);
		assertEquals(initialDefense, player.getDefence());
	}

	// This test case is used to test if the player has already wear armor, then the
	// defense can not increase anymore.
	@Test
	public void testInValidPutOn3() {
		RealPlayer player = new MockPlayer();
//		Player player = new Player();
		Armor armor = new Armor(0, 0, 0);
		int initialDefense = player.getDefence();
		armor.putOn(player);
		armor.putOn(player);
		assertEquals(true, armor.getIsOn());
		assertEquals(initialDefense + armor.getDefence(), player.getDefence());
	}

	// Test on method for all item class
	@Test
	public void testOn() {
		Armor armor = new Armor(0, 0, 0);
		assertEquals(true, armor.on(0, 0));
		assertEquals(false, armor.on(1, 0));
		BloodVial bloodVial = new BloodVial(0, 0, "small");
		assertEquals(true, bloodVial.on(0, 0));
		assertEquals(false, bloodVial.on(1, 0));
		Bomb bomb = new Bomb(0, 0);
		assertEquals(true, bomb.on(0, 0));
		assertEquals(false, bomb.on(1, 0));
		FateCoin fateCoin = new FateCoin(0, 0);
		assertEquals(true, fateCoin.on(0, 0));
		assertEquals(false, fateCoin.on(1, 0));
		Key key = new Key(0, 0, "dd");
		assertEquals(true, key.on(0, 0));
		assertEquals(false, bomb.on(1, 0));
		Weapon weapon = new Weapon(0, 0, 0);
		assertEquals(true, weapon.on(0, 0));
		assertEquals(false, weapon.on(1, 0));
		Wing wing = new Wing(0, 0, 0);
		assertEquals(true, wing.on(0, 0));
		assertEquals(false, wing.on(1, 0));
	}
}
