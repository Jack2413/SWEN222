package Board;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

public class Board {
	private static Map<Integer, Level> levels;
	private int currentLevel;

	public Board(Scanner sc){
		this.levels = new HashMap<Integer, Level>();
		try {
			loadInitialize(sc);
		} catch (InvalidFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Board(){
		this.levels = new HashMap<Integer, Level>();
		this.currentLevel = 1;
		try {
			originalInitialize();
		} catch (InvalidFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//====================================== Return ======================================================

	/**
	 * Return current level
	 */
	public Level getCurrentLevel(){
		return this.levels.get(currentLevel);
	}

	/**
	 * Return the number of current level 
	 * @return
	 */
	public int getCurrentLevelNumber(){
		return this.currentLevel;
	}

	/**
	 * Use for set current level
	 * @param level
	 */
	public void setCurrentLevel(int level) {
		currentLevel = level;
	}

	/**
	 * Method of Get All levels
	 * @return
	 */
	public Collection<Level> getAllLevel(){
		return this.levels.values();
	}

	/**
	 * Use for save and test
	 */
	@Override
	public String toString(){
		StringBuilder temp = new StringBuilder();
		temp.append("( "+this.currentLevel +" )\n");
		for(Level l:this.levels.values())temp.append(l.toString()+"\n");
		return temp.toString();
	}

	//================================== initialize ======================================================
	/**
	 * Start the normal Game
	 * @throws InvalidFileException 
	 */
	private void originalInitialize() throws InvalidFileException{
		String fileName = "OriginalMap.txt";
		try {
			InputStream is = Board.class.getResourceAsStream("OriginalMap.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

			Scanner sc = new Scanner(bufferedReader);
			while(sc.hasNext()){
				if(sc.hasNext("l")){
					sc.next();      //consume l
					int NumOfLevel = sc.nextInt();
					Level temp = new Level(NumOfLevel);
					temp.parserLevel(sc);
					this.levels.put(NumOfLevel, temp);
				}
			}
			bufferedReader.close();
			sc.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}	

	/**
	 * Load a file, start a saved game.
	 * @throws InvalidFileException 
	 */
	public void loadInitialize(Scanner sc) throws InvalidFileException{
		try{
			sc.next();      // consume (
			this.currentLevel = sc.nextInt();
			sc.next();      // consume )
			while(sc.hasNext()){
				sc.next();      //consume l
				int NumOfLevel = sc.nextInt();
				Level temp = new Level(NumOfLevel);
				temp.parserLevel(sc);
				this.levels.put(NumOfLevel, temp);
			}
			sc.close();
		}catch (java.util.InputMismatchException e){
			System.out.println(e);
		}

	}


}
