package clueGame;

import experiment.TestBoardCell;

public class Room {
	private String name = "";
	private BoardCell centerCell = new BoardCell(0, 0);
	private BoardCell labelCell = new BoardCell(0, 0) ;
	
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
