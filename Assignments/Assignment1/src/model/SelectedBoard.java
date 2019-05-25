package model;


public class SelectedBoard extends PlayerBoard {
	
	
	
	public static final char ALPHU = 'A'; // alphabet UpCase
	public static final char ALPHL = 'a'; // alphabet LowCase
	public static final int COL =8;
	public static final int ROW = 3;
	
	//public Chess[][] chess = new Chess[2][3];
	public int c = 0;
	public SelectedBoard(Chess.Type type) {
		super(type);
		//this.ch = type == Chess.Type.P1Chess ? ALPHU : ALPHL;
		//fillBoard();
		
	}
	@Override
	public void fillBoard() {
		
		//ArrayList<Chess> c = new ArrayList<Chess>();
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {		
				chess[i][j] = new Chess(this.type, ch);
			}
		}
	}
	
}