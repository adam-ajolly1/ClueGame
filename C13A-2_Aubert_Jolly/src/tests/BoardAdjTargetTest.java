package tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		BoardCell testCell = board.getCell(17, 9);
		HashSet<BoardCell> testList = testCell.getAdjList();

		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(16, 6)));
		assertTrue(testList.contains(board.getCell(0, 5)));

		// now test the trads
		BoardCell tradsTestCell = board.getCell(0,5);
		Set<BoardCell> tradsTestList = tradsTestCell.getAdjList();
		assertEquals(2, tradsTestList.size());
		// 4, 4 // 5,5 // 4,6 // 5, 7 // 4,8
		assertTrue(tradsTestList.contains(board.getCell(3, 4)));
		assertTrue(tradsTestList.contains(board.getCell(3, 6)));

		// one more room, CTLM
		BoardCell ctlmTestCell = board.getCell(1,17);
		Set<BoardCell> ctlmTestList = ctlmTestCell.getAdjList();
		assertEquals(1, ctlmTestList.size());
		// 4, 16 // 4, 18
		assertTrue(ctlmTestList.contains(board.getCell(3, 16)));
	}


	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		// door to trads - (0,5), (3, 3), (4, 4), (3,5)
		BoardCell testDoor1 = board.getCell(3, 4);
		HashSet<BoardCell> testList = testDoor1.getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(0, 5)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(4, 4)));
		assertTrue(testList.contains(board.getCell(3, 5)));

		// door to marquez - (7, 17)*, (9, 16), (10, 17), (9, 18)
		BoardCell testDoor2 = board.getCell(9, 17);
		HashSet<BoardCell> testList2 = testDoor2.getAdjList();
		assertEquals(4, testList2.size());
		assertTrue(testList2.contains(board.getCell(7, 17)));
		assertTrue(testList2.contains(board.getCell(9, 16)));
		assertTrue(testList2.contains(board.getCell(10, 17)));
		assertTrue(testList2.contains(board.getCell(9, 18)));

		// door to coorsktek - (12, 10)*, (10,6), (11, 5), (12, 6)

		BoardCell testDoor3 = board.getCell(11, 6);
		HashSet<BoardCell> testList3 = testDoor3.getAdjList();
		assertEquals(4, testList3.size());
		assertTrue(testList3.contains(board.getCell(12, 10)));
		assertTrue(testList3.contains(board.getCell(10, 6)));
		assertTrue(testList3.contains(board.getCell(11, 5)));
		assertTrue(testList3.contains(board.getCell(12, 6)));
	}

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		BoardCell testWalkway = board.getCell(18, 13);
		HashSet<BoardCell> testWalkwayList = testWalkway.getAdjList();
		assertEquals(2, testWalkwayList.size());
		assertTrue(testWalkwayList.contains(board.getCell(17, 13)));
		assertTrue(testWalkwayList.contains(board.getCell(18, 14)));

		// Test near a door but not adjacent
		BoardCell testWalkway2 = board.getCell(15, 15);
		HashSet<BoardCell> testWalkwayList2 = testWalkway2.getAdjList();
		assertEquals(3, testWalkwayList2.size());
		assertTrue(testWalkwayList2.contains(board.getCell(15, 14)));
		assertTrue(testWalkwayList2.contains(board.getCell(14, 15)));
		assertTrue(testWalkwayList2.contains(board.getCell(15, 16)));

		// Test adjacent to walkways, between Bradford and Thomas
		BoardCell testWalkway3 = board.getCell(3, 5);
		HashSet<BoardCell> testWalkwayList3 = testWalkway3.getAdjList();
		assertEquals(4, testWalkwayList3.size());
		assertTrue(testWalkwayList3.contains(board.getCell(3, 4 )));
		assertTrue(testWalkwayList3.contains(board.getCell(2, 5)));
		assertTrue(testWalkwayList3.contains(board.getCell(4, 5)));
		assertTrue(testWalkwayList3.contains(board.getCell(3, 6)));

		// Test next to closet
		BoardCell testWalkway4 = board.getCell(8, 8);
		HashSet<BoardCell> testWalkwayList4 = testWalkway4.getAdjList();
		assertEquals(3, testWalkwayList4.size());
		assertTrue(testWalkwayList4.contains(board.getCell(8, 7 )));
		assertTrue(testWalkwayList4.contains(board.getCell(9, 8)));
		assertTrue(testWalkwayList4.contains(board.getCell(8, 9)));
	}


	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInMarquez() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(3, 18)));	
		assertTrue(targets.contains(board.getCell(13, 17)));	

		// test a roll of 3
		board.calcTargets(board.getCell(7, 17), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(9, 15)));
		assertTrue(targets.contains(board.getCell(10, 16)));	
		assertTrue(targets.contains(board.getCell(3, 16)));
		assertTrue(targets.contains(board.getCell(9, 19)));	

		// test a roll of 4
		board.calcTargets(board.getCell(7, 17), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(3, 15)));
		assertTrue(targets.contains(board.getCell(8, 15)));	
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(10, 19)));	
	}

	@Test
	public void testTargetsInMinesMarket() {
		// everything for yaaaa
		// test a roll of 1
		board.calcTargets(board.getCell(17, 1), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(15, 2)));	

		// test a roll of 3
		board.calcTargets(board.getCell(17, 1), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(15, 0)));
		assertTrue(targets.contains(board.getCell(13, 1)));	
		assertTrue(targets.contains(board.getCell(14, 3)));
		assertTrue(targets.contains(board.getCell(15, 4)));	

		// test a roll of 4
		board.calcTargets(board.getCell(17, 1), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(13, 1)));
		assertTrue(targets.contains(board.getCell(14, 4)));	
		assertTrue(targets.contains(board.getCell(14, 2)));	
		assertTrue(targets.contains(board.getCell(15, 3)));	
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(16, 4)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door (CTLM)
		board.calcTargets(board.getCell(16, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(16, 18)));	
		assertTrue(targets.contains(board.getCell(17, 19)));	
		assertTrue(targets.contains(board.getCell(16, 20)));

		// test a roll of 3
		board.calcTargets(board.getCell(16, 19), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(15, 17)));
		assertTrue(targets.contains(board.getCell(16, 18)));	
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertTrue(targets.contains(board.getCell(18, 20)));	

		// test a roll of 4
		board.calcTargets(board.getCell(16, 19), 4);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(15, 18)));	
		assertTrue(targets.contains(board.getCell(15, 20)));
		assertTrue(targets.contains(board.getCell(17, 18)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(10, 1)));
		assertTrue(targets.contains(board.getCell(10, 3)));	
		assertTrue(targets.contains(board.getCell(11, 2)));	

		// test a roll of 3
		board.calcTargets(board.getCell(10, 2), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(11, 4)));
		assertTrue(targets.contains(board.getCell(13, 1)));	

		// test a roll of 4
		board.calcTargets(board.getCell(10, 2), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(11, 5)));
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(7, 1)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(12, 6)));	

		// test a roll of 3
		board.calcTargets(board.getCell(13, 6), 3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(14, 8)));
		assertTrue(targets.contains(board.getCell(12, 10)));	

		// test a roll of 4
		board.calcTargets(board.getCell(13, 6), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(17, 9)));
		assertTrue(targets.contains(board.getCell(12, 10)));
		assertTrue(targets.contains(board.getCell(11, 6)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(12, 5).setOccupied(true);
		board.calcTargets(board.getCell(13, 6), 4);
		board.getCell(12, 5).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(9, 6)));
		assertTrue(targets.contains(board.getCell(14, 3)));
		assertTrue(targets.contains(board.getCell(14, 7)));	
		assertFalse(targets.contains(board.getCell(12, 5)));
		
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(8, 13).setOccupied(true);
		board.getCell(7, 14).setOccupied(true);
		board.calcTargets(board.getCell(8, 14), 1);
		board.getCell(8, 13).setOccupied(false);
		board.getCell(7, 14).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 15)));	
		assertTrue(targets.contains(board.getCell(9, 14)));	
			

		// check leaving a room with a blocked doorway
		board.getCell(13, 15).setOccupied(true);
		board.calcTargets(board.getCell(13, 17), 3);
		board.getCell(13, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(16, 17)));
		assertTrue(targets.contains(board.getCell(17, 18)));	
		assertTrue(targets.contains(board.getCell(15, 18)));
		assertFalse(targets.contains(board.getCell(11, 5)));

	}
}