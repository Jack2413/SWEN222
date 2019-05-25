package Board;

import java.awt.Rectangle;

/**
 * Entity have different type such as ground,stair and wall.
 * @author tian
 *
 */
public abstract class Entity {
	protected int Code;
	protected int PosX,PosY,size;
	private Rectangle EntityRange;

	public Entity(int code,int x,int y,int size){
		this.Code = code;
		this.PosX = x;
		this.PosY = y;
		this.size = size;
		this.EntityRange = new Rectangle(x * size ,y * size,size,size);
	}

	//================================= Return =========================================================
	/**
	 * 
	 * @return name of the entity
	 */
	public int getCode() {return this.Code;}
	/**
	 * position X
	 * @return
	 */
	public int getPosX(){return this.PosX;}

	/**
	 * position Y
	 * @return
	 */
	public int getPosY(){return this.PosY;}

	/**
	 * Entity Size
	 * @return
	 */
	public int getSize(){return this.size;}

	/**
	 * Range of the entity
	 * @return
	 */
	public Rectangle getRange(){return this.EntityRange;}
	//================================= Test ============================================================
	@Override
	public String toString(){ return String.valueOf(this.Code);}

}
