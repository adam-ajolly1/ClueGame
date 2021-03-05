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
	private char secretPassage = 'a';
	private DoorDirection doordirection;
	
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
	public boolean isRoomLabel() {
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
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public BoardCell() { // default constructor
		this.row = row;
		this.col = col;
	}
	public boolean isDoorway() {
		return false;
	}
	public DoorDirection getDoorDirection() {
		return DoorDirection.UP;
	}
	public boolean isRoomCenter() {
		return false;
	}
	public boolean isLabel() {
		return false;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
		
	}
}
