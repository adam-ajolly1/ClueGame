package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

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
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				makeAdjacencyList(grid[row][col]);
			}
		}
	}
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
    public int getRow() {
    	return numRows;
    }
    public int getCol() {
    	return numCols;
    }
    public void loadConfigFiles() throws IOException, BadConfigFormatException {
    	loadLayoutConfig();
    	loadSetupConfig();
    }
    public void loadSetupConfig() {
    	// read setup file and set relevant variables
    	try {
    		File f = new File(setupConfigFile);
			Scanner read = new Scanner(f);
			while(read.hasNextLine()) {
				String data = read.nextLine();
				String[] arr = data.split(" ");
				if (arr[0] == "Room,") {
					Room r = new Room(arr[1]); // sets up room with name
					// TODO: There needs to be something with initial here but I am not sure... roomMap?
				}
			}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    }
    public void loadLayoutConfig() throws IOException, BadConfigFormatException {
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
    	grid = new BoardCell[numRows][numCols];
    	br = new BufferedReader(new FileReader(layoutConfigFile));
    	for(int row = 0; row < numRows; row ++) {
    		line = br.readLine();
    		arr = line.split(",");
    		for(int column = 0; column < arr.length; column ++) {
    			BoardCell c = new BoardCell(row, column);
    			String symbol = arr[column];
    			char initial = symbol.charAt(0);
    			c.setInitial(initial); // sets room initial
    			if(symbol.length() > 1) {
    				char special = symbol.charAt(1);
    				if(special == '*') {
    					c.setRoomCenter(true);
    				}
    				else if(special == '#') {
    					c.setRoomLabel(true);
    				}
    				else if(special == '^') {
    					c.setDoordirection(DoorDirection.UP);
    				}
    				else if (special == '>') {
    					c.setDoordirection(DoorDirection.RIGHT);
    				}
    				else if (special == 'v') {
    					c.setDoordirection(DoorDirection.DOWN);
    				}
    				else if (special == '<') {
    					c.setDoordirection(DoorDirection.LEFT);
    				}
    				else {
    					c.setSecretPassage(special);
    				}
    			}
    			grid[row][column] = c; // adds appropriate cell to the grid	
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
	public BoardCell getCell(int row, int col) {
		return new BoardCell(row, col);
	}
	public void makeAdjacencyList(BoardCell cell) {

		if(cell.getRow() +1 < numRows) {
			cell.addAdjacency(this.getCell(cell.getRow()+1, cell.getCol()));
		}
		if(cell.getRow() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow()-1, cell.getCol()));
		}
		if(cell.getCol() +1 < numCols) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()+1));
		}
		if(cell.getCol() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()-1));
		}
	}
	
	

}
