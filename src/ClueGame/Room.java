package clueGame;

import experiment.TestBoardCell;

public class Room {
	private String name = "";
	private BoardCell centerCell = new BoardCell(0, 0);
	private BoardCell labelCell = new BoardCell(0, 0);
	
	public Room() {
		// TODO: Make default constructor
	}
	public Room(String name) {
		this.name = name;
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
	public void setName(String name) {
		this.name = name;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
}
