package SwordAndShell;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observer;


import model.Chess;
import model.PlayerBoard;

public class PlayerView extends BoardView implements Observer {

	
	  
	private static final long serialVersionUID = 1L;
	PlayerBoard Playerboard;

	public PlayerView(PlayerBoard playerboard) {
		super(playerboard);
		this.Playerboard = playerboard;
		this.Playerboard.addObserver(this);
		this.setFocusable(true);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Playerboard.alpha));

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = Chess.size / 10;
		int y = Chess.size / 10 + Chess.size / 30;
		for (int i = 0; i <PlayerBoard.COL ; i++) {
			for (int j = 0; j < PlayerBoard.ROW; j++) {

				drawchess(Playerboard.chess[i][j], g, x, y,size);
				x += Chess.size + (Chess.size / 8);
			}
			y += Chess.size + (Chess.size / 6);
			x = Chess.size / 10;
		}

	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(size * 3 + size / 2, size * 5);
	}
	
	
	
}
