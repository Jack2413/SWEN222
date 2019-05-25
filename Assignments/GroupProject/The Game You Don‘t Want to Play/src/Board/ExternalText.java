package Board;

import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;

public class ExternalText {

	/**
	 * to test enter the  temple and level the temple 
	 */
	@Test
	public <T> void testCast01() {
		Ground<?> k1 = new Ground<Object>(70, 0, 0, 0);
		assertTrue(!k1.closeTemple());
	}

	@Test
	public <T> void testCast02() {
		Ground<?> k1 = new Ground<Object>(65, 0, 0, 0);
		assertTrue(!k1.closeTemple());
		k1 = new Ground<Object>(66, 0, 0, 0);
		assertTrue(!k1.closeTemple());
		k1 = new Ground<Object>(67, 0, 0, 0);
		assertTrue(!k1.closeTemple());
	}
	/**
	 * to test enter the shop and level the shop
	 */

	@Test
	public <T> void testCast03() {
		Ground<?> k1 = new Ground<Object>(70, 0, 0, 0); 
		assertTrue(!k1.closeShop());
	}

	@Test
	public <T> void testCast04() {
		Ground<?> k1 = new Ground<Object>(60, 0, 0, 0);
		assertTrue(k1.closeShop());
	}
	/**
	 * to test can pick any items in any position of the board
	 */

	@Test
	public <T> void testCast05() {
		String load = "( 1 )\n" + "l 1 (\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + " 30 31 32 33 34 40 41 72 43 48 74 73\n"
				+ " 30 31 32 33 34 40 41 72 43 48 74 73\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					// System.out.printf("i: %d,j: %d: ",i,j);
					assertTrue(l.pickItem(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}

	/**
	 * to test if there do not have items, it can not pick up in any position of the board
	 */
	@Test
	public <T> void testCast06() {
		String load = "( 1 )\n" + "l 1 (\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + " 00 01 02 03 04 05 06 10 11 20 21 67\n"
				+ " 22 23 24 98 91 92 93 94 51 52 65 66\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					// System.out.printf("i: %d,j: %d: \n",i,j);
					assertTrue(!l.pickItem(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}
	/**
	 * to test in all position of the board, the wall can be break 
	 */

	@Test
	public <T> void testCast07() {
		String load = "( 1 )\n" + "l 1 (\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + " 11 11 11 11 11 11 11 11 11 11 11 11\n"
				+ " 11 11 11 11 11 11 11 11 11 11 11 11\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {

					assertTrue(l.breakWall(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}
	/**
	 * create a lava and not lava ground to test is lava method
	 */
	
	@Test
	public <T> void testCast08() {
		Ground<?> k1 = new Ground<Object>(01, 0, 0, 0);
		assertTrue(k1.isLava());
	}
	
	@Test
	public <T> void testCast09() {
		Ground<?> k1 = new Ground<Object>(00, 0, 0, 0);
		assertTrue(!k1.isLava());
	}
	/**
	 * to test in every position of the board the wall can not break because the wall are 
	 * not breakable
	 */

	@Test
	public <T> void testCast10() {
		String load = "( 1 )\n" + "l 1 (\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + " 10 10 10 10 10 10 10 10 10 10 10 10\n"
				+ " 10 10 10 10 10 10 10 10 10 10 10 10\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					assertTrue(!l.breakWall(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}
	/**
	 * to test in every position of the board the the door can not be open if there don't have any doors
	 */

	@Test
	public <T> void testCast11() {
		String load = "( 1 )\n" + "l 1 (\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + " 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+ " 00 00 00 00 00 00 00 00 00 00 00 00\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					assertTrue(!l.openTheDoor(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}
	/**
	 * to test the doors can be open in every position of the board
	 */

	@Test
	public <T> void testCast12() {
		String load = "( 1 )\n" + "l 1 (\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + " 20 21 23 24 22 20 21 23 24 22 20 21\n"
				+ " 20 21 23 24 22 20 21 23 24 22 20 21\n" + ")\n";
		try {
			Scanner sc = new Scanner(load);
			Board test = new Board(sc);
			Level l = test.getCurrentLevel();
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 12; j++) {
					assertTrue(l.openTheDoor(i, j));
				}
			}
		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
	}
	/**
	 * to test kill wiseMan method
	 */

	@Test
	public <T> void testCast13() {
		
		Ground<?> k1 = new Ground<Object>(03, 0, 0, 0);
		assertTrue(k1.killWiseMan());
		k1 = new Ground<Object>(04, 0, 0, 0);
		assertTrue(k1.killWiseMan());
		k1 = new Ground<Object>(05, 0, 0, 0);
		assertTrue(k1.killWiseMan());
		k1 = new Ground<Object>(06, 0, 0, 0);
		assertTrue(k1.killWiseMan());
	}
	
	@Test
	public <T> void testCast14() {
		Ground<?> k1 = new Ground<Object>(60, 0, 0, 0);
		assertTrue(!k1.killWiseMan());
	}
	/**
	 * to test clean Battle ground method
	 */
	
	@Test
	public <T> void testCast15() {
		Ground<?> k1 = new Ground<Object>(91, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(92, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(93, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(94, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(95, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(96, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(97, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
		k1 = new Ground<Object>(98, 0, 0, 0);
		assertTrue(k1.cleanBattleground());
	}
	
	@Test
	public <T> void testCast16() {
		Ground<?> k1 = new Ground<Object>(60, 0, 0, 0);
		assertTrue(!k1.killWiseMan());
	}
	/**
	 * to test counld Move method
	 */
	
	@Test
	public <T> void testCast17() {
		Ground<?> k1 = new Ground<Object>(00, 0, 0, 0);
		assertTrue(k1.couldMove());
		 k1 = new Ground<Object>(01, 0, 0, 0);
		assertTrue(!k1.couldMove());
	}

}
