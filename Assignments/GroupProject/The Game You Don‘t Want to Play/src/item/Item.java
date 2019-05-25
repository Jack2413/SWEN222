package item;

/***
 * This is a item interface which include all kinds of items
 */
public interface Item {
	/**
	 * @param x
	 * @param y
	 *            check if the point(x,y) is on the top of item
	 */
	public boolean on(int x, int y);

	/**
	 * return a the code name of every item;
	 */
	public String toString();

	public String getName();
}
