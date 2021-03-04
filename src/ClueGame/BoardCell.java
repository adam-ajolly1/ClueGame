package clueGame;

import java.util.HashSet;


public class BoardCell {
	private int row = 0;
	private int col = 0;
	private HashSet<BoardCell> adjacencyList = new HashSet<BoardCell>();
	private String initial;
	private boolean roomLabel;
	private boolean roomCenter;
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
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
