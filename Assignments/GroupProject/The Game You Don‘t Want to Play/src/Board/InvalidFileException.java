package Board;
/***
 * This exception will be throw if the load file is invalid
 * */
public class InvalidFileException extends Exception{
	public InvalidFileException(String msg) {
		super(msg);
	}
}
