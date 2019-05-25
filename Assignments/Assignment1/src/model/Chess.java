package model;

import java.awt.Dimension;
import java.util.Observable;
import java.awt.Rectangle;

/**
 * Chess class everything is build by Chess
 * 
 * @author Jackgan
 *
 */
public class Chess extends Observable {

	public final static int size = 70;

	public enum Type {
		RED, FACE1, FACE2, P1Chess, CYAN, P2Chess, EMPTY, LIGHTBROWN, BROWN, DARK_GRAY;

	}

	public Type type;
	public char[][] attribute;
	private char ch;
	private int x, y;
	private Rectangle rect;
	private Rectangle left, right, top, bot;
	public boolean moved = false;
	public char name;
	public boolean selected;
	public boolean arrive = false;
	public int px;
	public int py;
	public int runsofar = 0;

	// private char[][] attribute;
	/**
	 * to declare type, name and attribute
	 * 
	 * @param type
	 *            Chess type
	 * @param ch
	 *            the name of the Chess player1[A-X] player2[a-x]
	 */
	public Chess(Type type, char ch) {
		this.type = type;
		this.ch = ch;
		this.moved = false;
		attribute = new char[3][3];
		selected = false;
		Fillboard();

	}

	public Chess(Type type) {
		this.type = type;
		attribute = new char[3][3];
		selected = false;
		Fillboard();

	}

	/**
	 * each chess have 4 side left top right bottom, and every side have 1
	 * weapons or none this method is to fill 4 sides of the chess '|' = sword
	 * '#' = shield ' ' = nothing
	 * 
	 * @param attribute
	 *            chess's attribute
	 */

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
		}

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

	/**
	 * I use the different chess to make a game board, because it is easy to
	 * draw and make changes the game board is make by 10x10 chess now just fill
	 * the attributes for the chess that use to make gameboard
	 */

	public void Fillboard() {
		char fill = ' ';

		if (this.type == Type.LIGHTBROWN) {
			fill = '*';
		} else if (this.type == Type.BROWN) {
			fill = '$';

		} else if (this.type == Type.FACE1) {
			fill = '1';
		} else if (this.type == Type.FACE2) {
			fill = '2';
		} else if (this.type == Type.P1Chess) {
			FillAttribute(this.attribute);
			return;
		} else if (this.type == Type.P2Chess) {
			FillAttribute(this.attribute);
			return;
		} else if (this.type == Type.EMPTY) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// char c =(char) ((j+1)+'0');
					this.attribute[i][j] = '@';
				}
			}
			return;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.attribute[i][j] = fill;
			}
		}
	}

	/**
	 * print out the whole row of the attribute by given column index
	 * 
	 * @param i
	 *            the given column index of the attribute
	 */

	public void draw(int i) {
		for (int j = 0; j < 3; j++) {
			if (attribute[i][j] == '|' && (i == 1)) {// at col two of the
														// attribute if have
														// sword print like '-'
				System.out.printf("%-2c", '-');
			} else {
				System.out.printf("%-2c", attribute[i][j]);
			}
		}
	}

	/**
	 * to compare with this chess and the given chess is there are the same?
	 * 
	 * @param c1
	 *            the given chess
	 * @return true if is the same / otherwise return fasle
	 */

	public boolean equals(Chess c1) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (c1.attribute[i][j] != this.attribute[i][j]) {
					// System.out.println(c1.attribute[i][j]);
					// System.out.println(this.attribute[i][j]);
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

	/**
	 * when the chess rotate, it have to swap it's attribute
	 * 
	 * @param number
	 *            <1/2/3/4> = <0/90/180/270> degree
	 * @return
	 */

	public Chess rotate(String number) {
		int degree = Integer.parseInt(number);
		char temp = attribute[0][1];
		switch (degree) {
		case 1:

			return this;
		case 2:
			attribute[0][1] = attribute[1][0];
			attribute[1][0] = attribute[2][1];
			attribute[2][1] = attribute[1][2];
			attribute[1][2] = temp;
			return this;
		case 3:
			attribute[0][1] = attribute[2][1];
			char temp2 = attribute[1][0];
			attribute[1][0] = attribute[1][2];
			attribute[1][2] = temp2;
			attribute[2][1] = temp;
			return this;
		case 4:
			attribute[0][1] = attribute[1][2];
			attribute[1][2] = attribute[2][1];
			attribute[2][1] = attribute[1][0];
			attribute[1][0] = temp;
			return this;
		}
		return this;
	}

	/**
	 * 
	 * 
	 * @return boolean if this chess is empty
	 */

	public boolean isEmpty() {
		return this.type != Chess.Type.P1Chess && this.type != Chess.Type.P2Chess;
	}

	/**
	 * get attributes by given index and returns
	 * 
	 * @param x
	 *            given index x
	 * @param y
	 *            given index y
	 * @return attribute
	 */

	public char getAttribute(int x, int y) {
		return attribute[x][y];
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setAttribute(char a, char b, char c, char d) {
		this.attribute[0][1] = a;
		this.attribute[1][2] = b;
		this.attribute[2][1] = c;
		this.attribute[1][0] = d;

	}

	public void deepcolone(Chess chess) {

		this.type = chess.type;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.attribute[i][j] = chess.attribute[i][j];
			}
		}
		this.ch = chess.ch;
		this.moved = chess.moved;
		this.name = chess.name;

	}

	public void setRange(int x, int y, int size, int size2) {
		int new_X = x;
		int new_Y = y;
		rect = new Rectangle(new_X, new_Y, size, size2);
		top = new Rectangle(new_X, new_Y, size, size / 10);
		new_X = (x + size - size / 10);
		new_Y = y;
		right = new Rectangle(new_X, new_Y, size / 10, size);
		new_X = x;
		new_Y = y + (size - size / 10);
		bot = new Rectangle(new_X, new_Y, size, size / 10);
		new_X = x;
		new_Y = y;
		left = new Rectangle(new_X, new_Y, size / 10, size);
	}

	public boolean inRange(int x, int y) {
		if (this.rect == null)
			return false;
		if (rect.contains(x, y)) {
			return true;
		}
		return false;
	}

	public String check(int x2, int y2) {
		// System.out.printf("X: %d Y :%d TX:%d TY:%d TW%d TH%d",x,y);
		if (top.contains(x2, y2)) {
			return "up";
		} else if (right.contains(x2, y2)) {
			return "right";
		} else if (bot.contains(x2, y2)) {
			return "down";
		} else if (left.contains(x2, y2))
			return "left";
		else {
			return "rotate";
		}

	}

	public void setPixel(int x2, int y2) {
		this.px = x2;
		this.py = y2;

	}

	public int getPx() {
		return this.px;
	}

	public int getPy() {
		return this.py;
	}

	public void startAnimation(String action, int speed) {
		//System.out.println(direction);
		
		
		
		arrive=false;
		runsofar+=speed;
		switch(action){
		case"up":this.py-=speed; break;
		case"down":this.py+=speed; break;
		case"left":this.px-=speed; break;
		case"right":this.px+=speed; break;
		case"die":this.rotate("2"); break;
		}
		
		if(runsofar>=size){
			runsofar=0;
			arrive=true;
		}
		
		//System.out.printf("(runsofar:%d X:%d Y:%d)",runsofar,this.px,this.py);
		
		
	}
}
