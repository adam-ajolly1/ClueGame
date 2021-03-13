package clueGame;

import java.util.HashSet;

import experiment.TestBoardCell;

public class Room {
	private String name = "";
	private BoardCell centerCell = new BoardCell(0, 0);
	private BoardCell correspondingSecretPassage = null;
	private HashSet<BoardCell> correspondingDoors = new HashSet<BoardCell>();
	
	public void addToList(BoardCell cell) {
		correspondingDoors.add(cell);
	}
	public HashSet<BoardCell> getCorrespondingDoors() {
		return correspondingDoors;
	}
	public Room() {
		super();
	}
	private BoardCell labelCell = new BoardCell(0, 0) ;
	
	public Room(String name) {
		super();
		this.name = name;
	}
	
	public BoardCell getSecretPassage() {
		return correspondingSecretPassage;
	}

	public void setSecretPassage(BoardCell secretPassage) {
		this.correspondingSecretPassage = secretPassage;
	}

	@Override
	public String toString() {
		return "Room [correspondingSecretPassage=" + correspondingSecretPassage + ", correspondingDoors="
				+ correspondingDoors + ", labelCell=" + labelCell + "]";
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public String getName() {
		return name;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	

}
