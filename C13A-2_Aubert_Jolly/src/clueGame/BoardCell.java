package clueGame;

import java.util.HashSet;

import experiment.TestBoardCell;


public class BoardCell {
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
	private HashSet<TestBoardCell> visited;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public BoardCell() { // default constructor Look into if we use this
		this.row = row;
		this.col = col;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public void setDoorway(boolean doorway) {
		doorWay = doorway;
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
	public void setInitial(char initial) {
		this.initial = initial;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	public DoorDirection getDoordirection() {
		return doordirection;
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
	
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
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
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "secret passage=" + secretPassage + " Is room=" + isRoom + "]";
	}
	public boolean getIsRoom() {
		return isRoom;
	}
	public Room getCorrespondingRoom() {
		return correspondingRoom;
	}
	public void setCorrespondingRoom(Room correspondingRoom) {
		this.correspondingRoom = correspondingRoom;
	}
	public boolean isUnused() {
		return isUnused;
	}
	public void setUnused(boolean isUnused) {
		this.isUnused = isUnused;
	}
}
