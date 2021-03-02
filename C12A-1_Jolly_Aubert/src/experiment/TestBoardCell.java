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
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
		
	}
	public HashSet<TestBoardCell> getAdjList() {
		return adjacencyList;
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
