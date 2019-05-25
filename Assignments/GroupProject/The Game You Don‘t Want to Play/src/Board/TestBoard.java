package Board;
import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;
import character.*;
import commonPackage.usefor.test.MockConsumableItem;
import commonPackage.usefor.test.MockMonster;
import commonPackage.usefor.test.MockShop;
import commonPackage.usefor.test.MockTemple;
import commonPackage.usefor.test.MockWearableItem;
import commonPackage.usefor.test.MockWiseMan;
import commonPackage.usefor.test.RealPlayer;
import item.*;


public class TestBoard {

	@Test
	public void testLoadLevel01() {
		String load = "( 1 )\n"
				+   "l 1 (\n" 
				+	" 00 00 00 00 00 00 00 00 00 00 00 00\n"
				+	" 00 01 01 01 01 30 01 01 01 01 01 00\n"
				+	" 00 01 01 01 01 30 01 01 00 00 00 00\n"
				+	" 00 01 01 01 01 30 01 01 00 01 01 01\n"
				+	" 00 01 01 01 00 30 00 01 00 00 00 00\n"
				+	" 00 01 01 01 00 01 00 01 01 01 01 00\n"
				+	" 00 01 01 01 00 01 00 01 00 00 00 00\n"
				+	" 00 01 01 01 00 00 00 01 00 01 01 01\n"
				+	" 00 01 01 01 01 93 01 01 00 00 00 00\n"
				+	" 00 01 01 01 01 30 01 01 01 01 01 00\n"
				+	" 00 01 01 01 01 43 01 00 00 00 00 00\n"
				+	" 00 00 00 00 00 51 01 00 01 01 01 01\n"
				+	")\n";
		try{
			Scanner sc = new Scanner(load);
			Board test02 = new Board(sc);
			String save = test02.toString();
			load = load + "\n";
			assertEquals(load,save);
		}catch (java.util.InputMismatchException e){
			System.out.println(e);
		}
	}

	/**
	 * test set level for player could move other level
	 */
	@Test
	public void testLoadLevel02() {
		Board testBD = new Board();
		for(int i = 1; i <= 13; i++){
			testBD.setCurrentLevel(i);
			assertEquals(testBD.getCurrentLevelNumber(),i);		
		}

	}

	/**
	 * Test wall is breakable or not
	 */
	@Test
	public void testWall() {
		Wall w = new Wall(10,-1,-1,-1);
		assertFalse(w.isBreakable());
		Wall bw = new Wall(11,-1,-1,-1);
		assertTrue(bw.isBreakable());
	}
	
	/**
	 * Test is downstair or up stair
	 */
	@Test
	public void testStairs() {
		Stairs up = new Stairs(51,-1,-1,-1);
		assertTrue(up.upOrDownStair());
		Stairs dw = new Stairs(52,-1,-1,-1);
		assertFalse(dw.upOrDownStair());
	}
	
	/**
	 * Test color of the wall which will be use for player open the door
	 */
	@Test
	public void testDoor() {
		for(int i = 20; i <= 24; i++){
			Door dr = new Door(i,-1,-1,-1);
			assertEquals(dr.getPosX(), -1);
			assertEquals(dr.getSize(), -1);
			assertEquals(dr.getPosY(), -1);
			assertEquals(dr.getCode(), i);
			if(i == 20) assertEquals(dr.getColor(), "gold");
			if(i == 21) assertEquals(dr.getColor(), "cyan");
			if(i == 22) assertEquals(dr.getColor(), "bronze");
			if(i == 23) assertEquals(dr.getColor(), "purple");
			if(i == 24) assertEquals(dr.getColor(), "silver");
		}

	}
	/**
	 * test cast item contain item and pick item
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test
	public <T> void testCast01(){
		for(int i = 30; i <= 34; i++){
			Ground k1 = new Ground(i,0,0,0);
			assertTrue(k1.getWhatContain() instanceof MockConsumableItem);
			k1.pickItem();
			assertNull(k1.getWhatContain());
			assertEquals(k1.getCode(), 00);
		}
		for(int i = 40; i <= 41; i++){
			Ground BB = new Ground(40,0,0,0);
			assertTrue(BB.getWhatContain() instanceof MockConsumableItem);
			BB.pickItem();
			assertNull(BB.getWhatContain());
			assertEquals(BB.getCode(), 00);
		}
		Ground BM = new Ground(43,0,0,0);
		assertTrue(BM.getWhatContain() instanceof MockConsumableItem);
		Ground gg = new Ground(00,0,0,0);
		assertNull(gg.getWhatContain());
		Ground gl = new Ground(01,0,0,0);
		assertNull(gl.getWhatContain());
	}

	/**
	 * test create wiseman cast wiseman and kill wiseman
	 */
	@SuppressWarnings({ "rawtypes"})
	@Test
	public <T> void testCast02(){
		for(int i = 03; i <= 06; i++){
			Ground w0 = new Ground(i,0,0,0);
			assertTrue(w0.getWhatContain() instanceof MockWiseMan);
			w0.killWiseMan();
			assertNull(w0.getWhatContain());
			assertEquals(w0.getCode(), 00);
		}

	}

	/**
	 * test create monster cast monster kill monster
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test
	public <T> void testCast03(){

		for(int i = 91; i <= 98; i++){
			Ground t1 = new Ground(i,0,0,0);
			assertTrue(t1.getWhatContain() instanceof MockMonster);
			t1.cleanBattleground();
			assertNull(t1.getWhatContain());
			assertEquals(t1.getCode(), 00);
		}
	}

	/**
	 * test create shop/temple cast shop/temple close shop/temple
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public <T> void testCast04(){
		Ground ST0 = new Ground(60,0,0,0);
		ST0.setShopOrTemple(new TestShop());
		assertTrue(ST0.getWhatContain() instanceof MockShop);
		ST0.closeShop();
		assertNull(ST0.getWhatContain());
		assertEquals(ST0.getCode(), 00);	

		for(int i = 65; i <= 67 ; i++){
			Ground TT0 = new Ground(65,0,0,0);
			TT0.setShopOrTemple((T) new TestTemple());
			assertTrue(TT0.getWhatContain() instanceof TestTemple);
			TT0.closeTemple();
			assertNull(TT0.getWhatContain());
			assertEquals(TT0.getCode(), 00);	
		}

	}

	/**
	 * test cast wear item ,because of setItem() class will use original item library
	 * I build a mock item then test setItem
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test
	public <T> void testCast05(){
		for(int i = 70; i <= 78; i++){
			Ground equip = new Ground(i,0,0,0);
			assertTrue(equip.getWhatContain() instanceof MockWearableItem);
			equip.pickItem();
			assertNull(equip.getWhatContain());
			assertEquals(equip.getCode(), 00);	
			assertTrue(equip.setItem( new TestArmor()));
			assertTrue(equip.setItem( new TestWeapon()));
			assertTrue(equip.setItem( new TestWing()));
		}
	}


	//=======================================================================================
	private static class TestTemple implements MockTemple{

	}
	private static class TestShop implements MockShop{

	}
	private static class TestArmor implements MockWearableItem{
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			int i = (int )(Math.random() * 70 + 3);
			return String.valueOf(i);
		}

	}
	private static class TestWeapon implements MockWearableItem{
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			int i = (int )(Math.random() * 73 + 3);
			return String.valueOf(i);
		}

	}
	private static class TestWing implements MockWearableItem{
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			int i = (int )(Math.random() * 76 + 3);
			return String.valueOf(i);
		}

	}
}
