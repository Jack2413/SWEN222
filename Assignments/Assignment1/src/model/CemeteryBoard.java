package model;

import java.util.List;

public class CemeteryBoard extends PlayerBoard {
	
	public CemeteryBoard(Chess.Type type) {
		super(type);
	}
	
	@Override
	public void fillBoard() {
		
		//ArrayList<Chess> c = new ArrayList<Chess>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {		
				chess[i][j] = new Chess(this.type, ch);
			}
		}
	}
	
	public void addDeaths(List<Chess> list){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				if(!list.isEmpty()&&chess[i][j].type==Chess.Type.EMPTY){
				chess[i][j] = list.remove(list.size()-1);
				}
			}
		}
		//System.out.println("inCemtery");
		setChanged();
		notifyObservers();
	}
}
