package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 19;
	public static final int NUM_COLUMNS = 21;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board2;

	@BeforeAll
	public static void setUp() {
		board2 = new Board();
		
		// Board is singleton, get the only instance
		board2 = Board.getInstance();
		// set the file names to use my config files
		board2.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// TODO: for some reason, I couldn't get the ClueSetup.txt to show up, so I just
		// changed the contents of ClueSetup306.txt to be our contents. 
		// Initialize will load BOTH config files
		board2.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Marquez", board2.getRoom('M').getName() );
		assertEquals("Coors Tek", board2.getRoom('O').getName() );
		assertEquals("Trads", board2.getRoom('T').getName() );
		assertEquals("Library", board2.getRoom('L').getName() );
		assertEquals("Rec Center", board2.getRoom('R').getName() );
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board2.getNumRows());
		assertEquals(NUM_COLUMNS, board2.getNumCols());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board2.getCell(11, 14);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board2.getCell(3, 4);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board2.getCell(11, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board2.getCell(15, 2);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that walkways are not doors
		cell = board2.getCell(12, 14);
		assertFalse(cell.isDoorway());
	}
	

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board2.getNumRows(); row++)
			for (int col = 0; col < board2.getNumCols(); col++) {
				BoardCell cell = board2.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board2.getCell( 1, 2);
		Room room = board2.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Trads" ) ;
		assertFalse( cell.isLabel() );
		assertFalse( cell.isRoomCenter() ) ;
		assertFalse( cell.isDoorway()) ;

		// this is a label cell to test
		//I CHANGED THE BUILDING NAME FROM BROWNBUILDING TO LIBRARY BECAUSE ROW 2 AND COL 19 IS THE LIBRARY
		cell = board2.getCell(0, 17);
		room = board2.getRoom( cell );
		assertTrue( room != null );
		assertEquals( room.getName(), "Library" ) ;
		assertTrue( cell.isLabel());
		assertTrue( room.getLabelCell() == cell );
		
		// this is a room center cell to test
		cell = board2.getCell(12, 10);
		room = board2.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Coors Tek" ) ;
		assertTrue( cell.isRoomCenter() );
		assertTrue( room.getCenterCell() == cell );
		
		// this is a secret passage test
		cell = board2.getCell(14, 0);
		room = board2.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Rec Center" ) ;
		assertTrue( cell.getSecretPassage() == 'L' );
		
		// test a walkway
		cell = board2.getCell(18, 16);
		room = board2.getRoom( cell ) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue( room != null );
		assertEquals( room.getName(), "Walkway" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
		// test a closet
		cell = board2.getCell(0, 1);
		room = board2.getRoom( cell ) ;
		assertTrue( room != null );
		assertEquals( room.getName(), "Unused" ) ;
		assertFalse( cell.isRoomCenter() );
		assertFalse( cell.isLabel() );
		
	}

}
