package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import model.Chess.Type;

/**
 * Is player's chess Board 
 *it will randomly create 24 different chess, and stored in this board
 * @author Jackgan
 *
 */

public class PlayerBoard  extends GameBoard implements ActionListener { 
	public Chess[][] chess = new Chess[8][3];
	public Chess.Type type;
	public static final char ALPHU = 'A'; // alphabet UpCase
	public static final char ALPHL = 'a'; // alphabet LowCase
	public static final int COL =8;
	public static final  int ROW = 3;
	char ch;
	public int c = 0;

	public Timer timer = new Timer(15, this);

	public float alpha = 1.0f;
	
	public PlayerBoard(Chess.Type type) {
		this.type = type;
		this.ch = type == Chess.Type.P1Chess ? ALPHU : ALPHL;
		fillBoard();
		//timer.start();
	}
	
	 public void actionPerformed(ActionEvent e) {
		 
		 	alpha += 0.01f;
		    if (alpha >= 1) {
		    	alpha=1.0f;
		      timer.stop();
		    }
		    setChanged();
		    notifyObservers();
	 }
		 
		 /*
		 if (start < 1) {
		      alpha +=0.01f;
		    }else if (start > 0){
		    	alpha-=0.01f;	
		    }
		// System.out.println(alpha);
		    if (alpha >= 1) {
		      alpha = 1;
		      start=1;
		      timer.stop();
		    }else if (alpha<=0){
		    	alpha=0;
		    	start=0;
		    	timer.stop();
		    }
		    setChanged();
		    notifyObservers();
		  }
		  */
		 
	 
	/**
	 * to fill this board by create random chess
	 * @param chess2 
	 */
	
	public void fillBoard() {
		Chess P1;
		ArrayList<Chess> c = new ArrayList<Chess>();
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				do {
					P1 = new Chess(this.type, ch);
					 //System.out.println(ch);
				} while (duplicate(P1, c));
				chess[i][j] = P1;
				//chess[i][j].setPixel(x,y);
				ch++;
				c.add(P1);
			}
		}
	}
	/**
	 * to compare with the given chess and given ArrayList
	 * is there any chess in the array are equal to the given chess
	 * @param chess the given chess
	 * @param chesslist the given array
	 * @return
	 */

	private boolean duplicate(Chess chess, ArrayList<Chess> chesslist) {
		if (chesslist.isEmpty()) {
			return false;
		}
		for (int i = 0; i < chesslist.size(); i++) {
			if (chess.equals(chesslist.get(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * remove the chess by the given name
	 * @param string the name of the chess
	 * @return True if remove success / otherwise return false
	 */
	 

	public boolean remove(String string) {
		char a = string.charAt(0);
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (chess[i][j].name == a) {
					chess[i][j] = new Chess(Chess.Type.EMPTY);
					setChanged();
					notifyObservers();
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * get the Chess by the given name
	 * @param string the given name
	 * @return true if find the chess / otherwise return false
	 */
	
	public Chess get(String string) {
		char a = string.charAt(0);
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (chess[i][j].name == a) {
					return chess[i][j];
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean selecte(int x, int y, boolean WhiteTurn) {
		Type type = WhiteTurn?Chess.Type.P1Chess:Chess.Type.P2Chess;
		
		for(int i=0; i<COL; i++){
			for(int j=0; j<ROW; j++){				
				this.chess[i][j].selected=false;
			}
		}
		for(int i=0; i<COL; i++){
			for(int j=0; j<ROW; j++){
				
				if( this.chess[i][j].inRange(x, y)&&!chess[i][j].isEmpty()&&chess[i][j].type==type){
				this.chess[i][j].selected=true;
				setChanged();
				notifyObservers();
				return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Chess getSelected() {
		for(int i=0; i<COL; i++){
			for(int j=0; j<ROW; j++){
				if( this.chess[i][j].selected){
					
				return this.chess[i][j];
				}
			}
		}
		return null;
	}
	
	
	public void add(Chess selected) {
		int k = 1;
		for(int i=0; i<COL; i++){
			for(int j=0; j<ROW; j++){
				chess[i][j].deepcolone(selected.rotate(k+""));
				if(k==4){
					selected.rotate("3");
				return;
				}
				k++;
			}
		}
		
	}
	
	
}
