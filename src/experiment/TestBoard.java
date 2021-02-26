package experiment;
// Test commit comment
import java.util.HashSet;

public class TestBoard {
	private int[][] board;
	private HashSet<TestBoardCell> targets;
	public TestBoard(int[][] arr) 
	{
		this.board = arr;
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	public HashSet<TestBoardCell> getTargets(){
		return new HashSet<TestBoardCell>();
	}
	public TestBoardCell getCell( int row, int col) {
		TestBoardCell myCell = new TestBoardCell(row, col);
		return myCell;
	}
}
