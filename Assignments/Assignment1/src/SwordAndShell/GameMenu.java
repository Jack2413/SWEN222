package SwordAndShell;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import model.Chess;
import resources.ImgResources;

public class GameMenu extends JComponent {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public GameMenu(GUI gui) {
		
		JButton newGame = new JButton("New Game");
		JButton info = new JButton("Info");
		JButton quit = new JButton("Quit");
		newGame.addActionListener((e) -> {
		
			gui.cardlayout3.next(gui.jp);

		});
		quit.addActionListener((e) -> {
			
			System.exit(0);

		});
		info.addActionListener((e) -> {
			
			gui.getInfo();

		});

		this.setLayout(new GridLayout(0, 1, 0, 100));
		this.setBorder(new EmptyBorder(250, 600, 250, 600));
		Insets margin = new Insets(20, 150, 20, 150);

		newGame.setMargin(margin);
		info.setMargin(margin);
		quit.setMargin(margin);
		
		//newGame.setBorder(null);
		//newGame.setOpaque(false);
		//newGame.setContentAreaFilled(false);
		
		//newGame.setBorderPainted(false);
		//button.setBorderPainted(false);

		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(newGame);
		this.add(info);
		this.add(quit);

		this.setSize(Chess.size * 5, Chess.size * 5);
		setFocusable(true);

		setVisible(true);
	}

	

	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D) _g;

		g.drawImage(ImgResources.BackGroud.img, 0, 0, this.getWidth(), this.getHeight(), null);

	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

}
