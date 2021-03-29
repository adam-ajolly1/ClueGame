package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	public BoardCell selectTargets() {
		return new BoardCell();
	}
	public Solution createSuggestion() {
		// find out what room they are in 
		BoardCell location = new BoardCell(this.row, this.column);
		if(location.isRoomCenter()) {
			Room playerRoom = location.getCorrespondingRoom();
		}
		
		// pull random weapons / people from unseen list -> there is a deck variable
		// 
		Solution suggestion = new Solution(null, null, null);
		return suggestion;
	}
	

}
