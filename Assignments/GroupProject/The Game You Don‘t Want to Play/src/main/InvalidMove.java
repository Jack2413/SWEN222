package main;

/**
 * An exception that indicates an attempt to make an invalid move.
 * @author stella
 *
 */
@SuppressWarnings("serial")
public class InvalidMove extends Exception {
	public InvalidMove(String msg) {
		super(msg);
	}
}