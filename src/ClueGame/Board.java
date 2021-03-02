package clueGame;

import java.util.HashMap;

import experiment.TestBoardCell;

public class Board {
	private int numRows = 0;
	private int numCols = 0;
	BoardCell [][] grid = new BoardCell [numRows][numCols];
	private String layoutConfigFile = new String();
	private String setupConfigFile = new String() ;
	private HashMap<Room, Character> roomMap = new HashMap<Room, Character>();
	
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
           super();
    }
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	return;
    }
    public void loadConfigFiles() {
    	
    }
    public void loadSetupConfig() {
    	
    }
    public void loadLayoutConfig() {
    	
    }
	public Room getRoom(char initial) {
		Room charRoom = new Room();
		return charRoom;
	}
	public Room getRoom(BoardCell cell) {
		Room cellRoom = new Room();
		return cellRoom;
	}
	public void setConfigFiles(String layout, String setup) {
		return;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numCols;
	}
	public BoardCell getCell(int row, int col) {
		return new BoardCell(row, col);
	}
	

}
