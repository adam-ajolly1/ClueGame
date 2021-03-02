package experiment;
// Test commit comment
import java.util.HashSet;

public class TestBoard {
	public final static int COLS = 4;
	public final static int ROWS = 4;   
	private TestBoardCell[][] board;
	private HashSet<TestBoardCell> targets;
	private HashSet<TestBoardCell> visited;
	public TestBoard() 
	{
		board = new TestBoardCell[ROWS][COLS];
		for(int row = 0; row < ROWS; row ++) {
			for(int col = 0; col < COLS; col ++) {
				board[row][col] = new TestBoardCell(row, col);
			}
		}
		for(int row = 0; row < ROWS; row ++) {
			for(int col = 0; col < COLS; col ++) {
				makeAdjacencyList(board[row][col]);
			}
		}
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		
		for(TestBoardCell c: thisCell.getAdjList()) {
			if(visited.contains(c) || c.getIsOccupied()) {
				continue; // skip this cell
			} 
			visited.add(c);
			if (numSteps == 1 || c.getIsRoom()) {
				targets.add(c);
			} else {
				findAllTargets(c, numSteps - 1);
			}
			visited.remove(c);
		}
	}
	public HashSet<TestBoardCell> getTargets(){
		return targets;
	}
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}

	public void makeAdjacencyList(TestBoardCell cell) {

		if(cell.getRow() +1 < ROWS) {
			cell.addAdjacency(this.getCell(cell.getRow()+1, cell.getCol()));
		}
		if(cell.getRow() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow()-1, cell.getCol()));
		}
		if(cell.getCol() +1 < COLS) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()+1));
		}
		if(cell.getCol() -1 >= 0) {
			cell.addAdjacency(this.getCell(cell.getRow(), cell.getCol()-1));
		}
	}
}
