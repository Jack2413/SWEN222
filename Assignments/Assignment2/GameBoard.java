import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameBoard extends JPanel {

	public Chess[][] board = new Chess[10][10];
	public Chess[][] chess = new Chess[10][10];
	public boolean created = false;
	Chess empty = new Chess(Chess.Type.EMPTY);

	public GameBoard() {
		setPreferredSize(getPreferredSize());
		fillBoard(board);
	}

	private void fillBoard(Chess[][] chess2) {

		Chess white = new Chess(Chess.Type.WHITE);
		Chess black = new Chess(Chess.Type.BLACK);
		Chess cyan = new Chess(Chess.Type.CYAN);
		Chess red = new Chess(Chess.Type.RED);
		Chess darkgray = new Chess(Chess.Type.GRAY);

		boolean swap = true;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (swap) {
					board[i][j] = black;
					swap = !swap;
				} else {
					board[i][j] = white;
					swap = !swap;
				}
				if (i < 2 && j < 2 || i > 7 && j > 7) {
					board[i][j] = darkgray;
				}
				chess[i][j] = empty;
			}
			swap = !swap;
		}
		board[2][2] = cyan;
		board[7][7] = red;

	}

	public void paint(Graphics g) {
		// if(T != Chess.Type.EMPTY){
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = Chess.size / 10;
		int y = Chess.size / 10 + Chess.size / 30;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].paint(g, x, y);
				chess[i][j].paint(g, x, y);
				x += Chess.size;
			}
			y += Chess.size;
			x = Chess.size / 10;
		}

	}

	public Dimension getPreferredSize() {
		return new Dimension(Chess.size * 10 + Chess.size / 5, Chess.size * 10 + Chess.size / 5);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	public boolean create(Chess chess2, String degree, boolean WhiteTurn) {
		if(chess2==null)return false;
		
		if (WhiteTurn&&chess[2][2].isEmpty()) {
			chess2.rotate(degree);
			chess[2][2] = chess2;
			created=true;
			return true;
		} else if(!WhiteTurn&&chess[7][7].isEmpty()){
			chess2.rotate(degree);
			chess[7][7] = chess2;
			created=true;
			return true;
		}
		return false;
	}

	public boolean move(String letter, String direction) {
		char ch = letter.charAt(0);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				// System.out.print(chess[i][j].type);
				if (chess[i][j].name == ch) {
					int x = 0, y = 0;
					switch (direction) {
					case "up":
						x = i - 1;
						y=j;
						break;
					case "down":
						x = i + 1;
						y=j;
						break;
					case "left":
						x=i;
						y = j - 1;
						break;
					case "right":
						x=i;
						y = j + 1;
						break;
					default:
						break;
					}
					 if((!((x < 2 && y < 2) || (x > 7 && y >
					7)))&&x<10&&y<10&&x>=0&&y>=0){
					chess[x][y] = chess[i][j];
					chess[i][j] = empty;
					return true;
					 }
				}
			}
			// System.out.println();
		}
		return false;
	}

	public boolean rotate(String letter, String degree) {
		Chess temp = find(letter);
		if(find(letter)!=null){
			temp.rotate(degree);
			return true;
		}
		return false;
	}
	
	public boolean isChessMoved(String letter){
		Chess temp = find(letter);
		if(find(letter)!=null){
			return temp.moved;
		}
		return false;
	}
	public Chess find(String letter){
		char a = letter.charAt(0);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (chess[i][j].name == a) {
					return chess[i][j];
				}}}
		return null;
	}
	
}
