package experiment;

import java.util.HashSet;

public class TestBoardCell {
	private int row;
	private int col;
	private HashSet<TestBoardCell> adjacencyList = new HashSet<TestBoardCell>(); 
	private boolean isRoom;
	private boolean isOccupied;

	
	public TestBoardCell(int myRow, int myCol) {
		row = myRow;
		col = myCol;
	}
	public void addAdjacency(TestBoardCell cell) {
		// add onto the adjacencylist here
	}
	public HashSet<TestBoardCell> getAdjList() {
		return new HashSet<TestBoardCell>();
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	
	

}
