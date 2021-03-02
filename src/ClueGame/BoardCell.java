package clueGame;

import java.util.HashSet;


public class BoardCell {
	private int row = 0;
	private int col = 0;
	private HashSet<BoardCell> adjacencyList = new HashSet<BoardCell>();
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage = 'a';
	private DoorDirection doordirection;
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
}
