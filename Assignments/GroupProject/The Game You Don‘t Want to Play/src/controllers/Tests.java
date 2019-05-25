package controllers;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
//@author minping

import gui.View;
import item.BloodVial;
import item.ConsumableItem;
import main.Game;

public class Tests {

	// this test case is used to test rowCol convert index
	@Test
	public void testRowColIndex() {
		View view = new View();
		MouseController mouseController = new MouseController(view);
		assertEquals(11, mouseController.rowColCovertIndex(4, 3));
		assertEquals(0, mouseController.rowColCovertIndex(1, 1));
	}

	// this test case is used to test find item method
	@Test
	public void testFindItem() {
		View view = new View();
		MouseController mouseController = new MouseController(view);
		Stack<ConsumableItem> inventory = new Stack<>();
		BloodVial bloodVial = new BloodVial(0, 0, "big");
		inventory.push(bloodVial);
		mouseController.setInventory(inventory);
		assertEquals("40", mouseController.findItem(0).getName());
	}

	// this test case is used to test clickOn method for bag JPanel
	@Test
	public void testClickOn() {
		View view = new View();
		MouseController mouseController = new MouseController(view);
		assertEquals(true, mouseController.checkClickOn(50, 60, true));
		assertEquals(false, mouseController.checkClickOn(90, 200, true));
	}

	// this test case is used to test clickOn method for character JPanel
	@Test
	public void testClickOn2() {
		View view = new View();
		MouseController mouseController = new MouseController(view);
		assertEquals(true, mouseController.checkClickOn(60, 70, false));
		assertEquals(true, mouseController.checkClickOn(60, 140, false));
		assertEquals(true, mouseController.checkClickOn(60, 200, false));
		assertEquals(false, mouseController.checkClickOn(140, 200, false));
	}

}
