package clueGame;

import java.util.HashSet;

import experiment.TestBoardCell;

public class Room {
	// instance variables for the Room class 
	private String name = "";
	private BoardCell centerCell = new BoardCell(0, 0);
	private BoardCell labelCell = new BoardCell(0, 0) ;
	private BoardCell correspondingSecretPassage = null;
	private HashSet<BoardCell> correspondingDoors = new HashSet<BoardCell>();

	// room default constructor
	public Room() {
		super();
	}
	// room parameterized constructor
	public Room(String name) {
		super();
		this.name = name;
	}


	// adds a corresponding door to its room 
	public void addToList(BoardCell cell) {
		correspondingDoors.add(cell);
	}
	
	//To string method used for debugging
	@Override
	public String toString() {
		return "Room [correspondingSecretPassage=" + correspondingSecretPassage + ", correspondingDoors="
				+ correspondingDoors + ", labelCell=" + labelCell + "]";
	}

	//GETTERS
	public String getName() {
		return name;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public BoardCell getSecretPassage() {
		return correspondingSecretPassage;
	}
	public HashSet<BoardCell> getCorrespondingDoors() {
		return correspondingDoors;
	}


	//SETTERS
	public void setName(String name) {
		this.name = name;
	}
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public void setSecretPassage(BoardCell secretPassage) {
		this.correspondingSecretPassage = secretPassage;
	}




}
