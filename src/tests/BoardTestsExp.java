package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard test;

	private static final int[][] INTS = new int [4][4];
	@BeforeEach
	// TODO: Come back to this (office hours?)
	// create a test board object to test all other adjacencies on
	void init() {
		int [][] arr = INTS;
		test = new TestBoard(arr);
	}



	@Test
	void testAdjacencyTopLeftCorner() {
		// Tests adjacency lists on the top left corner
		TestBoardCell cell = test.getCell(0, 0);
		HashSet<TestBoardCell> testAdj = cell.getAdjList();
		Assert.assertTrue(testAdj.contains(test.getCell(1, 0)));
		Assert.assertTrue(testAdj.contains(test.getCell(0, 1)));
		Assert.assertEquals(2, testAdj.size());


	}
	@Test
	void testAdjacencyBottomRightCorner() {
		// tests adjacency on the bottom right hand corner 
		TestBoardCell cell = test.getCell(3, 3);
		HashSet<TestBoardCell> testAdj = cell.getAdjList();
		Assert.assertTrue(testAdj.contains(test.getCell(2, 3)));
		Assert.assertTrue(testAdj.contains(test.getCell(3, 2)));
		Assert.assertEquals(2, testAdj.size());
	}

	@Test
	void testAdjacencyRightEdge() {
		// tests adjacency lists on the right edge
		TestBoardCell cell = test.getCell(1, 3);
		TestBoardCell adjacentTop = new TestBoardCell(0, 3);
		TestBoardCell adjacentBottom = new TestBoardCell(2, 3);
		TestBoardCell adjacentLeft = new TestBoardCell(1, 2);

		Assert.assertTrue(cell.getAdjList().contains(adjacentTop));
		Assert.assertTrue(cell.getAdjList().contains(adjacentBottom));
		Assert.assertTrue(cell.getAdjList().contains(adjacentLeft));
		Assert.assertEquals(3, cell.getAdjList().size());

	}	
	@Test
	void testAdjacencyLeftEdge() {
		// tests adjacency on the left edge
		TestBoardCell cell = test.getCell(3, 0);
		TestBoardCell adjacentRow = new TestBoardCell(3,1);
		TestBoardCell adjacentCol = new TestBoardCell(2,0);

		Assert.assertTrue(cell.getAdjList().contains(adjacentRow));

		Assert.assertTrue(cell.getAdjList().contains(adjacentCol));
		Assert.assertEquals(2, cell.getAdjList().size());

	}
	@Test
	void testAdjacencyMiddle() {
		// tests adjacency lists on the middle of the board
		TestBoardCell cell = test.getCell(1, 1);
		TestBoardCell adjacentLeft = new TestBoardCell(0,1);
		TestBoardCell adjacentRight = new TestBoardCell(2,1);
		TestBoardCell adjacentTop = new TestBoardCell(1,0);
		TestBoardCell adjacentBottom = new TestBoardCell(1,2);

		Assert.assertTrue(cell.getAdjList().contains(adjacentLeft));

		Assert.assertTrue(cell.getAdjList().contains(adjacentRight));

		Assert.assertTrue(cell.getAdjList().contains(adjacentTop));

		Assert.assertTrue(cell.getAdjList().contains(adjacentBottom));
		Assert.assertEquals(4, cell.getAdjList().size());
	}
	@Test
	void testTargets4() {
		// tests a move of 4 on an empty board
		//(0, 2), (1,1), (2, 0), (3, 1), (2, 2), (1, 3)
		TestBoardCell cell = test.getCell(0,0);
		test.calcTargets(cell, 4);
		HashSet<TestBoardCell> targets = test.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(test.getCell(0,2)));
		Assert.assertTrue(targets.contains(test.getCell(1,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,0)));
		Assert.assertTrue(targets.contains(test.getCell(3,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,2)));
		Assert.assertTrue(targets.contains(test.getCell(1,3)));


	}
	@Test
	void testTargets5() {

		TestBoardCell cell = test.getCell(0,0);
		test.calcTargets(cell, 5);
		HashSet<TestBoardCell> targets = test.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(test.getCell(0,1)));
		Assert.assertTrue(targets.contains(test.getCell(1,0)));
		Assert.assertTrue(targets.contains(test.getCell(0,3)));
		Assert.assertTrue(targets.contains(test.getCell(3,0)));
		Assert.assertTrue(targets.contains(test.getCell(2,1)));
		Assert.assertTrue(targets.contains(test.getCell(1,2)));
		Assert.assertTrue(targets.contains(test.getCell(2,3)));
		Assert.assertTrue(targets.contains(test.getCell(3,2)));


	}
	@Test
	void testTargets2() {

		TestBoardCell cell = test.getCell(0,0);
		test.calcTargets(cell, 2);
		HashSet<TestBoardCell> targets = test.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(test.getCell(1,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,0)));
		Assert.assertTrue(targets.contains(test.getCell(0,2)));

	}
	@Test
	void testOccupied() {
		// tests a move of 4 on an empty board
		//(0, 2), (1,1), (2, 0), (3, 1), (2, 2), (1, 3)
		TestBoardCell cell = test.getCell(0,0);
		test.calcTargets(cell, 4);
		test.getCell(3, 1).setOccupied(true);
		test.getCell(1, 3).setOccupied(true);
		HashSet<TestBoardCell> targets = test.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(test.getCell(0,2)));
		Assert.assertTrue(targets.contains(test.getCell(1,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,0)));
		Assert.assertFalse(targets.contains(test.getCell(3,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,2)));
		Assert.assertFalse(targets.contains(test.getCell(1,3)));


	}
	@Test
	void testRoom() {
		TestBoardCell cell = test.getCell(0,0);
		test.calcTargets(cell, 4);
		test.getCell(3, 1).setRoom(true);
		test.getCell(1, 3).setRoom(true);
		HashSet<TestBoardCell> targets = test.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(test.getCell(0,2)));
		Assert.assertTrue(targets.contains(test.getCell(1,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,0)));
		Assert.assertFalse(targets.contains(test.getCell(3,1)));
		Assert.assertTrue(targets.contains(test.getCell(2,2)));
		Assert.assertFalse(targets.contains(test.getCell(1,3)));
	}


}
