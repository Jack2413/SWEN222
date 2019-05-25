package Board;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import character.*;
import gui.View;
import item.*;

/**
 * The basic of Level is 2D-array of Entity. Each entity would instance as different things such as wall and ground.
 * @author tian
 *
 */
public class Level {
	public static final int BOARDSIZE = 12;
	private Entity entities[][];
	private int floor;
	private Shop SType;
	private Temple TType0,TType1,TType2;

	public Level(int floor) {
		this.floor = floor;
		this.entities = new Entity[BOARDSIZE][BOARDSIZE];
		this.SType = new Shop();
		this.TType0 = new Temple();
		this.TType1 = new Temple();
		this.TType2 = new Temple();

	}

	//=========================================== Return Method ========================================================	
	/**
	 * if pick the item on the ground then set ground into normal ground
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean pickItem(int x, int y){
		if(this.entities[x][y] instanceof Ground){
			return ((Ground) this.entities[x][y]).pickItem();
		}
		return false;
	}

	/**
	 * if break the wall then set normal ground into this coords of entities
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean breakWall(int x, int y){
		if(this.entities[x][y] instanceof Wall){
			if( ((Wall) this.entities[x][y]).isBreakable()){
				this.entities[x][y] = new Ground(00,x,y,View.TILESIZE);
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for reset the door to normal ground
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean openTheDoor(int x,int y){
		if ( this.entities[x][y] instanceof Door ){
			this.entities[x][y] = new Ground(00,x,y,View.TILESIZE);
			return true;
		}
		return false;
	}

	/**
	 * what is the number of this level
	 * 
	 * @return
	 */
	public int getfloor() {return this.floor;}


	/**
	 * return Entity at Given Position
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Entity getEntityAt(int x, int y) {return entities[x][y];}

	/**
	 * return the array of entities
	 * @return
	 */
	public Entity[][] getEntities() {return entities;}
	public Shop getshop() {return this.SType;}
	public Temple getTemple() {return this.TType0;}

	//============================================= initialize =======================================================
	/**
	 * Parser a  12*12 board
	 * @param sc
	 * @throws InvalidFileException 
	 */
	public void parserLevel(Scanner sc) throws InvalidFileException{
		int count = 0;
		sc.next();           //consume (
		for(int i = 0; i < BOARDSIZE; i++ ){
			for(int c = 0; c < BOARDSIZE; c++ ){
				int entry = sc.nextInt();
				if(sc.hasNextInt()) count++;
				
				addEntity(entry,i,c,View.TILESIZE);
			}
		}
		
		if(count!=143) throw new InvalidFileException("invalid file");	
		sc.next();            //consume )
	}

	/**
	 * Add entities in this level
	 * @param <T>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void addEntity(int code, int x, int y, int size) {
		// from 00 ~09  Ground
		if(code < 10){
			Entity Ground = new Ground(code,x,y,size);
			this.entities[x][y] = Ground;
			//from 10 ~ 19  Wall
		}else if(code >= 10 && code < 20){
			Entity Wall = new Wall(code,x,y,size);
			this.entities[x][y] = Wall;
			//from 20 ~ 30 Door	
		}else if(code >= 20 && code < 30){
			Entity D = new Door(code,x,y,size);
			this.entities[x][y] = D;
			//from 30 ~ 50 Item	
		}else if(code >= 30 && code < 50){
			Entity GroundItem = new Ground(code,x,y,size);
			this.entities[x][y] = GroundItem;
			//from 50 ~ 60 Stair
		}else if(code >= 50 && code < 60){
			Stairs Stair = new Stairs(code,x,y,size);
			//Stair.SetStairs(this.floor);
			this.entities[x][y] = (Entity)Stair;
		
			//60 ~ 70 shop and temple
		}else if(code >= 60 && code < 70){	
			if(code == 60){
				Ground GroundShop = new Ground(code,x,y,size);
				GroundShop.setShopOrTemple((T)SType);
				this.entities[x][y] = GroundShop;
			}else if(code == 65){
				Ground GroundTemple = new Ground(code,x,y,size);
				GroundTemple.setShopOrTemple((T)TType0);
				this.entities[x][y] = GroundTemple;
			}else if(code == 66){
				Ground GroundTemple = new Ground(code,x,y,size);
				GroundTemple.setShopOrTemple((T)TType1);
				this.entities[x][y] = GroundTemple;
			}else if(code == 67){
				Ground GroundTemple = new Ground(code,x,y,size);
				GroundTemple.setShopOrTemple((T)TType2);
				this.entities[x][y] = GroundTemple;
			}
			//equipment
		}else if(code >= 70 && code < 80){	
			Entity equipment = new Ground(code,x,y,size);
			this.entities[x][y] = equipment;
			//from 90 ~ 99 Monster	
		}else if(code >= 90 && code <= 99){
			Entity GroundMonster = new Ground(code,x,y,size);
			this.entities[x][y] = GroundMonster;
		}

	}

	//============================================= Test =================================================================
	/**
	 * Use for load and save
	 */@Override
	public String toString() {
		StringBuilder temp = new StringBuilder();
		temp.append("l " + this.floor +" (" + "\n" );
		for (int i = 0; i < 12; i++) {
			for (int c = 0; c < 12; c++) {
				int code = this.entities[i][c].getCode();
				if(code < 10) temp.append(" 0" + code);
				else temp.append(" " + this.entities[i][c].getCode());
			}
			temp.append("\n");
		}
		temp.append(")\n");
		return temp.toString();
	}




}
