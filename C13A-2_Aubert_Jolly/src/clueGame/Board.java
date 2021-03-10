package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;

import experiment.TestBoardCell;

public class Board {
	// read the files and set numrows and numcols
	private int numRows = 0;
	private int numCols = 0;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	BoardCell [][] grid = new BoardCell [numRows][numCols];
	private String layoutConfigFile = new String();
	private String setupConfigFile = new String() ;
	private HashMap<Room, Character> roomMap = new HashMap<Room, Character>();
	
	public int getNumRows() {
		return numRows;
	}


	public int getNumColumns() {
		return numCols;
	}
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
	public Board() {
		super();
		//initialize();
		grid = new BoardCell[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid[row][col] = null;
			}
		}
		roomMap = new HashMap<Room, Character>();
		roomMap.clear();
		layoutConfigFile = "";
		roomMap.clear();
		setupConfigFile = "";
		
	}


	private void makeAdjacent() {
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				makeAdjacencyList(grid[row][col]);
			}
		}
	}
	
	
    //public void
	
	
    public HashMap<Room, Character> getRoomMap() {
		return roomMap;
	}


	// this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	grid = new BoardCell[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid[row][col] = null;
			}
		}
		roomMap = new HashMap<Room, Character>();
		roomMap.clear();
		roomMap.clear();
    	loadConfigFiles();
    	makeAdjacent();
    }
    public void loadConfigFiles() {
    	try {
			loadSetupConfig();
		} catch (BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void loadSetupConfig() throws BadConfigFormatException {
    	// read setup file and set relevant variables
    	try {
    		File f = new File(setupConfigFile);
			Scanner read = new Scanner(f);
			while(read.hasNextLine()) {
				String data = read.nextLine();
				String[] arr = data.split(",");
				if (arr.length < 2) {
					continue;
				}
				if (arr[0].contentEquals("Room")) {
					Room temp = new Room(arr[1].substring(1));
					roomMap.put(temp, arr[2].charAt(1)); 
				}
				else if (arr[0].contentEquals("Space")) {
					Room temp = new Room(arr[1].substring(1));
					roomMap.put(temp, arr[2].charAt(1));
				} else {
					throw new BadConfigFormatException();
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
    	int rowCount = 0;
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
    		rowCount++;

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
    			BoardCell cell = new BoardCell(row, column);
    			String symbol = arr[column];
    			
    			Character initial = symbol.charAt(0);
    			cell.setInitial(initial); // sets room initial
    			if (!roomMap.containsValue(initial)){
    				throw new BadConfigFormatException();
    			}
    			if(symbol.length() > 1) {
    				char special = symbol.charAt(1);
    				if(special == '*') {
    					cell.setRoomCenter(true);
    					// for every room and character representation
    					for (Entry<Room, Character> entry : roomMap.entrySet()) {
    						//if the initial of this cell is equal to an initial in the map
    						if(initial.equals(entry.getValue())) {
    							//set the label cell of the room to the current cell.
    							entry.getKey().setCenterCell(cell);
    						}
    					}
    					
    				}
    				else if(special == '#') {
    					cell.setRoomLabel(true);
    					// for every room and character representation
    					for (Entry<Room, Character> entry : roomMap.entrySet()) {
    						//if the initial of this cell is equal to an initial in the map
    						if(initial.equals(entry.getValue())) {
    							//set the label cell of the room to the current cell.
    							entry.getKey().setLabelCell(cell);
    						}
    					}
    				}
    				else if(special == '^') {
    					cell.setDoordirection(DoorDirection.UP);
    					cell.setDoorway(true);
    				}
    				else if (special == '>') {
    					cell.setDoordirection(DoorDirection.RIGHT);
    					cell.setDoorway(true);
    				}
    				else if (special == 'v') {
    					cell.setDoordirection(DoorDirection.DOWN);
    					cell.setDoorway(true);
    				}
    				else if (special == '<') {
    					cell.setDoordirection(DoorDirection.LEFT);
    					cell.setDoorway(true);
    				}
    				else {
    					cell.setSecretPassage(special);
    				}
    			}
    			grid[row][column] = cell; // adds appropriate cell to the grid	
    		}
    	}
//    	giveRooms();
    }
//    public void giveRooms() {
//    	for (int i = 0; i < numRows; i++) {
//    		for (int j = 0; j < numCols; j++) {
//    			if (grid[i][j].isDoorway()) {
//    				if(grid[i][j].getDoordirection() == DoorDirection.UP) {
//    					grid[i][j].setCorrespondingRoom(grid[i-1][j].getRoom(););
//    				}
//    				else if(grid[i][j].getDoordirection() == DoorDirection.DOWN) {
//    					
//    				}
//    				else if(grid[i][j].getDoordirection() == DoorDirection.LEFT) {
//    					
//    				}
//    			}
//    		}
//    	}
//    }
    	// check if all of the rows are the same length

    
	public String getLayoutConfigFile() {
		return layoutConfigFile;
	}


	public String getSetupConfigFile() {
		return setupConfigFile;
	}


	public Room getRoom(Character initial) {
		for (Entry<Room, Character> entry : roomMap.entrySet()) {
			if(initial.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public Room getRoom(BoardCell cell) {
		Character initial = grid[cell.getRow()][cell.getCol()].getInitial();
		Room ans = null;
		for (Entry<Room, Character> entry : roomMap.entrySet()) {
			if(initial.equals(entry.getValue())) {
				ans = entry.getKey();
			}
		}
		return ans;
	}
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigFile = setup;
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public void makeAdjacencyList(BoardCell cell) {
			// check the door direction
			DoorDirection direction = cell.getDoordirection();
			// find the initial of the room that the door leads to 
			// set the adjacency list to be the room center of that room
			// then, you can make adjacencyLists of the other cells by the door,
			// but do not check the direction of the doorway
		Character initial = ' ';
		if(cell.getRow() +1 < numRows && !cell.getIsRoom()) {
			//if it is a door and faces down{
			//do code for a door else{
			if (direction == DoorDirection.DOWN) {
				//find the initial of the room that the door leads to
				initial = grid[cell.getRow()+1][cell.getCol()].getInitial();
				findCenter(initial, cell);
			}
			else {
			cell.addAdjacency(grid[cell.getRow()+1][cell.getCol()]);
			}
		}
		if(cell.getRow() -1 >= 0 && !cell.getIsRoom()) {
			if (direction == DoorDirection.UP) {
				//find the initial of the room that the door leads to
				initial = grid[cell.getRow()-1][cell.getCol()].getInitial();
				findCenter(initial, cell);
			} else {
				cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
			}
		}
		if(cell.getCol() +1 < numCols && !cell.getIsRoom()) {
			if (direction == DoorDirection.RIGHT) {
				//find the initial of the room that the door leads to
				initial = grid[cell.getRow()][cell.getCol()+1].getInitial();
				findCenter(initial, cell);
			} else {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()+1));
			}
		}
		if(cell.getCol() -1 >= 0 && !cell.getIsRoom()) {
			if (direction == DoorDirection.LEFT) {
				//find the initial of the room that the door leads to
				initial = grid[cell.getRow()][cell.getCol() -1].getInitial();
				findCenter(initial, cell);
			}
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()-1));
		}
		if(initial != ' ') {
			//find the corresponding room for that initial
		}
	}
	public void findCenter(Character initial, BoardCell cell) {
		for (Entry<Room, Character> entry : roomMap.entrySet()) {
			//if the initial of this cell is equal to an initial in the map
			if(initial.equals(entry.getValue())) {
				cell.addAdjacency(entry.getKey().getCenterCell());
			}
		}
	}
	
	
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	public void findAllTargets(BoardCell thisCell, int numSteps) {
		
		for(BoardCell c: thisCell.getAdjList()) {
			if(visited.contains(c) || c.getIsOccupied()) {
				continue; // skip this cell
			} 
			visited.add(c);
			if (numSteps == 1 || c.getIsRoom()) {
				targets.add(c);
			} else {
				findAllTargets(c, numSteps - 1);
			}
			visited.remove(c);
		}
	}
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	
	

}
