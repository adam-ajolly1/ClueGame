package experiment;
// Test commit comment
import java.util.HashSet;

public class TestBoard {
	public final static int COLS = 4;
	public final static int ROWS = 4;   
	private TestBoardCell[][] board = new TestBoardCell[ROWS][COLS];
	private HashSet<TestBoardCell> targets;
	private HashSet<TestBoardCell> visited;
	public TestBoard(TestBoardCell [][] arr) 
	{
		this.board = arr;
	}
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		if (thisCell.isOccupied()) {
			return;
		}
		if (thisCell.isRoom()) {
			numSteps = 1;
		}
		makeAdjacencyList(thisCell);
		HashSet<TestBoardCell> adj = thisCell.getAdjList();
		for(TestBoardCell c: adj) {
			if(visited.contains(c)) {
				continue; // skip this cell
			} 
			visited.add(c);
			if (numSteps == 1) {
				targets.add(c);
			} else {
				findAllTargets(c, numSteps - 1);
			}
			visited.remove(c);
		}
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
