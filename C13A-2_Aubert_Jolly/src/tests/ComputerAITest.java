package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class ComputerAITest {
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

	@Test
	public void testCreateSuggestion() {
		ComputerPlayer p = new ComputerPlayer("Marlene", Color.blue);		
		Card katherine = new Card(CardType.PERSON, "Katherine");
		p.updateSeen(katherine);
		Card marvin = new Card(CardType.PERSON, "Marvin");
		p.updateSeen(marvin);
		Card pcj = new Card(CardType.PERSON, "PCJ");
		p.updateSeen(pcj);
		
		// makes sure that in 100 runs, no Solutions contain seen people
		// make sure that suggesting room is correct
		Card library = new Card(CardType.ROOM, "Library");
		
		for(int i = 0; i < 100; i++) {
			Solution suggestionCreated = p.createSuggestion(library);
			assertEquals(library, suggestionCreated.getRoom());
			assertNotEquals(katherine, suggestionCreated.getPerson());
			assertNotEquals(marvin, suggestionCreated.getPerson());
			assertNotEquals(pcj, suggestionCreated.getPerson());
		}
		Card pencil = new Card(CardType.WEAPON, "Pencil");
		p.updateSeen(pencil);
		Card starbucksCup = new Card(CardType.WEAPON, "Starbucks Cup");
		p.updateSeen(starbucksCup);
		Card blueKey = new Card(CardType.WEAPON, "Blue Key");
		p.updateSeen(blueKey);
		
		// makes sure that in 100 runs, no solutions contain seen people OR weapons
		
		
		for(int i = 0; i < 100; i++) {
			Solution suggestionCreated2 = p.createSuggestion(library);
			assertEquals(library, suggestionCreated2.getRoom());
			assertNotEquals(katherine, suggestionCreated2.getPerson());
			assertNotEquals(marvin, suggestionCreated2.getPerson());
			assertNotEquals(pcj, suggestionCreated2.getPerson());
			assertNotEquals(pencil, suggestionCreated2.getWeapon());
			assertNotEquals(starbucksCup, suggestionCreated2.getWeapon());
			assertNotEquals(blueKey, suggestionCreated2.getWeapon());
			
		}	
	}
	@Test
	public void testSelectTargets() {
		
		// asserts that if there is an unseen room in targets, the player moves there
		ComputerPlayer katherine  = new ComputerPlayer("Katherine", Color.green);
		board.calcTargets(board.getCell(4, 4), 2);
		HashSet<BoardCell> targets= board.getTargets();
		BoardCell move = katherine.selectTarget(targets);
		assertEquals(board.getCell(0, 5), move); // room center of the trads
		
		// asserts that once the room is seen, the player does not move there
		Card c = new Card(CardType.ROOM, "Trads");
		katherine.updateSeen(c);
		move = katherine.selectTarget(targets);
		assertNotEquals(board.getCell(0, 5), move);
		
	}
}
