package clueGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import experiment.TestBoardCell;

public class Board {
	// read the files and set numrows and numcols
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
		initialize();
		grid = new TestBoardCell[numRows][numCols];
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				makeAdjacencyList(grid[row][col]);
			}
		}
	}
    public void
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	loadConfigFiles();
    }
    public void loadConfigFiles() {
    	loadLayoutConfig();
    	loadSetupConfig();
    }
    public void loadSetupConfig() {
    	// read setup file and set relevant variables
    	
    }
    public void loadLayoutConfig() {
    	// load layout file and set relevant variables
    	// while has next line:
    	// figure out how many columns there are
    	// at the end, figure out how many rows there are
    	
    	BufferedReader br = new BufferedReader(new FileReader(layoutConfigFile));
    	int rowCount = 1;
    	String line = br.readLine();
    	String[] arr = line.split(",");
    	int colCount = arr.length;
    	while (line != null) {
    		rowCount++;
    		// have a counter variable for columns
    		arr = line.split(",");
    		
    		if (arr.length != colCount) {
    			throw new BadConfigFormatException();
    		}
    		line = br.readLine();

    	}
    	numRows = rowCount;
    	numCols = colCount;
    	String[][] grid2 = new String[numRows][numCols];
    	br = new BufferedReader(new FileReader(layoutConfigFile));
    	for(int i = 0; i < numRows; i++) {
    		line = br.readLine();
    		arr = line.split(",");
    		for(int column = 0; column < arr.length; column ++) {
    			BoardCell c = new BoardCell(i, column);
    			// TODO: add appropriate setters
    		}
    	}
    }
    	// check if all of the rows are the same length

    
	public Room getRoom(char initial) {
		Room charRoom = new Room();
		return charRoom;
	}
	public Room getRoom(BoardCell cell) {
		Room cellRoom = new Room();
		return cellRoom;
	}
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigFile = setup;
	}
	public int getNumRows() {
		//numRows = -1
		if (numRows != -1) {
			return numRows
		}
		
		return numRows;
	}
	public int getNumColumns() {
		return numCols;
	}
	public BoardCell getCell(int row, int col) {
		return new BoardCell(row, col);
	}
	

}
