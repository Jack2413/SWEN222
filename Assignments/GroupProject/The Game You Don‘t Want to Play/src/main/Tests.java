package main;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Board.Door;
import Board.Wall;
import character.Player;
import item.Bomb;
import item.Key;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {
	@Test
	public void test01_gameCreated() {
		Game game = new Game();
	}
	
	@Test
	public void test02_playerOpenDoor() {
		Game game = new Game();
		Door door = new Door(20, 1, 1, 1);
		Player player = game.getPlayer();
		try {
			player.addItem(new Key("gold"));
			game.tryOpenDoor(door);
		} catch (InvalidMove e) {
			fail("should be able to open door");
		}
	}
	
	@Test
	public void test03_playerOpenDoorNoKey() {
		Game game = new Game();
		Door door = new Door(20, 1, 1, 1);
		try {
			game.tryOpenDoor(door);
			fail("should not be able to open door, do not have key");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test04_playerUseBombNoBreakableWall() {
		Game game = new Game();
		Bomb bomb = new Bomb();
		try {
			game.getPlayer().addItem(bomb);
			game.tryBomb();
			fail("should not be able to use bomb, no breakable wall");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test05_playerUseBombNotHave() {
		Game game = new Game();
		try {
			game.tryBomb();
			fail("should not be able to use bomb not have");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test06_playerMovement() throws InvalidMove {
		Game game = new Game();
		Player player = game.getPlayer();
		int xPos = player.getXPos();
		int yPos = player.getYPos();
		game.getPlayer().move("right");
		int newXPos = player.getXPos();
		int newYPos = player.getYPos();
		
		assertEquals(xPos+1, newXPos);
		assertEquals(yPos, newYPos);
	}
}
