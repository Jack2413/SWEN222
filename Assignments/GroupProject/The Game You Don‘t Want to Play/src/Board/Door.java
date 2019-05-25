package Board;

/**
 * Door instance as different color door that could open by specific key
 * 
 * @author tian
 *
 */
public class Door extends Entity {
	public String color;

	public Door(int code, int x, int y, int size) {
		super(code, x, y, size);
		setColor();
	}


	public void setColor() {
		switch (super.Code) {
		case 20:
			color = "gold";
			break;
		case 21:
			color = "cyan";
			break;
		case 22:
			color = "bronze";
			break;
		case 23:
			color = "purple";
			break;
		case 24:
			color = "silver";
			break;
		}
	}
	// ================================= Return =========================================================

	public String getColor() {
		return this.color;
	}

}
