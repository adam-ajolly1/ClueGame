package clueGame;

import experiment.TestBoardCell;

public class Room {
	private String name = "";
	private BoardCell centerCell = new BoardCell(0, 0);
	public Room() {
		super();
	}
	private BoardCell labelCell = new BoardCell(0, 0) ;
	
	public Room(String name) {
		super();
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
