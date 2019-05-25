package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import item.Item;
import main.Game;
import resources.ImgResources;

/**
 * This Panel Shows conversation between NPC and player, and shows all the goods
 * of the shop or the all the attribute of the player, that can level up;
 * 
 * @author jackgan
 *
 */

public class DialogPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final int TILESIZE = 64;

	private Map<Item, Integer> items;
	private Map<String, Integer> boosts;
	private String[] boostsStr;
	private boolean bookUse = false;
	private HashMap<String, List<Integer>> manual = new HashMap<>();
	private Game game;
	private Object[] it;

	private int code = 0;
	private int i = 0;

	public DialogPanel(Game game) {
		this.game = game;
		game.addObserver(this);

	}

	@Override
	public void paint(Graphics g) {

		if (bookUse) {
			drawBook(g);
			return;
		}

		Image img = ImgResources.dialogBackGroud.img;
		drawBackGround(img, g);
		drawIcon(g);

		if (game.isInShop()) {
			drawitem(g);
			return;
		} else if (game.isInTemple()) {
			drawTempleItem(g);
			return;
		} else {
			String str = "Now is on floor: " + game.getBoard().getCurrentLevelNumber();
			g.setColor(Color.WHITE);
			g.drawString(str, 80, 50);
			// drawString(str,g);
		}
	}

	private void drawTempleItem(Graphics g) {
		int x = 18;
		int y = 430;
		int w = 60;
		int h = 60;
		int gap = 20;

		boosts = game.getBoard().getCurrentLevel().getTemple().getBoosts();
		boostsStr = boosts.keySet().toArray(new String[boosts.size()]);

		if (boostsStr.length == 0)
			return;
		for (int i = 0; i < 4; i++) {
			int j = 0;
			for (; j < 3; j++) {

				ImageIcon img = new ImageIcon(View.class.getResource("/Entities/" + boostsStr[i * 3 + j] + ".png"));
				img.paintIcon(null, g, x - 2, y - 2);
				g.setColor(Color.WHITE);
				// Font font = new Font();
				// g.setFont(font);
				g.drawString("+" + boosts.get(boostsStr[i * 3 + j]) + "", x + 30, y + 55);

				if ((i * 3 + j + 1) == boostsStr.length) {
					break;
				}
				x += (w + gap);
			}
			if ((i * 3 + j + 1) == boostsStr.length) {
				break;
			}
			x = 18;
			y += (gap + h);
		}

	}

	private void drawBook(Graphics g) {
		int x = 30;
		int y = 20;

		Image img = ImgResources.dialogBackGroud2.img;
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
		ImageIcon icon = new ImageIcon(View.class.getResource("/Entities/49.png"));
		icon.paintIcon(null, g, x, y);

		y = 120;
		g.setColor(Color.WHITE);
		for (String key : manual.keySet()) {
			List<Integer> attribute = manual.get(key);
			icon = new ImageIcon(View.class.getResource("/Entities/9" + key + ".png"));
			icon.paintIcon(null, g, x, y);
			g.drawString("Heath: " + attribute.get(0), x + 50, y);
			g.drawString("Damage: " + attribute.get(1), x + 50, y += 20);
			g.drawString("Deference: " + attribute.get(2), x + 50, y += 20);
			y += 30;

		}
	}

	private void drawitem(Graphics g) {
		int x = 18;
		int y = 430;
		int w = 60;
		int h = 60;
		int gap = 20;

		Graphics2D _g = (Graphics2D) g.create();
		_g.setComposite(AlphaComposite.SrcOver.derive(0.7f));
		_g.setColor(Color.WHITE);

		this.items = game.getBoard().getCurrentLevel().getshop().showShop();

		this.it = items.keySet().toArray();
		if (it.length == 0)
			return;
		for (int i = 0; i < 4; i++) {
			int j = 0;
			for (; j < 3; j++) {
				_g.fillRect(x, y, w, h);
				ImageIcon img = new ImageIcon(View.class.getResource("/Entities/" + it[i * 3 + j].toString() + ".png"));
				img.paintIcon(null, g, x - 2, y - 2);
				g.setColor(Color.RED.darker());
				// Font font = new Font();
				// g.setFont(font);
				g.drawString("$" + items.get(it[i * 3 + j]) + "", x + 35, y + 55);

				if ((i * 3 + j + 1) == it.length) {
					break;
				}
				x += (w + gap);
			}
			if ((i * 3 + j + 1) == it.length) {
				break;
			}
			x = 18;
			y += (gap + h);
		}

	}

	private void drawString(String str, Graphics g) {

		g.setColor(Color.WHITE);

		int i = 0;
		for (; i < str.length() / 25; i++) {
			g.drawString(str.substring(i * 25, i * 25 + 25), 30, 110 + 20 * i);
		}
		g.drawString(str.substring(i * 25, str.length()), 30, 110 + 20 * i);

	}

	private void drawIcon(Graphics g) {
		switch (game.getPlayer().getFacingDirection()) {
		case "up":
			code = 10;
			break;
		case "down":
			code = 1;
			break;
		case "right":
			code = 7;
			break;
		case "left":
			code = 4;
			break;
		default:
			break;
		}
		String name = code + i + "";
		ImageIcon img = null;
		if (game.isInShop()) {
			img = new ImageIcon(View.class.getResource("/Entities/60.png"));
		}else if(game.isInTemple()) 
		{
			img = new ImageIcon(View.class.getResource("/Entities/65.png"));
		}else {
			img = new ImageIcon(View.class.getResource("/Player/Player_" + name + ".png"));

		}
		img.paintIcon(null, g, 30, 30);

	}

	private void drawBackGround(Image img, Graphics g) {
		// to load the dialog BackGroud image
		// fill the BackGroud to appropriate size
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);

	}

	/**
	 * set the size for character panel, on the basis of tile size
	 */
	public Dimension getPreferredSize() {
		return new Dimension((int) (TILESIZE * 4), TILESIZE * 12);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();

	}

	public Object[] getItemsInOrder() {
		return this.it;
	}

	public Map<Item, Integer> getItemsInShop() {
		return this.items;
	}

	public void setBook(HashMap<String, List<Integer>> map) {
		this.manual = map;
		bookUse = !bookUse;
		repaint();
	}

	public String[] getboosts() {
		return this.boostsStr;
	}
	
	public Map<String, Integer> getboostsInTemple() {
		return this.boosts;
	}

}
