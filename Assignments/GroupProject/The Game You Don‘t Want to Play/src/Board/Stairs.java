package Board;
/**
 * Stair is use for player move into other level
 * @author tian
 *
 */
public class Stairs extends Entity{
	// true point to up stair, false point to down stair
 	private boolean upOrDown = true;

	public Stairs(int code, int x, int y, int size) {
		super(code, x, y, size);
		setUpOrDown();
	}
	
	public void setUpOrDown() {
		if(super.Code == 51) upOrDown = true;
		else if(super.Code == 52) upOrDown = false;
	}
	
	//================================= Return =========================================================
	/**
	 * return true mean this stair is up stair 
	 * otherwise return false
	 * @return
	 */
	public boolean upOrDownStair(){ return this.upOrDown;}

}
