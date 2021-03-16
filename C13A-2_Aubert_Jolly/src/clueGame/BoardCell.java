package clueGame;

import java.util.HashSet;

import experiment.TestBoardCell;


public class BoardCell {
	// private instance variables for BoardCell
	private int row;
	private int col;
	private HashSet<BoardCell> adjacencyList = new HashSet<BoardCell>();
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isUnused;
	private char secretPassage = 'a';
	private DoorDirection doordirection = DoorDirection.NONE;
	private boolean doorWay;
	private Room correspondingRoom;
	boolean isOccupied;
	boolean isRoom = false;

	// BoardCell default constructor
	public BoardCell() {
		row = 0;
		col = 0;
	}

	// BoardCell parameterized constructor
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	
	// adds a cell to adjacency list of another cell 
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}
	//to string method used for debugging
	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}



	//GETTERS
	public boolean isUnused() {
		return isUnused;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public char getInitial() {
		return initial;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public DoorDirection getDoordirection() {
		return doordirection;
	}
	public boolean isDoorway() {
		return doorWay;
	}
	public DoorDirection getDoorDirection() {
		return doordirection;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public HashSet<BoardCell> getAdjList() {
		return adjacencyList;
	}
	public boolean getIsOccupied() {
		return isOccupied;
	}
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean getIsRoom() {
		return isRoom;
	}
	public Room getCorrespondingRoom() {
		return correspondingRoom;
	}
	
	//SETTERS
	public void setCorrespondingRoom(Room correspondingRoom) {
		this.correspondingRoom = correspondingRoom;
	}
	public void setUnused(boolean isUnused) {
		this.isUnused = isUnused;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public void setDoorway(boolean doorway) {
		doorWay = doorway;
	}
	public void setInitial(char initial) {
		this.initial = initial;
	}
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	public void setDoordirection(DoorDirection doordirection) {
		this.doordirection = doordirection;
	}
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
}
