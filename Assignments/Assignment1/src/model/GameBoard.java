package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.Timer;

import model.Chess.Type;
import resources.SoundResources;

/**
 * 
 * @author Jackgan GameBoard class
 */

public class GameBoard extends Observable implements ActionListener {

	public Chess[][] board = new Chess[10][10];
	private Chess[][] chess = new Chess[10][10];
	public boolean created = false;
	private static final int COL = 10;
	private static final int ROW = 10;
	int time = 20;
	private int speed = 2;
	private String action;
	private Chess animatChess;
	Timer timer = new Timer(time, this);

	Chess empty = new Chess(Chess.Type.EMPTY);
	private List<Chess> deathList1 = new ArrayList<Chess>();
	private List<Chess> deathList2 = new ArrayList<Chess>();
	
	private List<Chess> waitinglist = new ArrayList<Chess>();
	private List<String> actionlist = new ArrayList<String>();
	

	public GameBoard() {
		fillBoard(board);
	}

	/**
	 * to fill the board, it is the initial state for the board
	 * 
	 * @param board
	 *            the new board
	 */

	private void fillBoard(Chess[][] board) {

		boolean swap = true;
		int x = Chess.size / 10;
		int y = Chess.size / 10 + Chess.size / 30;
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (swap) {
					board[i][j] = new Chess(Chess.Type.BROWN);
					swap = !swap;
				} else {
					board[i][j] = new Chess(Chess.Type.LIGHTBROWN);
					swap = !swap;
				}
				if (i == 1 && j == 1) {
					board[1][1] = new Chess(Chess.Type.FACE1);
				} else if (i == 2 && j == 2) {
					board[2][2] = new Chess(Chess.Type.CYAN);
				} else if (i == 7 && j == 7) {
					board[7][7] = new Chess(Chess.Type.RED);
				} else if (i == 8 && j == 8) {
					board[8][8] = new Chess(Chess.Type.FACE2);
				} else if (i < 2 && j < 2 || i > 7 && j > 7) {
					board[i][j] = new Chess(Chess.Type.DARK_GRAY);

				}
				chess[i][j] = new Chess(Chess.Type.EMPTY);
				chess[i][j].setPixel(x, y);
				board[i][j].setPixel(x, y);

				x += Chess.size;
			}

			y += Chess.size;
			x = Chess.size / 10;
			swap = !swap;
		}
	}

	/**
	 * to draw the game board row by row, Eg draw first row of the chess's
	 * attribute for all chess in row1, then draw the second row of the chess's
	 * attribute for all chess in row1.
	 * 
	 */

	public void draw() {

		for (int i = 0; i < COL; i++) {
			for (int k = 0; k < 3; k++) {
				for (int j = 0; j < ROW; j++) {
					chess[i][j].draw(k);
				}
				System.out.println();
			}

		}
	}

	/**
	 * this method is for create a chess on the specified space
	 * 
	 * @param chess2
	 *            the given chess by user
	 * @param degree
	 *            rotate degree
	 * @param WhiteTurn
	 *            boolean is whiteTurn or not
	 * @return true if success created / otherwise return false
	 */

	public boolean create(Chess chess2, boolean WhiteTurn) {

		if (chess2 == null || created) {
			System.out.println(created);
			return false;
		}

		if (WhiteTurn && chess[2][2].isEmpty()) {
			// chess2.rotate(degree);
			chess2.px = chess[2][2].px;
			chess2.py = chess[2][2].py;
			chess[2][2].deepcolone(chess2);
			chess[2][2].setX(2);
			chess[2][2].setY(2);
			chess[2][2].moved = false;
			created = true;

			setChanged();
			notifyObservers();
			return true;
		} else if (!WhiteTurn && chess[7][7].isEmpty()) {
			// chess2.rotate(degree);
			chess2.px = chess[7][7].px;
			chess2.py = chess[7][7].py;
			chess[7][7].deepcolone(chess2);
			chess[7][7].setX(7);
			chess[7][7].setY(7);
			chess[7][7].moved = false;
			created = true;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	/**
	 * move the chess to the given direction and make sure the place has been
	 * moved is inside the board
	 * 
	 * @param letter
	 *            the name of the chess
	 * @param direction
	 *            the moving direction
	 * @return if success moved return true / otherwise return false
	 */

	public boolean move(String letter, String direction) {

		System.out.printf("letter: %s direction: %s\n", letter, direction);
		String str = checkGodir(letter, direction);
		if (str != null) {
			move(str, direction);
		}
		// System.out.printf("Chess: %s move down ",letter);
		char ch = letter.charAt(0);
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				// System.out.print(chess[i][j].type);
				if (chess[i][j].name == ch) {
					int x = 0, y = 0;
					switch (direction) {
					case "up":
						x = i - 1;
						y = j;
						break;
					case "down":
						x = i + 1;
						y = j;
						break;
					case "left":
						x = i;
						y = j - 1;
						break;
					case "right":
						x = i;
						y = j + 1;
						break;
					default:
						break;
					}
					if (inboard(x, y)) {
						

						if (timer.isRunning()) {
							waitinglist.add(chess[i][j]);
							this.action = direction;
							actionlist.add(action);
							
						} else {
							this.action = direction;
							animatChess = chess[i][j];
							System.out.printf("%c run\n",animatChess.name);
							
							timer.start();
							System.out.printf("animation:%c action %s \n",animatChess.name,action);
						}
				

						chess[x][y] = chess[i][j];
						chess[x][y].setX(x);
						chess[x][y].setY(y);
						chess[x][y].moved = true;
						remove(i, j);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		animatChess.startAnimation(action, speed);
		if (animatChess.arrive) {
			timer.stop();
			
			if(action.equals("die")){
				remove(animatChess.getX(),animatChess.getY());
				}else{
					reaction(animatChess.name + "");
				}
			if(!waitinglist.isEmpty()){
				
				animatChess = waitinglist.remove(0);
				action = actionlist.remove(0);
				
				timer.start();
				System.out.printf("animation:%c action %s \n",animatChess.name,action);
			}
			// animatChess.notify();
		}

		setChanged();
		notifyObservers();

	}

	private String checkGodir(String letter, String direction) {
		Chess chess1 = find(letter);
		System.out.println(chess1 == null);
		int x = chess1.getX();
		int y = chess1.getY();
		switch (direction) {
		case "up":
			x -= 1;
			break;
		case "down":
			x += 1;
			break;
		case "left":
			y -= 1;
			break;
		case "right":
			y += 1;
			break;

		}
		if (!inboard(x, y)) {
			return null;
		}
		if (!chess[x][y].isEmpty()) {
			System.out.printf("MyNextName: %c\n", chess[x][y].name);
			return chess[x][y].name + "";

		} else {
			return null;
		}
	}

	private boolean inboard(int x, int y) {
		return ((!((x < 2 && y < 2) || (x > 7 && y > 7))) && x < 10 && y < 10 && x >= 0 && y >= 0);

	}

	/**
	 * to rotate the chess
	 * 
	 * @param leteer
	 *            name of the chess
	 * @param degree
	 *            <1/2/3/4> = <0/90/180/270>
	 * @return if find the chess in board and rotates
	 */
	public boolean rotate(String letter, String degree) {
		Chess temp = find(letter);
		if (find(letter) != null) {
			temp.rotate(degree);
			temp.moved = true;
			// System.out.printf("letter: %s reaction
			// %s",letter,reaction(letter));
			return true;
		}
		return false;
	}

	/**
	 * to check the current chess moved or not
	 * 
	 * @param letter
	 *            the name of the chess
	 * @return if the chess moved return true / otherwise return false
	 */

	public boolean isChessMoved(String letter) {
		Chess temp = find(letter);
		if (find(letter) != null) {
			return temp.moved;
		}
		return false;
	}

	/**
	 * find the Chess by the given name
	 * 
	 * @param leteer
	 *            name of the chess
	 * @return if find the chess return true / otherwise return false
	 */

	public Chess find(String letter) {
		char a = letter.charAt(0);
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (chess[i][j].name == a) {
					return chess[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * reset the board initial to before this turn start just change the boolean
	 * created and chesses moved to be fasle; the chess keep in the same
	 * position
	 * 
	 */

	public void reset() {
		this.created = false;
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				chess[i][j].moved = false;
			}
		}
	}

	/**
	 * To check the reactions around the selected chess <lefe/up/down/right>
	 * side
	 * 
	 * @param c
	 *            the selected chess
	 * @return An Map that contains the sides which has the Reactions with, and
	 *         the reaction chess
	 */

	public Map<Chess, String> checkReaction(Chess c) {

		Map<Chess, String> map = new HashMap<Chess, String>();
		// System.out.println(c.getY());
		int x = c.getX(); // index of selected chess
		int y = c.getY();// index of selected chess
		Chess current = chess[x][y]; // to select the current chess
		// System.out.printf("Error: %d%d\n",x,y);

		if (inboard(x - 1, y)) {
			if ((current.getAttribute(0, 1) == '|' || chess[x - 1][y].getAttribute(2, 1) == '|')
					&& !chess[x - 1][y].isEmpty()) {
				map.put(chess[x - 1][y], "up");
			}
		}
		if (inboard(x, y + 1)) {
			if ((current.getAttribute(1, 2) == '|' || chess[x][y + 1].getAttribute(1, 0) == '|')
					&& !chess[x][y + 1].isEmpty()) {
				map.put(chess[x][y + 1], "right");
			}
		}
		if (inboard(x + 1, y)) {
			if ((current.getAttribute(2, 1) == '|' || chess[x + 1][y].getAttribute(0, 1) == '|')
					&& !chess[x + 1][y].isEmpty()) {
				map.put(chess[x + 1][y], "down");
			}
		}
		if (inboard(x, y - 1)) {
			if ((current.getAttribute(1, 0) == '|' || chess[x][y - 1].getAttribute(1, 2) == '|')
					&& !chess[x][y - 1].isEmpty()) {
				map.put(chess[x][y - 1], "left");
			}
		}
		return map;

	}

	/**
	 * To parse the reaction, then design (execute or exit)
	 * 
	 * @param letter
	 *            A letter form the comment
	 * @return true if there have any reaction, if no reactions return false
	 */

	public boolean reaction(String letter) {
		Chess current = find(letter);
		boolean temp = current.moved;
		Map<Chess, String> map = checkReaction(current);
		// System.out.println(1);
		if (map.isEmpty()) {

			return false;
		} else if (map.size() == 1) {
			for (Chess reactionChess : map.keySet()) {
				boolean temp2 = reactionChess.moved;
				// System.out.println(2);
				executeReaction(current, map.get(reactionChess), reactionChess);
				// reaction(reactionChess.name + "");
				reactionChess.moved = temp2;
			}

		} else {
			/*
			 * //while (map.size() > 0) { while(true){
			 * if(map.keySet().size()>1){ System.out.
			 * println("Now have mutiple reaction: Please choose the reaction: "
			 * ); Scanner scanner = new Scanner(System.in); String str =
			 * scanner.nextLine(); Chess reactionChess = find(str.substring(1));
			 * boolean temp2 = reactionChess.moved; executeReaction(current,
			 * map.get(reactionChess), reactionChess);
			 * map.keySet().remove(reactionChess); reactionChess.moved = temp2;
			 * }else{ Chess reactionChess2 = (Chess) map.keySet().toArray()[1];
			 * boolean temp2 = reactionChess2.moved; executeReaction(current,
			 * map.get(reactionChess2), reactionChess2); reactionChess2.moved =
			 * temp2; break; }
			 */
			for (Chess reactionChess : map.keySet()) {
				boolean temp2 = reactionChess.moved;
				// System.out.println(2);
				executeReaction(current, map.get(reactionChess), reactionChess);
				// reaction(reactionChess.name + "");
				reactionChess.moved = temp2;
			}

			// if (reactionChess.name == ch) {

			// reaction(reactionChess.name + "");
			// map.remove(reactionChess);
			// else {
			// System.out.println("can't find the reaction pleause enter agian:
			// ");

			// }
		}

		current.moved = temp;
		return true;
	}

	/**
	 * To execute the Reactions
	 * 
	 * @param currentChess
	 * @param direction
	 * @param reactionChess
	 */

	private void executeReaction(Chess currentChess, String direction, Chess reactionChess) {
		int x = currentChess.getX();
		int y = currentChess.getY();
		int rx = 0, ry = 0;
		char CW = ' '; // currentWeapon
		char RW = ' '; // relactionWeapon

		// System.out.printf("CW: %c X: %d Y: %d\n",CW,x,y);
		// System.out.printf("RW: %c RX: %d RY: %d\n",RW,rx,ry);

		switch (direction) {
		case "up":
			CW = chess[x][y].getAttribute(0, 1);
			RW = chess[x - 1][y].getAttribute(2, 1);
			rx = x - 1;
			ry = y;
			break;
		case "down":
			CW = chess[x][y].getAttribute(2, 1);
			RW = chess[x + 1][y].getAttribute(0, 1);
			rx = x + 1;
			ry = y;
			break;
		case "left":
			CW = chess[x][y].getAttribute(1, 0);
			RW = chess[x][y - 1].getAttribute(1, 2);
			rx = x;
			ry = y - 1;
			break;
		case "right":
			CW = chess[x][y].getAttribute(1, 2);
			RW = chess[x][y + 1].getAttribute(1, 0);
			rx = x;
			ry = y + 1;

			break;
		}
		// System.out.printf("CW: %c X: %d Y: %d\n",CW,x,y);
		// System.out.printf("RW: %c RX: %d RY: %d\n",RW,rx,ry);

		if (CW == '|' && RW == '|') {
			die(rx, ry);
			die(x, y);
		} else if (CW == '|' && RW == ' ') {
			die(rx, ry);
		} else if (CW == ' ' && RW == '|') {
			die(x, y);
		} else if (CW == '|' && RW == '#') {
			move(chess[x][y].name + "", oppsDirction(direction));
		} else if (CW == '#' && RW == '|') {
			move(chess[rx][ry].name + "", direction);
		}

	}

	private void die(int x, int y) {
		
		SoundResources.Break.sound.play();
		if (timer.isRunning()) {
			waitinglist.add(chess[x][y]);
			actionlist.add("die");
		} else {
			animatChess = chess[x][y];
			action = "die";
			timer.start();
		}
		
		if (chess[x][y].type == Chess.Type.P1Chess) {
			deathList1.add(chess[x][y]);
		} else {
			deathList2.add(chess[x][y]);
		}
		//remove(x, y);
	}

	private String oppsDirction(String direction) {
		switch (direction) {
		case "up":
			return "down";
		case "down":
			return "up";
		case "left":
			return "right";
		case "right":
			return "left";
		default:
			return "";
		}
	}

	/**
	 * to move the gameBoard[][]
	 * 
	 * @param i
	 *            the row position for the gameBoard
	 * @param j
	 *            the col position for the gameBoard
	 */
	private void remove(int i, int j) {
		int tempPx = chess[i][j].px;
		int tempPy = chess[i][j].py;

		chess[i][j] = new Chess(Chess.Type.EMPTY);
		if (i == 2 && j == 2) {
			chess[i][j] = new Chess(Chess.Type.EMPTY);
		} else if (i == 7 && j == 7) {
			chess[i][j] = new Chess(Chess.Type.EMPTY);
		}

		chess[i][j].px = tempPx;
		chess[i][j].py = tempPy;

	}

	public void deepclone2(GameBoard gameboard) {
		// System.out.println(222222);
		this.created = gameboard.created;
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				chess[i][j].deepcolone(gameboard.chess[i][j]);
				setChanged();
				notifyObservers();
			}
		}

	}

	public String checkWin() {
		int x, y;
		String str;
		x = 1;
		y = 1;

		if (this.chess[x + 1][y].getAttribute(0, 1) == '|' || this.chess[x][y + 1].getAttribute(1, 0) == '|') {

			return str = "black";
		}
		x = 8;
		y = 8;
		if (this.chess[x - 1][y].getAttribute(2, 1) == '|' || this.chess[x][y - 1].getAttribute(1, 2) == '|') {
			return str = "white";
		}
		return null;

	}

	/**
	 * to reset the selections turn back to default false then check the
	 * position (x,y) is it chick on the chess ? mark the chess if it chick on
	 * the chess
	 * 
	 * @param x
	 *            COL on the game board
	 * @param y
	 *            ROW on the game board
	 * @param WhiteTurn
	 *            send a boolean is this white turn
	 * @return the resolute for selected or not
	 */

	public boolean selecte(int x, int y, boolean WhiteTurn) {
		Type type = WhiteTurn ? Chess.Type.P1Chess : Chess.Type.P2Chess;

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				this.chess[i][j].selected = false;

			}
		}
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				if (this.chess[i][j].inRange(x, y) && !chess[i][j].isEmpty() && chess[i][j].type == type) {
					this.chess[i][j].selected = true;
					setChanged();
					notifyObservers();
					return true;
				}
			}
		}
		return false;
	}

	public Chess getSelected() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (this.chess[i][j].selected) {
					// System.out.println(chess[i][j].name);
					return this.chess[i][j];
				}
			}
		}
		return null;
	}

	public Chess getchess(int i, int j) {
		return chess[i][j];
	}

	public List<Chess> getdeathlist1() {
		return deathList1;
	}

	public List<Chess> getdeathlist2() {
		return deathList2;
	}

}
