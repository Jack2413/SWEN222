package gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Board.*;
import character.Player;
import controllers.*;
import main.Game;
import resources.IconResources;
import resources.ImgResources;
import resources.PlayerResources;
import resources.SoundResources;

/**
 * 
 * This is View class for the GUI To do rendering, to paint our all the
 * components, includes Players, Maps ect
 * 
 * @author ZhanCheng Gan
 *
 */

public class View extends JComponent implements Observer {

	private static final long serialVersionUID = 1L;
	public static final int TILESIZE = 64;
	private java.util.Timer timer = new java.util.Timer();
	private int ac = 0;

	private BagPanel bagPanel;
	private CharacterPanel characterPanel;
	private DialogPanel dialogPanel;

	private JButton Save;
	private JButton Load;
	private JButton Music;
	private JButton Quit;

	private boolean gameStop = false;
	private boolean MusicOn = false;

	private Game game;
	private JFrame f;

	/**
	 * The Constructor of this view class to lay out the game frame add bag Panel
	 * characterPanel and dialogPanel add mouse listener and key listener for all
	 * panels
	 * 
	 * @param game
	 */

	public View() {
		game = new Game(this);
		// setting attribute for this view
		game.addObserver(this);
		//this.game = game;
		setPanels();
	}
	
	public View(Game game) {
		this.game = game;
		game.addObserver(this);
		setPanels();
	}

	public void setPanels() {
		this.setFocusable(true);
		this.setPreferredSize(getPreferredSize());
		this.addKeyListener(new KeyController(this));
		this.addMouseListener(new MouseController(this));
		
		// create UI for the main, add panels
		bagPanel = new BagPanel(this.game);
		bagPanel.addMouseListener(new MouseController(this));
		bagPanel.setSize(new Dimension(getPreferredSize()));

		characterPanel = new CharacterPanel(this.game);
		characterPanel.addMouseListener(new MouseController(this));
		characterPanel.setSize(new Dimension(getPreferredSize()));

		dialogPanel = new DialogPanel(this.game);
		dialogPanel.addMouseListener(new MouseController(this));
		dialogPanel.setSize(new Dimension(getPreferredSize()));

		// set GridLayout for fl
		JPanel fl = new JPanel(new GridLayout(2, 1));
		fl.add(characterPanel);
		fl.add(bagPanel);
		fl.setVisible(true);

		// add to the frame
		f = new JFrame("The Game You Don't Want to Play");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(fl, BorderLayout.WEST);
		f.add(this, BorderLayout.CENTER);
		f.add(dialogPanel, BorderLayout.EAST);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);

		addButton();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				if (ac == 200) {
					ac = 100;
				} else {
					ac = 200;
				}
				if (!game.getPlayer().checkDead()&&!game.getGameWin())
					repaint();
			}

		}, 0, 500);
	}

	/**
	 * added all the buttons for this View panel
	 */

	private void addButton() {
		Save = new JButton("Save");
		Load = new JButton("Load");
		Music = new JButton("Music");
		Quit = new JButton("Quit");

		Save.addActionListener((e) -> {
			game.save();
		});
		Load.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt");
			chooser.setFileFilter(filter);
			FileReader savefile;
			int result = chooser.showOpenDialog(new JFrame());
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					savefile = new FileReader(chooser.getSelectedFile());
					new Game(savefile);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				SoundResources.Meun.sound.stop();
			}
		});

		Quit.addActionListener((e) -> {
			// gameContinue();
			System.exit(0);
		});
		Music.addActionListener((e) -> {
			MusicOn = !MusicOn;
			if (MusicOn) {
				SoundResources.FightBGM.sound.loop();
			} else {
				SoundResources.FightBGM.sound.stop();
			}

		});

		// set layout for the buttons
		this.setLayout(new GridLayout(0, 1, 0, 100));
		this.setBorder(new EmptyBorder(160, 300, 160, 300));

		Save.setFocusable(false);
		Load.setFocusable(false);
		Music.setFocusable(false);
		Quit.setFocusable(false);

		Save.setVisible(gameStop);
		Load.setVisible(gameStop);
		Music.setVisible(gameStop);
		Quit.setVisible(gameStop);

		this.add(Save);
		this.add(Load);
		this.add(Music);
		this.add(Quit);
	}

	/**
	 * When user purses ESC button, the game will stop set the button to Visible and
	 * will repaint the current Frame
	 */

	public void gameStop() {

		gameStop = !gameStop;
		Save.setVisible(gameStop);
		Load.setVisible(gameStop);
		Music.setVisible(gameStop);
		Quit.setVisible(gameStop);
		repaint();

	}

	public Dimension getPreferredSize() {
		return new Dimension((TILESIZE * 12), TILESIZE * 12);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();

	}

	/**
	 * To paint out the game board
	 */

	@Override
	public void paintComponent(Graphics g) {

		drawFloor(g);
		drawMap(game.getBoard(), g);
		// drawDecoration(g);
		drawPlayer(game.getPlayer(), g);

		if (game.isAttacking()) {
			drawAttacking(g);
		}
		if (gameStop) { // if the game is stop paint out the stop frame
			Graphics2D _g = (Graphics2D) g.create();
			_g.setComposite(AlphaComposite.SrcOver.derive(0.8f));

			_g.setColor(Color.darkGray.darker());
			_g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		// System.out.println(game.getPlayer().isDeath());
		if (game.getPlayer().checkDead()) {
			Icon icon = IconResources.Die.icon;
			Object[] options = { "Restart", "Quit" };
			int response = JOptionPane.showOptionDialog(this, "Do you want to restart your game?", "You Die",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			 if (response == 0) {
				new Menu();
				f.setVisible(false);
			} else if (response == 1) {
				System.exit(0);
			}
		}
		if (game.getGameWin()) {
			Icon icon = PlayerResources.Down.image;
			Object[] options = { "Restart", "Quit" };
			int response = JOptionPane.showOptionDialog(this, "Do you want to restart your game?", "You WIN!",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			 if (response == 0) {
				new Menu();
				f.setVisible(false);
			} else if (response == 1) {
				System.exit(0);
			}
		}
	}

	private void drawDecoration(Graphics g) {
		for (int x = 0; x < Level.BOARDSIZE * 2; x++) {
			for (int y = 0; y < Level.BOARDSIZE * 2; y++) {
				if ((int) (Math.random() * 5) == 0)
					continue;
				int num = (int) (Math.random() * 40) + 1;
				ImageIcon img = new ImageIcon(View.class.getResource("/decoration/d" + num + ".png"));
				img.paintIcon(null, g, y * TILESIZE / 2, x * TILESIZE / 2);
			}
		}
	}

	/**
	 * to show the attacking panel when player fighting with monsters it shows the
	 * 
	 * @param g
	 */
	private void drawAttacking(Graphics g) {

		int x = TILESIZE * 3;
		int y = TILESIZE * 4;

		Image img = ImgResources.fightingBackGroud.img;
		g.drawImage(img, x, y, x + TILESIZE * 6, y + TILESIZE * 4, 0, 0, img.getWidth(null), img.getHeight(null), null);

		x += TILESIZE;
		y += TILESIZE * 0.6;
		ImageIcon icon = PlayerResources.Down.image;
		icon.paintIcon(null, g, x, y);
		
		drawHeathBar(x,(int) (y + TILESIZE*0.7), game.getPlayer().getHealth(),30,g);
		
		g.drawString("HP: " + game.getPlayer().getHealth(), x, (int) (y + TILESIZE*1.5));
		g.drawString("Attack: " + game.getPlayer().getDamage(), x, (int) (y + TILESIZE*2));
		g.drawString("Deffence: " + game.getPlayer().getDefence(), x, (int) (y + TILESIZE*2.5));

		x += TILESIZE * 3;
		String path = "/Entities/" + game.getPlayer().getVSmonster().getName() + ".png";
		icon = new ImageIcon(View.class.getResource(path));
		icon.paintIcon(null, g, x, y);
		
		drawHeathBar(x,(int) (y + TILESIZE*0.7), game.getPlayer().getVSmonster().getHealth(),30,g);
		
		
		g.drawString("HP: " + game.getPlayer().getVSmonster().getHealth(), x, (int) (y + TILESIZE*1.5));
		g.drawString("Attack: " + game.getPlayer().getVSmonster().getDamage(), x, (int) (y + TILESIZE*2));
		g.drawString("Deffence: " + game.getPlayer().getVSmonster().getDefence(), x, (int) (y + TILESIZE*2.5));

	}

	private void drawHeathBar(int x, int y, int health, int height, Graphics g) {
		
		int count = health/100;
		int lastBarLenth = health%100;
		
		List<Color> c = new ArrayList<Color>();
		c.add(Color.BLUE);
		c.add(Color.RED.darker());
		c.add(Color.ORANGE);
		c.add(Color.YELLOW.darker());
		c.add(Color.GREEN.brighter());
		c.add(Color.CYAN.darker());
		c.add(Color.BLUE);
		c.add(Color.MAGENTA);
		
		for(int i = 0; i<count; i++) {
			c.add(c.get(i).darker());
		}
		
		if(count!=0) {
		g.setColor(c.get(count));
		g.fillRect(x, y, 100, height);
		}
		g.setColor(c.get(count+1));
		g.fillRect(x, y, lastBarLenth, height);
		
		g.setColor(Color.WHITE);
		g.drawString("X"+(count), x+75, y+20);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 100, height);
		
	}

	/**
	 * To draw all the entity in the game board
	 * 
	 * @param board
	 *            the game board in the game
	 * @param g
	 *            Graphics
	 */

	private void drawMap(Board board, Graphics g) {

		for (int x = 0; x < Level.BOARDSIZE; x++) {
			for (int y = 0; y < Level.BOARDSIZE; y++) {

				int py = y * TILESIZE;
				int px = x * TILESIZE;
				int code = 0;

				try {
					if (board.getCurrentLevel().getEntityAt(x, y) != null) {
						code = board.getCurrentLevel().getEntityAt(x, y).getCode();
						if (code >= 90 && code < 98) {// set the monster image in the middle
							code += ac;
							py += 16;
							px += 16;
						} else if (code == 98) {
							code += ac;
						}
						ImageIcon img = new ImageIcon(View.class.getResource("/Entities/" + code + ".png"));
						img.paintIcon(null, g, py, px);
					}
					if (code == 60)
						y += 1;
				} catch (NullPointerException e) {
					System.err.println("NullPointerException: DrawMap image unfind: " + code);

				}
			}
		}

	}

	/**
	 * To draw the floor of the current map, it Equivalent to background image
	 * 
	 * @param g
	 *            Graphics
	 */

	private void drawFloor(Graphics g) {
		for (int x = 0; x < Level.BOARDSIZE; x++) {
			for (int y = 0; y < Level.BOARDSIZE; y++) {
				ImageIcon img = new ImageIcon(View.class.getResource("/Entities/0.png"));
				img.paintIcon(null, g, y * TILESIZE, x * TILESIZE);
			}
		}

	}

	/**
	 * To draw the image for current direction of player facing.
	 * 
	 * @param player
	 * @param g
	 */

	private void drawPlayer(Player player, Graphics g) {
		int x = player.getXPos() * TILESIZE + 16;
		int y = player.getYPos() * TILESIZE + 16;

		switch (game.getPlayer().getFacingDirection()) {// use the image depends on witch direction the player using
		case "up":
			PlayerResources.Up.image.paintIcon(null, g, x, y);
			break;
		case "down":
			PlayerResources.Down.image.paintIcon(null, g, x, y);
			break;
		case "right":
			PlayerResources.Right.image.paintIcon(null, g, x, y);
			break;
		case "left":
			PlayerResources.Left.image.paintIcon(null, g, x, y);
			break;
		default:
			break;
		}
	}
	// ==========getters==========

	public BagPanel getBagPanel() {
		return bagPanel;
	}

	public JPanel getCharacterPanel() {
		return characterPanel;
	}

	public DialogPanel getDialogPanel() {
		return dialogPanel;
	}

	public Game getGame() {
		return game;
	}

	public boolean getGameStop() {
		// TODO Auto-generated method stub
		return this.gameStop;
	}

}
