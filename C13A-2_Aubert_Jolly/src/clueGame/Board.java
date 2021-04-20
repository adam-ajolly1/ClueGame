package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;

import experiment.TestBoardCell;

public class Board extends JPanel {
	// private instance variables for the Board class
	private static final int NUMPLAYERS = 6;
	private int numRows = 0;
	private int numCols = 0;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	BoardCell [][] grid = new BoardCell [numRows][numCols];
	private String layoutConfigFile = new String();
	private String setupConfigFile = new String() ;
	private HashMap<Room, Character> roomMap = new HashMap<Room, Character>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private HashSet<Card> deck = new HashSet<Card>();
	private Solution theAnswer;
	public final static int WIDTH = 32;
	public final static int HEIGHT = 31;
	public final static int OFFSET = 3;



	public Solution getTheAnswer() {
		return theAnswer;
	}

	// constructor is private to ensure only one can be created
	private static Board theInstance = new Board();
	public Board() {
		super();
		addMouseListener(new clickedBoardListener());
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	// setters and getters for instance variables

	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigFile = setup;
	}
	public void addToPlayerList(Player player) {
		playerList.add(player);
	}
	public void clearPlayerList() {
		playerList.clear();
	}

	public String getLayoutConfigFile() {
		return layoutConfigFile;
	}

	public String getSetupConfigFile() {
		return setupConfigFile;
	}
	public HashMap<Room, Character> getRoomMap() {
		return roomMap;
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

	public HashSet<BoardCell> getAdjList(int row, int col){
		return grid[row][col].getAdjList();
	}
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	// initializes the board
	public void initialize() {
		grid = new BoardCell[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				grid[row][col] = null;
			}
		}
		roomMap = new HashMap<Room, Character>();
		roomMap.clear();
		deck = new HashSet<Card>();
		deck.clear();
		for(Player p: playerList) {
			p.getHand().clear();
		}
		this.playerList.clear();

		loadConfigFiles();
		initializeDoors();
		makeAdjacent();

	}
	public void loadConfigFiles() {
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(this.playerList.size() == NUMPLAYERS) {
			this.deal();
		}
		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// loads clueSetup.txt and populates roomMap
	public void loadSetupConfig() throws BadConfigFormatException {

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
					this.updateDeck(CardType.ROOM, arr[1]);
					Room temp = new Room(arr[1].substring(1));
					roomMap.put(temp, arr[2].charAt(1)); 
				}
				else if (arr[0].contentEquals("Space")) {
					Room temp = new Room(arr[1].substring(1));
					roomMap.put(temp, arr[2].charAt(1));
				} else if(arr[0].contentEquals("Weapon")) {
					this.updateDeck(CardType.WEAPON, arr[1].substring(1));
				}else if (arr[0].contentEquals("Person")) {
					if (arr[1].substring(1).contentEquals("Human")) {
						this.playerList.add(new HumanPlayer(arr[1].substring(1), Color.decode(arr[2].substring(1))));
					} else {
						this.playerList.add(new ComputerPlayer(arr[1].substring(1), Color.decode(arr[2].substring(1))));
					}
					this.updateDeck(CardType.PERSON, arr[1].substring(1));
				} else {
					throw new BadConfigFormatException();
				}
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateDeck(CardType cardType, String cardName) {
		deck.add(new Card(cardType, cardName));
	}
	// loads clueLayout.csv and populates grid based on information
	public void loadLayoutConfig() throws BadConfigFormatException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(layoutConfigFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int rowCount = 0;
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] arr = line.split(",");
		int colCount = arr.length;
		while (line != null) {
			arr = line.split(",");

			if (arr.length != colCount) {
				throw new BadConfigFormatException();
			}
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			rowCount++;
		}
		// sets rows and columns based on the numbers read in above 
		numRows = rowCount; 
		numCols = colCount;

		// creates grid of boardCells
		grid = new BoardCell[numRows][numCols];
		HashMap<Character, Character>secretRoomSet = new HashMap<Character, Character>();

		// creates buffered reader to read CSV
		try {
			br = new BufferedReader(new FileReader(layoutConfigFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// cycles through rows and columns
		for(int row = 0; row < numRows; row ++) {
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (line != null) {
				arr = line.split(",");
			}

			for(int column = 0; column < arr.length; column ++) {
				BoardCell cell = new BoardCell(row, column);



				String symbol = arr[column];

				Character initial = symbol.charAt(0);
				// checks if cell is unused
				if(initial == 'K') {
					cell.setUnused(true);
				}

				// sets room initial
				cell.setInitial(initial); 
				if (!roomMap.containsValue(initial)){
					throw new BadConfigFormatException();
				}

				// sets variables for special characters *, #, and ^><v
				if(symbol.length() > 1) {
					Character special = symbol.charAt(1);
					switch (special) {
					case '*':
						cell.setRoomCenter(true);
						cell.setIsRoom(true);

						// sets center of room 
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								entry.getKey().setCenterCell(cell);
							}
						}
						break;
					case '#':
						cell.setRoomLabel(true);
						cell.setIsRoom(true);

						// sets the label cell of the room
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								//set the label cell of the room to the current cell.
								cell.setCorrespondingRoom(entry.getKey());
								entry.getKey().setLabelCell(cell);
							}
						}
						break;
					case '^':
						cell.setDoordirection(DoorDirection.UP);
						cell.setDoorway(true);
						break;
					case '>':
						cell.setDoordirection(DoorDirection.RIGHT);
						cell.setDoorway(true);
						break;
					case 'v':
						cell.setDoordirection(DoorDirection.DOWN);
						cell.setDoorway(true);
						break;
					case '<':
						cell.setDoordirection(DoorDirection.LEFT);
						cell.setDoorway(true);
						break;
					default:
						secretRoomSet.put(special,initial);
						cell.setSecretPassage(special);
						cell.setIsRoom(true);
					}

				}

				// if length =1, sets all non-special rooms also be rooms
				else if(!(initial == 'W')) {
					cell.setIsRoom(true);
				}
				// finally, adds cell with information to the grid
				grid[row][column] = cell; 
			}
		}
		// sets secret passages for each room, and what room they are adjacent to
		for (Entry<Character, Character> s: secretRoomSet.entrySet()) {
			BoardCell adjSecretPassage = new BoardCell();
			for (Entry<Room, Character> entry : roomMap.entrySet()) {
				if(s.getKey().equals(entry.getValue())) {
					adjSecretPassage = entry.getKey().getCenterCell();
				}
			}
			for (Entry<Room, Character> entry : roomMap.entrySet()) {
				if(s.getValue().equals(entry.getValue())) {
					entry.getKey().setSecretPassage(adjSecretPassage);
				}
			}
		}
	}



	public void initializeDoors() {
		//loop through the grid and sets each door to its corresponding room 
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(grid[i][j].isDoorway()) {
					BoardCell door = grid[i][j];
					if(door.getDoorDirection() == DoorDirection.UP) {
						Character initial = grid[i-1][j].getInitial();
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								entry.getKey().addToList(door);
							}
						}
					}
					if(door.getDoorDirection() == DoorDirection.DOWN) {
						Character initial = grid[i+1][j].getInitial();
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								entry.getKey().addToList(door);
							}
						}
					}
					if(door.getDoorDirection() == DoorDirection.LEFT) {
						Character initial = grid[i][j-1].getInitial();
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								entry.getKey().addToList(door);
							}
						}
					}
					if(door.getDoorDirection() == DoorDirection.RIGHT) {
						Character initial = grid[i][j+1].getInitial();
						for (Entry<Room, Character> entry : roomMap.entrySet()) {
							if(initial.equals(entry.getValue())) {
								entry.getKey().addToList(door);
							}
						}
					}
				}
			}		
		}
	}
	public void makeAdjacencyList(BoardCell cell) {

		// gets direction of door, returns NONE if no door
		DoorDirection direction = cell.getDoordirection();

		Character initial = ' ';
		if (cell.isRoomCenter()) {
			// if there is a secret passage, include it in adjacency list
			if (getRoom(cell).getSecretPassage() != null) {
				cell.addAdjacency(getRoom(cell).getSecretPassage());
			}
			// add all corresponding doors to be adjacent to the room center
			for(BoardCell roomCenterCell: getRoom(cell).getCorrespondingDoors()) {
				cell.addAdjacency(roomCenterCell);
			}
		} 
		else {
			// if cell is NOT a room center
			// checks if it's a valid cell on the board, and if it's either a) not occupied or b) a door
			if(cell.getRow() + 1 < numRows && (!grid[cell.getRow() + 1][cell.getCol()].isOccupied() || direction != DoorDirection.NONE)) {
				//if it is a door and faces down{
				//do code for a door else{
				if (direction == DoorDirection.DOWN) {
					//find the initial of the room that the door leads to
					initial = grid[cell.getRow()+1][cell.getCol()].getInitial();
					findCenter(initial, cell);
				}
				else {
					if(!grid[cell.getRow()+1][cell.getCol()].isUnused()&& (!grid[cell.getRow() + 1][cell.getCol()].getIsRoom()))
						// adds adjacent walkways
						cell.addAdjacency(grid[cell.getRow()+1][cell.getCol()]);
				}
			}
			// same pattern follows for up, right, and left
			if(cell.getRow() -1 >= 0 && (!grid[cell.getRow() - 1][cell.getCol()].isOccupied() || direction != DoorDirection.UP)) {
				if (direction == DoorDirection.UP) {
					initial = grid[cell.getRow()-1][cell.getCol()].getInitial();
					findCenter(initial, cell);

				} else {
					if(!grid[cell.getRow()-1][cell.getCol()].isUnused() && (!grid[cell.getRow()-1][cell.getCol()].getIsRoom())) {
						cell.addAdjacency(grid[cell.getRow() - 1][cell.getCol()]);
					}
				}
			}
			if(cell.getCol() + 1 < numCols && (!grid[cell.getRow()][cell.getCol()+1].isOccupied() || direction!= DoorDirection.RIGHT)) {
				if (direction == DoorDirection.RIGHT) {
					initial = grid[cell.getRow()][cell.getCol()+1].getInitial();
					findCenter(initial, cell);
				} 
				else {
					if(!grid[cell.getRow()][cell.getCol()+1].isUnused()&& (!grid[cell.getRow()][cell.getCol()+1].getIsRoom())) {
						cell.addAdjacency(grid[cell.getRow()][cell.getCol()+1]);
					}
				}
			}
			if(cell.getCol() - 1 >= 0 && (!grid[cell.getRow()][cell.getCol()-1].isOccupied() || direction != DoorDirection.LEFT)) {
				if (direction == DoorDirection.LEFT) {
					initial = grid[cell.getRow()][cell.getCol() -1].getInitial();
					findCenter(initial, cell);
				} else {
					if(!grid[cell.getRow()][cell.getCol() - 1].isUnused() && (!grid[cell.getRow()][cell.getCol() - 1].getIsRoom() )) {
						cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()-1));
					}
				}
			}
		}
	}

	//takes in a cell's initial and the cell and adds the room center corresponding to the initial
	//to the cell's adjacency list
	public void findCenter(Character initial, BoardCell cell) {
		//find the room in roomMap corresponding to the initial
		for (Entry<Room, Character> entry : roomMap.entrySet()) {
			//if the initial of this cell is equal to an initial in the map
			if(initial.equals(entry.getValue())) {
				cell.addAdjacency(entry.getKey().getCenterCell());
			}
		}
	}

	//finds all the possible end board cells given a startcell and a roll number.
	public void calcTargets(BoardCell startCell, int pathlength) {
		//initialize list of all visited cell (once we uncounter a cell, we can't go back)
		visited = new HashSet<BoardCell>();
		//initialize list of the final targets
		targets = new HashSet<BoardCell>();
		//add the starting cell to the targets (we can't go back to where we started
		visited.add(startCell);
		//call recursive function
		findAllTargets(startCell, pathlength);
	}


	public HashSet<Card> getDeck() {
		return deck;
	}

	public void findAllTargets(BoardCell thisCell, int numSteps) {
		//for every cell next to thisCell
		for(BoardCell c: thisCell.getAdjList()) {
			//if it is a room center, or has been visited, or is occupied
			if(visited.contains(c) || (c.getIsOccupied() && !c.isRoomCenter())) {
				continue; // skip this cell
			} 
			//add to visited so we do not go back
			visited.add(c);
			//basis step, if we do not have any more steps (end of the roll)
			if (numSteps == 1 || c.getIsRoom()) {
				targets.add(c);
			} else {
				//recursive step, find targets with one less on the roll
				findAllTargets(c, numSteps - 1);
			}
			visited.remove(c);
		}
	}
	// makes adjacency lists for each cell in the grid
	private void makeAdjacent() {
		for(int row = 0; row < numRows; row ++) {
			for(int col = 0; col < numCols; col ++) {
				makeAdjacencyList(grid[row][col]);
			}
		}
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void deal() {
		// TODO Auto-generated method stub
		HashSet<Card> dealDeck = (HashSet<Card>) this.deck.clone();
		ArrayList<Card> roomsDeck = new ArrayList<Card>();
		ArrayList<Card> personDeck = new ArrayList<Card>();
		ArrayList<Card> weaponDeck = new ArrayList<Card>();
		for (Card c: dealDeck) {
			switch(c.getCardType()) {
			case ROOM:
				roomsDeck.add(c);
				break;
			case PERSON:
				personDeck.add(c);
				break;
			case WEAPON:
				weaponDeck.add(c);
				break;
			}
		}
		Random rand = new Random();
		Card roomAns = roomsDeck.get(rand.nextInt(roomsDeck.size()));
		Card personAns = personDeck.get(rand.nextInt(personDeck.size()));
		Card weaponAns = weaponDeck.get(rand.nextInt(weaponDeck.size()));
		this.setTheAnswer(roomAns, personAns, weaponAns);
		dealDeck.remove(roomAns);
		dealDeck.remove(personAns);
		dealDeck.remove(weaponAns);
		int counter = 0;
		while(dealDeck.size() > 0) {
			Card[] deckArray = dealDeck.toArray(new Card[dealDeck.size()]);
			Card randomCard = deckArray[rand.nextInt(deckArray.length)];
			this.playerList.get(counter++ % NUMPLAYERS).updateHand(randomCard);
			dealDeck.remove(randomCard);
		}

	}

	public void setTheAnswer(Card room, Card person, Card weapon) {
		this.theAnswer = new Solution(room, person, weapon);
	}
	public boolean checkAccusation(Card room, Card person, Card weapon) {
		if(theAnswer.getRoom() == room && theAnswer.getPerson() == person && theAnswer.getWeapon() == weapon) {
			return true;
		}
		else {
			return false;
		}
	}
	public Card handleSuggestion(Solution suggestion, Player p) {
		Card toReturn = new Card();
		// moves player of suggestion into room
		Card PlayerToMove = suggestion.getPerson();
		Card roomSuggestion = suggestion.getRoom();
		
		Player move = null;
		for(Player x: this.playerList) {
			if(x.getName() == PlayerToMove.getCardName()) {
				move = x;
				break;
			}
		}
		Room r = null;
		for (Entry<Room, Character> entry : roomMap.entrySet()) {
			if (entry.getKey().getName() == roomSuggestion.getCardName()) {
				r = entry.getKey();
				break;
			}
		}
		this.grid[move.getRow()][move.getColumn()].setOccupied(false);
		move.setLocation(r.getCenterCell().getRow(), r.getCenterCell().getCol());
		
		// iterates over player list and disproves suggestion, continuing 
		// if the player is the suggesting player. 
		for(Player x: this.playerList) {
			if(x.equals(p)) {
				continue;
			}
			toReturn = x.disproveSuggestion(suggestion);
			if(toReturn != null) {
				break;
			}
		}
		p.updateSeen(toReturn); // adds the shown card to the seen list
		return toReturn;
	}
	// function that takes a Room on a board and returns the corresponding card
	public Card roomToCard(Room r) {
		String roomName = r.getName();
		for(Card c: this.getDeck()) {
			if(c.getCardName().substring(1).equals(roomName) && c.getCardType() == CardType.ROOM) {
				return new Card(CardType.ROOM, c.getCardName().substring(1));
			}
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid[0].length; j++) {
				grid[i][j].draw(WIDTH, HEIGHT, OFFSET, g);
			}
		}
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid[0].length; j++) {
				if(grid[i][j].isRoomLabel()) {
					grid[i][j].drawRoomLabel(WIDTH, HEIGHT, OFFSET, g);
				}
			}
		}
		Player one = playerList.get(0);
		if (one.getRow() == 0 && one.getColumn() == 0) {
			this.getCell(2, 0).setOccupied(true);
			one.setLocation(2, 0);
			one.draw(32,  31,  3, g);
		}
		else {
			one.draw(32,  31,  3, g);
		}

		Player two = playerList.get(1);
		if(two.getRow() == 0 && two.getColumn() ==0) {
			this.getCell(10, 0).setOccupied(true);
			two.setLocation(10, 0);
			two.draw(32, 31, 3, g);
		}
		else {
			two.draw(32, 31, 3, g);
		}


		Player three = playerList.get(2);
		if(three.getRow() == 0 && three.getColumn() == 0) {
			this.getCell(18, 15).setOccupied(true);
			three.setLocation(18, 13);
		}
		else {
			three.draw(32, 31, 3, g);
		}

		Player four = playerList.get(3);
		if(four.getRow() == 0 && four.getColumn() == 0) {
			this.getCell(9, 19).setOccupied(true);
			four.setLocation(9, 19);
			four.draw(32, 31, 3, g);
		}
		else {
			four.draw(32, 31, 3, g);
		}

		Player five = playerList.get(4);
		if(five.getRow() == 0 && five.getColumn() == 0) {
			this.getCell(0, 12).setOccupied(true);
			five.setLocation(0, 12);
			five.draw(32, 31, 3, g);
		}
		else {
			five.draw(32, 31, 3, g);
		}


		Player six = playerList.get(5);
		if(six.getRow() == 0 && six.getColumn() == 0) {
			this.getCell(0, 9).setOccupied(true);
			six.setLocation(0, 9);
			six.draw(32, 31, 3, g);
		}
		else {
			six.draw(32, 31, 3, g);
		}
	}

	private class clickedBoardListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// turn is not finished yet
			GameControlPanel.hasFinished = false;
			if (Board.getInstance().getPlayerList().get(GameControlPanel.currPlayerNum % 6) instanceof HumanPlayer) {
				HashSet<BoardCell> targets = Board.getInstance().getTargets();
				boolean isTarget = false;
				for (BoardCell c: targets) {
					//Large "if" statement to see if person clicks on a target
					if (e.getPoint().getX() > c.getCol() * Board.WIDTH + Board.OFFSET && e.getPoint().getX() < c.getCol() * Board.WIDTH+ Board.OFFSET + Board.WIDTH) {
						if (e.getPoint().getY() > c.getRow() * Board.HEIGHT + Board.OFFSET && e.getPoint().getY() < c.getRow() * Board.HEIGHT+ Board.OFFSET + Board.HEIGHT) {
							c.isOccupied = true;
							Board.getInstance().grid[Board.getInstance().getPlayerList().get(GameControlPanel.currPlayerNum % 6).row][Board.getInstance().getPlayerList().get(GameControlPanel.currPlayerNum % 6).column].setOccupied(false);
							isTarget = true;
							Board.getInstance().getPlayerList().get(GameControlPanel.currPlayerNum % 6).setLocation(c.getRow(), c.getCol());
							if(c.getIsRoom()) {
								// HANDLE SUGGESTION is commented out so that we don't get nullptr exceptions.
								//Board.getInstance().handleSuggestion(null, null);
							}
							for (BoardCell p: targets) {
								p.setTarget(false);
							}
							// clears target and repaints the board with cyan
							Board.getInstance().clearTarget();
							Board.getInstance().repaint();
							// turn is now finished, can move on 
							GameControlPanel.hasFinished = true;
							break;
						}
					}
				}
				//if the person does not click on a target
				if (!isTarget) {
					System.out.println("Error: Not a target");
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}


	public void clearTarget() {
		// TODO Auto-generated method stub
		this.targets.clear();
	}
}


