package controllers;

import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Stack;

import character.Shop;
import gui.BagPanel;
import gui.CharacterPanel;
import gui.DialogPanel;
import gui.View;
import item.BloodVial;
import item.ConsumableItem;
import item.Item;
import item.MonsterManual;
import main.Game;
import main.InvalidMove;

/**
 * This class is used to implement that the player use the mouse to click the
 * consumable item to use
 * 
 * @author Minping
 */

public class MouseController implements MouseMotionListener, MouseListener {

	private View view;
	private Item[][] bag = new Item[4][3];
	private int sizeRectangle = 60;
	private int width = 45, length = 60;
	private int gapWidth = 10;
	private int initialX = 25;
	private int initialY = 60;
	private int bagRow = 0, bagCol = 0, itemCol = 0;
	private Rectangle[][] bagRectangle = new Rectangle[4][3];

	private Rectangle[] shopRectangle = new Rectangle[3];
	private Stack<ConsumableItem> inventory = new Stack<>();

	public void setInventory(Stack<ConsumableItem> temp) {
		inventory = temp;
	}
	/**
	 * constructor
	 * 
	 * @param view
	 **/
	public MouseController(View view) {
		this.view = view;

		inventory = view.getGame().getPlayer().getInventory();

		fillRectangle();
		// printOut();
	}

	/**
	 * This method is used to create array of rectangle for all valid selection area
	 **/
	public void fillRectangle() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 3; col++) {
				if (row < 2) {
					bagRectangle[row][col] = new Rectangle(initialX + (sizeRectangle + gapWidth) * col,
							initialY + (sizeRectangle + gapWidth) * row, sizeRectangle, sizeRectangle);
				} else {
					bagRectangle[row][col] = new Rectangle(initialX + (sizeRectangle + gapWidth) * col,
							initialY + (sizeRectangle + gapWidth) * row + 10, sizeRectangle, sizeRectangle);
				}
			}
		}
		for (int col = 0; col < 3; col++) {
				shopRectangle[col] = new Rectangle(20+(length+20)*col, 430,length,length);
		}

	}

	/**
	 * This method is used to convert 2D array index into 1D array index
	 * 
	 * @param row
	 * @param col
	 **/
	public int rowColCovertIndex(int row, int col) {
		int index = 3 * (row - 1) + col;
		return index - 1;
	}

	/**
	 * This method is used to check if the mouse click on the correct position
	 * 
	 * @param x
	 * @param y
	 * @param isBagPanel
	 *            --- either for bag panel or character panel
	 * @return boolean
	 **/
	public boolean checkClickOn(int x, int y, boolean isbagPanel) {
		if (isbagPanel) {
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 3; col++) {

					if (bagRectangle[row][col].contains(x, y)) {
						bagRow = row;
						bagCol = col;
						return true;
					}
				}
			}
		}
		 else {
			 for (int col = 0; col < 3; col++) {
				 if (shopRectangle[col].contains(x, y)) {
				 itemCol = col;
				 return true;
				 }
			
			 }
		
		 }
		return false;
	}

	/**
	 * This method is used to check if the input index is valid or not
	 * 
	 * @param index
	 */
	public ConsumableItem findItem(int index) {
		if (index > inventory.size() - 1 || index < 0) {
			return null;
		}
		return inventory.get(index);
	}

	public boolean findItemInBag(int index) {
		if (index < view.getBagPanel().getItemInBag().length && index >= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.printf("X:%dY:%d\n", e.getX(), e.getY());
		if (!view.getGameStop()) {
			if (e.getSource() instanceof BagPanel) {
				useConsumableItem(e);
			} else if (e.getSource() instanceof DialogPanel) {
				if(view.getGame().isInShop()) {
					buyItem(e);
				}else if(view.getGame().isInTemple()) {
					System.out.printf("X:%dY:%d\n", e.getX(), e.getY());
					chooseTemple(e);
				}
				
			}

		}
	}
	public void chooseTemple(MouseEvent e) {
		if(checkClickOn(e.getX(), e.getY(), false)) {
			String[] names =view.getDialogPanel().getboosts();

			System.out.println("key size: "+view.getDialogPanel().getboostsInTemple().keySet().size());
			if(itemCol>=0 && itemCol<view.getDialogPanel().getboostsInTemple().keySet().size()) {
				view.getGame().tryTemple(names[itemCol]);
			}
			
			
		}
	}
	
	public void buyItem(MouseEvent e) {
		
		if (checkClickOn(e.getX(), e.getY(), false)) {
			Object[] itemShop=view.getDialogPanel().getItemsInOrder();
			ArrayList<Item> buyList = new ArrayList<Item>();
			Shop shop = view.getGame().getBoard().getCurrentLevel().getshop();
			
			for(Object object:itemShop) {
					Item item = (Item) object;
					buyList.add(item);		 
			}
			if(itemCol>=0 && itemCol<itemShop.length) {
				
				System.out.println("item name: "+buyList.get(itemCol).toString());
				try {
					shop.buyItem(buyList.get(itemCol), view.getGame().getPlayer());
				} catch (InvalidMove e1) {
				}
				view.getGame().changeView();
			}
			
		} else {
			System.out.println("have not clicked ");
		}
	}
	
	
	public void useConsumableItem(MouseEvent e) {

		if (checkClickOn(e.getX(), e.getY(), true)) {
			int index = rowColCovertIndex(bagRow + 1, bagCol + 1);
			if (!findItemInBag(index)) {
				return;
			}
			String itemName = view.getBagPanel().getItemInBag()[index];
//			System.out.println("item name: " + itemName);
			if (itemName.equals("49")||itemName.equals("40") || itemName.equals("41") || itemName.equals("48")) {
				try {
					if (itemName.equals("40")) {
						view.getGame().tryRestoreHealth("big");
					} else if (itemName.equals("41")) {
						view.getGame().tryRestoreHealth("small");
					} else if(itemName.equals("48")){
						view.getGame().tryUseFateCoin();
					}else {
						MonsterManual mm=new MonsterManual();
						
						view.getDialogPanel().setBook(mm.getManual());
					}

				} catch (InvalidMove e1) {
					e1.printStackTrace();
				}
			}

		} else {
			System.out.println("have not clicked ");
		}

	}

	public Stack<ConsumableItem> getInventory() {
		return inventory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
	// public void printOut() {
	//
	// for (int row = 0; row < 4; row++) {
	// for (int col = 0; col < 3; col++) {
	// int x = bagRectangle[row][col].x;
	// int y = bagRectangle[row][col].y;
	// System.out.println("row:" + row + " col" + col + " x:" + x + "y:" + y);
	// }
	// }
	//
	//
	// }

}
