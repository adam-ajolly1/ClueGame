package experiment;
// Test commit comment
import java.util.HashSet;

public class TestBoard {
	private TestBoardCell[][] board;
	private HashSet<TestBoardCell> targets;
	private HashSet<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;	
	public TestBoard(TestBoardCell [][] arr) 
	{
		this.board = arr;
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	public HashSet<TestBoardCell> getTargets(){
		return new HashSet<TestBoardCell>();
	}
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}
	
	public void makeAdjacencyList(TestBoardCell cell) {
		
		if(cell.getRow() +1 <= ROWS) {
			cell.addAdjacency(this.getCell(cell.getRow()+1, cell.getCol()));
		}
		if(cell.getRow() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow()-1, cell.getCol()));
		}
		if(cell.getCol() +1 <= COLS) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()+1));
		}
		if(cell.getCol() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()-1));
		}
	}
}
