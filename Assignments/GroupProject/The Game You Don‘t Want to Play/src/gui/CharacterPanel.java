package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Game;
import resources.ImgResources;

/**
 * The Panel for showing all the attribute main character
 * @author Zhancheng gan
 *
 */

public class CharacterPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	private static final int TILESIZE = 64;
	private static final int RECTSIZE = 50;
	private static final int GAPSIZE = 15;
	private static final int INITIALX = 32;
	private static final int INITIALY = 45;
	private Game game;
	private Rectangle[] charaRect = new Rectangle[3];

	public CharacterPanel(Game game) {
		game.addObserver(this);
		this.game = game;
		CreateRectangle();
	}

	@Override
	public void paint(Graphics g) {

		Image img = ImgResources.characterBackGroud.img; // to load the character BackGroud image
		// fill the BackGroud to appropriate size
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
		drawAttribute(g);
		drawKeys(g);
		drawItem(g);
	}
	/**
	 * 	To draw all the equipment on the player, shows on character Panel
	 * @param g Graphics
	 */
 
	private void drawItem(Graphics g) {
		if(game.getPlayer().getCurrentWeapon()!=null) {
			String name = game.getPlayer().getCurrentWeapon().getName();
			Image img;
			try {
				img = ImageIO.read(View.class.getResource("/Entities/" + name + ".png"));
				int x = charaRect[0].x;
				int y = charaRect[0].y;
				int w = img.getWidth(this);
				int h = img.getHeight(this);
				g.drawImage(img, x, y, w, h,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		if(game.getPlayer().getCurrentArmor()!=null) {
			String name = game.getPlayer().getCurrentArmor().getName();
			Image img;
			try {
				img = ImageIO.read(View.class.getResource("/Entities/" + name + ".png"));
				int x = charaRect[1].x;
				int y = charaRect[1].y;
				int w = img.getWidth(this);
				int h = img.getHeight(this);
				g.drawImage(img, x, y, w,h,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(game.getPlayer().getCurrentWing()!=null) {
			String name = game.getPlayer().getCurrentWing().getName();
			Image img;
			try {
				img = ImageIO.read(View.class.getResource("/Entities/" + name + ".png"));
				int x = charaRect[2].x;
				int y = charaRect[2].y;
				int w = img.getWidth(this);
				int h = img.getHeight(this);
				g.drawImage(img, x, y, w,h,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * To draw Number of all keys of the player have 
	 * @param g
	 */

	private void drawKeys(Graphics g) {
		int x = 156;
		int y = 267;
		g.drawString(game.getPlayer().getNumKeys("bronze")+"", x, y);
		y += 40;
		g.drawString(game.getPlayer().getNumKeys("sliver")+"", x, y);
		y += 40;
		g.drawString(game.getPlayer().getNumKeys("gold")+"", x, y);
		x = 216;
		y= 267;
		g.drawString(game.getPlayer().getNumKeys("cyan")+"", x, y);
		y += 40;
		g.drawString(game.getPlayer().getNumKeys("purple")+"", x, y);
		y += 40;
		g.drawString(game.getPlayer().getGold()+"", x, y);
	}
	/**
	 * To draw Attribute of the player:
	 * Health
	 * Damage
	 * Defence
	 * @param g
	 */

	private void drawAttribute(Graphics g) {
		int x = 86;
		int y = 267;
		// System.out.print(game.getPlayer().getHealth();
		g.drawString(game.getPlayer().getHealth() + "", x, y);
		y += 40;
		g.drawString(game.getPlayer().getDamage() + "", x, y);
		y += 40;
		g.drawString(game.getPlayer().getDefence() + "", x, y);

	}

	/**
	 * The Rectangles is provide for the Mouse controller, to checking does the user
	 * chick on the items on Character Panel .
	 */
	public void CreateRectangle() {
		for (int row = 0; row < 3; row++) {
			int x = INITIALX;
			int y = INITIALY + (RECTSIZE + GAPSIZE) * row;
			// the gap changes when the row lager than 1
			charaRect[row] = new Rectangle(x, y, RECTSIZE, RECTSIZE);

		}
	}

	/**
	 * To check those the user check on the items frame
	 * 
	 * @return true if contains otherwise return false
	 */
	public boolean containsRect(int x, int y) {
		for (int row = 0; row < 3; row++) {
			if (charaRect[row].contains(x, y))
				return true;
		}
		return false;

	}

	/**
	 * set the size for character panel, on the basis of tile size
	 */
	public Dimension getPreferredSize() {
		return new Dimension((int) (TILESIZE * 4), TILESIZE * 6);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();

	}
}
