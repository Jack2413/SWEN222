package SwordAndShell;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import model.Chess;
import model.GameBoard;
import resources.ImgResources;


public class BoardView extends JComponent implements Observer {

	
		  private static final long serialVersionUID = 1L;
		  public final static int size = 70;
		  GameBoard Gameboard;
		
		  
		  public BoardView(GameBoard gameboard) {
			this.Gameboard = gameboard;
		    this.Gameboard.addObserver(this);
		    this.setFocusable(true);    
		  }
		  
   
	public void update(Observable o, Object arg) {
		//System.out.print(1);
		
		repaint();
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//System.out.println("BoardView printing");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				drawchess(Gameboard.board[i][j],g, Gameboard.board[i][j].getPx(), Gameboard.board[i][j].getPy(),size);
						
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				drawchess(Gameboard.getchess(i,j),g, Gameboard.getchess(i,j).getPx(), Gameboard.getchess(i,j).getPy(),size);
				Gameboard.getchess(i,j).setX(i);
				Gameboard.getchess(i,j).setY(j);
			}
		}
	}


	protected void drawchess(Chess chess, Graphics g, int x, int y,int size) {	
		//if(chess==null)return;
		chess.setRange(x, y, size, size);
		switch(chess.type){
		case EMPTY: return;
		case BROWN: g.setColor(new Color(106,57,6)); break;
		case LIGHTBROWN: g.setColor(new Color(207,169,114)); break;
		case CYAN: g.setColor(Color.CYAN); break;
		case RED:g.setColor(Color.RED); break;
		case DARK_GRAY:g.setColor(Color.DARK_GRAY);break;
		case P1Chess:
			g.setColor(Color.WHITE);
			g.fillRect(x, y, size, size);
			g.setColor(Color.CYAN);
			g.fillOval(x + size / 20, y + size / 20, size - size / 10, size - size / 10);
			drawAttribute(chess, g, x, y,size);
			break;
		case P2Chess:
			g.setColor(Color.BLACK);
			g.fillRect(x, y, size, size);
			g.setColor(Color.RED);
			g.fillOval(x + size / 20, y + size / 20, size - size / 10, size - size / 10);
			drawAttribute(chess, g, x, y,size);
			break;
		case FACE1:
			g.drawImage(ImgResources.White.img, x, y, size, size,null);
			//System.out.println(1);
			break;
		case FACE2:
			g.drawImage(ImgResources.Black.img, x, y, size, size,null);
			//System.out.println(1);
			break;
		default:
			break;
		}
		if(chess.type!=Chess.Type.P2Chess&&chess.type!=Chess.Type.P1Chess&&chess.type!=Chess.Type.FACE1&&chess.type!=Chess.Type.FACE2){	
		g.fillRect(x, y, size, size);
		}
		if(chess.selected){
			g.setColor(Color.green);
			g.drawRect(x-1, y-1, size+2, size+2);
			g.drawRect(x-2, y-2, size+4, size+4);
		}
		if(chess.moved){
			g.setColor(Color.red);
			g.drawRect(x-1, y-1, size+2, size+2);
			g.drawRect(x-2, y-2, size+4, size+4);
		}
	}


	private void drawAttribute(Chess chess, Graphics g, int x, int y, int size) {
		g.setColor((chess.type == Chess.Type.P1Chess) ? Color.BLACK : Color.WHITE);
		int new_X = x;
		int new_Y = y;

		switch (chess.attribute[0][1]) {
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
		switch (chess.attribute[1][2]) {
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

		switch (chess.attribute[2][1]) {
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

		switch (chess.attribute[1][0]) {
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
	public Dimension getPreferredSize() {return new Dimension(size*11, size*11);}

}
