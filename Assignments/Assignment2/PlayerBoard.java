import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public class PlayerBoard {
	public Chess[][] chess;
	public Chess.Type type;
	public static final char ALPHU = 'A'; // alphabet UpCase
	public static final char ALPHL = 'a'; // alphabet LowCase
	char ch;

	public PlayerBoard(int col, int len, Chess.Type type) {
		// super(color, dimension);
		this.type = type;
		this.ch = type == Chess.Type.P1Chess ? ALPHU : ALPHL;
		this.chess = new Chess[col][len];
		fillBoard(chess);
	}

	private void fillBoard(Chess[][] chess2) {
		Chess P1;
		ArrayList<Chess> c = new ArrayList<Chess>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				do {
					P1 = new Chess(this.type, ch);
					// System.out.printf("A: %s\n",duplicate(P1, c));
				} while (duplicate(P1, c));
				chess[i][j] = P1;
				ch++;
				c.add(P1);
			}
		}
	}

	private boolean duplicate(Chess p1, ArrayList<Chess> c) {
		if (c.isEmpty()) {
			return false;
		}
		for (int i = 0; i < c.size(); i++) {
			if (p1.equals(c.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean remove(String string) {
		char a = string.charAt(0);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				if (chess[i][j].name == a) {
					chess[i][j] = new Chess(Chess.Type.EMPTY);
					return true;
				}
			}
		}
		return false;
	}

	public Chess get(String string) {
		char a = string.charAt(0);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				if (chess[i][j].name == a) {
					return chess[i][j];
				}
			}
		}
		return null;
	}
}
