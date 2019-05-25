package gui;

import javax.swing.SwingUtilities;
import org.junit.Test;

import main.Game;

public class internalTest {
	
	/**
	 * Paint out the menu to check 
	 * @throws InterruptedException
	 */
	
	@Test
	public void menuTest() throws InterruptedException{
		SwingUtilities.invokeLater(()->{
			new Menu();
		});
		Thread.sleep(3000);
	}
	/**
	 * Paint out the game to check 
	 * @throws InterruptedException
	 */
	
	@Test
	public void GameTest() throws InterruptedException{
		SwingUtilities.invokeLater(()->{
			new Game();
		});
		Thread.sleep(3000);
	}
	
//	@Test
//	public void menuTest() throws InterruptedException{
//		SwingUtilities.invokeLater(()->{
//			new Menu();
//		});
//		Thread.sleep(3000);
//	}

}
