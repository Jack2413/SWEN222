package Board;


/**
 * Some type of wall could be break by bomb
 * @author tian
 *
 */
public class Wall extends Entity{
	
	public Wall(int code, int x, int y, int size) {
		super(code, x, y, size);
	}

	//================================= Return =========================================================
	/**
	 * Is this wall breakable?
	 * If code name equal to 11 then it means breakable
	 * @return
	 */
	public boolean isBreakable(){
		if(super.Code == 11) return true;
		return false;
	}


}
