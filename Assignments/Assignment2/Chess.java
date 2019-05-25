import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.Array;

import javax.swing.JPanel;

public class Chess extends JPanel {

	public final static int size = 70;

	public enum Type {
		WHITE, BLACK, GRAY, RED, FACE1, FACE2, P1Chess, CYAN, P2Chess, EMPTY;

	}

	public Type type;
	private char[][] attribute;
	private char ch;
	public boolean moved;
	
	public char name;
	
	// private char[][] attribute;

	public Chess(Type type, char ch) {
		this.type = type;
		this.ch = ch;
		this.moved = false;
		attribute = new char[3][3];
		FillAttribute(this.attribute);
		setPreferredSize(getPreferredSize());
	}

	public Chess(Type type) {
		this.type = type;
		setPreferredSize(getPreferredSize());
	}

	private void FillAttribute(char[][] attribute) {

		char[] att = new char[4];
		for (int i = 0; i < 4; i++) {
			int num = (int) (Math.random() * 3);
			if (num == 0) {
				att[i] = '#';
			} else if (num == 1) {
				att[i] = '|';
			} else {
				att[i] = ' ';
			}
			// System.out.printf("%c", att[i]);
		}
		// System.out.printf("\n");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				attribute[i][j] = ' ';
			}
		}

		attribute[0][1] = att[0];
		attribute[1][2] = att[1];
		attribute[2][1] = att[2];
		attribute[1][0] = att[3];
		attribute[1][1] = ch;
		this.name = ch;

	}

	public void paint(Graphics g, int x, int y) {

		if (this.type == Type.WHITE) {
			g.setColor(new Color(207,169,114));
		} else if (this.type == Type.BLACK) {
			g.setColor(new Color(106,57,6));
		} else if (this.type == Type.CYAN) {
			g.setColor(Color.CYAN);
		} else if (this.type == Type.RED) {
			g.setColor(Color.RED);
		} else if (this.type == Type.GRAY) {
			g.setColor(Color.DARK_GRAY);
		} else if (this.type == Type.P1Chess) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, size, size);
			g.setColor(Color.CYAN);
			g.fillOval(x + size / 20, y + size / 20, size - size / 10, size - size / 10);
			drawAttribute(this.attribute, g, x, y);
			//g.setColor(Color.CYAN);
			//g.drawRect(x, y, size, size);
			
			return;
		} else if (this.type == Type.P2Chess) {
			g.setColor(Color.BLACK);
			g.fill3DRect(x, y, size, size,true);
			g.setColor(Color.RED);
			g.fillOval(x + size / 20, y + size / 20, size - size / 10, size - size / 10);
			drawAttribute(this.attribute, g, x, y);
			//g.setColor(Color.RED);
			//g.drawRect(x, y, size, size);
			
			
			return;
		} else if (this.type == Type.EMPTY) {
			return;
		}
		g.fillRect(x, y, size, size);

	}

	private void drawAttribute(char[][] attribute, Graphics g, int x, int y) {
		g.setColor((this.type == Type.P1Chess) ? Color.BLACK : Color.WHITE);
		int new_X = x;
		int new_Y = y;

		switch (attribute[0][1]) {
		case ('|'):
			new_X = x + (size / 2 - size / 20);
			g.fillRect(new_X, new_Y, size / 10, size / 2);
			break;
		case ('#'):
			g.fillRect(new_X, new_Y, size, size / 10);
			break;
		default:
			break;
		}
		switch (attribute[1][2]) {
		case ('|'):
			new_X = x + (size / 2);
			new_Y = y + (size / 2 - size / 20);
			g.fillRect(new_X, new_Y, size / 2, size / 10);
			break;
		case ('#'):
			new_X = (x + size - size / 10);
			new_Y = y;
			g.fillRect(new_X, new_Y, size / 10, size);
			break;
		default:
			break;
		}

		switch (attribute[2][1]) {
		case ('|'):
			new_X = x + (size / 2 - size / 20);
			new_Y = y + (size / 2);
			g.fillRect(new_X, new_Y, size / 10, size / 2);
			break;
		case ('#'):
			new_X = x;
			new_Y = y + (size - size / 10);
			g.fillRect(new_X, new_Y, size, size / 10);
			break;
		default:
			break;
		}

		switch (attribute[1][0]) {
		case ('|'):
			new_X = x;
			new_Y = y + (size / 2 - size / 20);
			g.fillRect(new_X, new_Y, size / 2, size / 10);
			break;
		case ('#'):
			new_X = x;
			new_Y = y;
			g.fillRect(new_X, new_Y, size / 10, size);
			break;
		default:
			break;
		}
	}

	public boolean equals(Chess c1) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (c1.attribute[i][j] != this.attribute[i][j]) {
					return false;
				}
			}
		}
		return true;
		// return c1.attribute==this.attribute;
	}

	public Dimension getPreferredSize() {
		return new Dimension(size * 10, size * 10);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void rotate(String number) {
		int degree = Integer.parseInt(number);
		char temp = attribute[0][1];
		switch (degree) {
		case 1:
			return;
		case 2:
			attribute[0][1] = attribute[1][0];
			attribute[1][0] = attribute[2][1];
			attribute[2][1] = attribute[1][2];
			attribute[1][2] = temp;
			return;
		case 3:
			attribute[0][1] = attribute[2][1];
			char temp2 = attribute[1][0];
			attribute[1][0] = attribute[1][2];
			attribute[1][2] = temp2;
			attribute[2][1] = temp;
			return;
		case 4:
			attribute[0][1] = attribute[1][2];
			attribute[1][2] = attribute[2][1];
			attribute[2][1] = attribute[1][0];
			attribute[1][0] = temp;
			return;
		}
	}

	public boolean isEmpty() {
		return this.type == Type.EMPTY;
	}
}
