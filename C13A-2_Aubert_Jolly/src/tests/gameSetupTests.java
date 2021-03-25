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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class gameSetupTests {

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
	// checks to make sure proper deck of cards is created
	public void testDeck() {
		HashSet<Card> testDeck = board.getDeck();
		// checks for correct amount of cards
		assertEquals(21, testDeck.size());
		int numWeapons = 0;
		int numPeople = 0;
		int numRooms = 0;
		for(Card c: testDeck) {
			if(c.getCardType() == CardType.PERSON) { 
				numPeople ++;
			}
			else if(c.getCardType() == CardType.ROOM) {
				numRooms++;
			}
			else { 
				numWeapons++;
			}
		}
		assertEquals(6, numPeople);
		assertEquals(9, numRooms);
		assertEquals(6, numWeapons);
	}

	@Test
	public void testPlayerList() {
		ArrayList<Player> testPlayerList = board.getPlayerList();
		// checks for correct amount of cards
		assertEquals(6, testPlayerList.size());
		boolean containsPCJ = false;
		boolean containsMarvin = false;
		for(Player c: testPlayerList) {
//			System.out.println(c.getColor());
			if (c.getName().contentEquals("PCJ")) {
				containsPCJ = true;
				assertEquals(c.getColor(), Color.white);
			}
			if (c.getName().equals("Marvin")) {
				containsMarvin = true;
				assertEquals(c.getColor(), Color.orange);
			}

		}
		assertTrue(containsPCJ);
		assertTrue(containsMarvin);
		
	}
	@Test
	public void testanswer2() {
		Solution testSolution = board.getTheAnswer();
		assertEquals(testSolution.room.getCardType(), CardType.ROOM);
		assertEquals(testSolution.person.getCardType(), CardType.PERSON);
		assertEquals(testSolution.weapon.getCardType(), CardType.WEAPON);
	}

	@Test
	public void testanswer() {
		Card roomAns = new Card(CardType.ROOM, "Brown Building");
		Card personAns = new Card(CardType.PERSON, "Marlene");
		Card weaponAns = new Card(CardType.WEAPON, "Starbucks Cup");
		board.setTheAnswer(roomAns, personAns, weaponAns);
		Solution testAns = board.getTheAnswer();
		assertTrue(testAns.room.getCardName() == "Brown Building");
		assertTrue(testAns.person.getCardName() == "Marlene");
		assertTrue(testAns.weapon.getCardName() == "Starbucks Cup");
		assertFalse(testAns.room.getCardName() == "Student Center");
		assertFalse(testAns.person.getCardName() == "PCJ");
		assertFalse(testAns.weapon.getCardName() == "Blue Key");
	}
	
	@Test
	public void testPlayerTypes() {
	ArrayList<Player> testPlayerList = board.getPlayerList();
	int playerCount = 0;
	int compCount = 0;
	int totalCount = 0;
	for(Player p: testPlayerList) {
		if(p instanceof HumanPlayer) {
			playerCount ++;
			totalCount++;
		}
		else if(p instanceof ComputerPlayer) {
			compCount ++;
			totalCount++;
		}
		
	}
	assertEquals(5, compCount);
	assertEquals(1, playerCount);
	assertEquals(6, totalCount);
	}
	
	

	@Test
	public void testDeal() {
		HashSet<Card> testDeck = new HashSet<Card>();
		for(Player p: board.getPlayerList()) {
		//make sure all players have 3 cards
			assertTrue(p.getHand().size() == 3);
			//add everyone's cards to a set of cards
			for(Card c: p.getHand()) {
				testDeck.add(c);
			}
		}
		//make sure the total amount of cards is 18 so there are no duplicates
		assertEquals(testDeck.size(), 18);
	}
}

