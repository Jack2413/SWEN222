package SwordAndShell;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Chess;
import model.GameBoard;

public class AnimationView extends BoardView {

	private static final long serialVersionUID = 1L;

	public AnimationView(GameBoard gameboard) {
		super(gameboard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintComponent(Graphics _g) {

		int newi = 0;
		int newj = 0;
		int newx = 0;
		int newy = 0;

		// System.out.println("used");
		Graphics2D g = (Graphics2D) _g.create();
		g.setComposite(AlphaComposite.SrcOver.derive(0.3f));

		g.setColor(Color.darkGray);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = Chess.size / 10;
		int y = Chess.size / 10 + Chess.size / 30;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				drawchess(Gameboard.board[i][j], g, x, y, size);
				if (Gameboard.getchess(i, j).selected) {
					newj = j;
					newi = i;
					newx = x;
					newy = y;
				} else {
					drawchess(Gameboard.getchess(i, j), g, x, y, size);
				}
				Gameboard.getchess(i, j).setX(i);
				Gameboard.getchess(i, j).setY(j);
				// System.out.println(1);
				x += Chess.size;
			}
			y += Chess.size;
			x = Chess.size / 10;
		}
		if(Gameboard.getchess(newi, newj).selected){
		drawchess(Gameboard.getchess(newi, newj), _g, newx - 10, newy - 10, size + 20);
		}
	}
}
