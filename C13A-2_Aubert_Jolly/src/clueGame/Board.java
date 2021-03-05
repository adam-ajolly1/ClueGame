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
	
	public int getNumRows() {
		return numRows;
	}


	public int getNumCols() {
		return numCols;
	}
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
	private Board() {
		super();
		//initialize();
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				makeAdjacencyList(grid[row][col]);
			}
		}
	}
	
	
    //public void
	
	
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
    	try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loadSetupConfig();
    }
    public void loadSetupConfig() {
    	// read setup file and set relevant variables
    	try {
    		File f = new File(setupConfigFile);
			Scanner read = new Scanner(f);
			while(read.hasNextLine()) {
				String data = read.nextLine();
				String[] arr = data.split("\\s+");
				if (arr[0].contentEquals("Room,")) {
					System.out.println("yes");
					System.out.println(arr[0]);
					System.out.println(arr[1]);
					System.out.println(arr[2]);
					Room temp = new Room(arr[1]);
					System.out.println(temp);
					System.out.println(arr.length);
					roomMap.put(temp, arr[2].charAt(0)); //TODO: Finish this
				}
			}
			read.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	
    }
    public void loadLayoutConfig() throws BadConfigFormatException {
    	// load layout file and set relevant variables
    	// while has next line:
    	// figure out how many columns there are
    	// at the end, figure out how many rows there are
    	
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(layoutConfigFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int rowCount = 1;
    	String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String[] arr = line.split(",");
    	int colCount = arr.length;
    	while (line != null) {
    		rowCount++;
    		// have a counter variable for columns
    		arr = line.split(",");
    		
    		if (arr.length != colCount) {
    			throw new BadConfigFormatException();
    		}
    		try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    	numRows = rowCount;
    	numCols = colCount;
    	grid = new BoardCell[numRows][numCols];
    	try {
			br = new BufferedReader(new FileReader(layoutConfigFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for(int row = 0; row < numRows; row ++) {
    		try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if (line != null) {
    			arr = line.split(",");
    		}
    		
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
