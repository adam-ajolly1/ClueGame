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

public class GameSolutionTest {

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
	//include test for checkAccusation(), disproveSuggestion() and handleSuggestion().
	@Test
	public void testcheckAccusation() {
		Card weaponAns = new Card(CardType.WEAPON, "Blue Key");
		Card personAns = new Card(CardType.PERSON, "Marlene");
		Card roomAns = new Card(CardType.ROOM, "Trads");
		board.setTheAnswer(roomAns, personAns, weaponAns);
		// asserts true if all cards in accusation are correct
		assertTrue(board.checkAccusation(roomAns, personAns, weaponAns));
		// asserts false if room is false
		Card wrongRoom = new Card(CardType.ROOM, "Brown Building");
		assertFalse(board.checkAccusation(wrongRoom, personAns, weaponAns));
		// asserts false if person is false
		Card wrongPerson = new Card(CardType.PERSON, "Marvin");
		assertFalse(board.checkAccusation(roomAns, wrongPerson, weaponAns));
		// asserts false if weapon is false
		Card wrongWeapon = new Card(CardType.WEAPON, "Starbucks Cup");
	}
	
	@Test
	public void testdisproveSuggestion() {
		Card weaponInHand = new Card(CardType.WEAPON, "CS@Mines Tesla");
		Card roomInHand = new Card(CardType.ROOM, "Mines Market");
		Card personInHand = new Card(CardType.PERSON, "Katherine");
		Player test = new ComputerPlayer("Test", Color.black);
		test.updateHand(weaponInHand);
		test.updateHand(roomInHand);
		test.updateHand(personInHand);
		Card suggestionRoom = new Card(CardType.ROOM, "Trads");
		Card suggestionWeapon = new Card(CardType.WEAPON, "Blue Key");
		Card suggestionPerson = new Card(CardType.PERSON, "Katherine");
		Solution testSuggestion = new Solution(suggestionRoom, suggestionPerson, suggestionWeapon);
		assertEquals(suggestionPerson, test.disproveSuggestion(testSuggestion));
		
		// alter suggestion so that there are multiple cards in player's hand
		suggestionRoom = roomInHand;
		suggestionWeapon = weaponInHand;
		
		Solution testSuggestion2 = new Solution(suggestionRoom, suggestionPerson, suggestionWeapon);
		
		// disproves a suggestion 100 times, and count how many times a room, person,
		// or weapon card is shown, and makes sure the number is reasonable
		int numRoomShown = 0;
		int numPersonShown = 0;
		int numWeaponShown = 0;
		
		for(int i = 0; i<100; i++) {
			if(test.disproveSuggestion(testSuggestion2).equals(suggestionPerson)) {
				numPersonShown ++;
			}
			else if(test.disproveSuggestion(testSuggestion2).equals(suggestionRoom)) {
				numRoomShown ++;
			}
			else if(test.disproveSuggestion(testSuggestion2).equals(suggestionWeapon)) {
				numWeaponShown ++;
			}
		}
		assertTrue(numRoomShown > 10);
		assertTrue(numPersonShown > 10);
		assertTrue(numWeaponShown > 10);
	
	
	// tests if returns null when there are no similar cards
	Card suggestionRoomNull = new Card(CardType.ROOM, "Trads");
	Card suggestionWeaponNull = new Card(CardType.WEAPON, "Blue Key");
	Card suggestionPersonNull = new Card(CardType.PERSON, "Adam");
	
	Solution testSuggestionNull = new Solution(suggestionRoomNull, suggestionPersonNull, suggestionWeaponNull);
	Card nullCard = null;
	assertEquals(nullCard, test.disproveSuggestion(testSuggestionNull));
	
	}
	@Test
	public void testhandleSuggestion() {
		board.clearPlayerList();
		
		Player test1 = new ComputerPlayer("Test 1", Color.green);
		Card katherine = new Card(CardType.PERSON, "Katherine");
		test1.updateHand(katherine);
		
		Player test2 = new ComputerPlayer("Test 2", Color.blue);
		Card fork = new Card(CardType.WEAPON, "Mines Market Fork");
		test2.updateHand(fork);
		
		Player test3 = new ComputerPlayer("Test 3", Color.cyan);
		Card library = new Card(CardType.ROOM, "Library");
		test3.updateHand(library);
		
		// adds all computer players to the player list
		board.addToPlayerList(test1);
		board.addToPlayerList(test2);
		board.addToPlayerList(test3);
		
		// creates human player and adds them to player list
		Player suggester = new HumanPlayer("Suggester", Color.black);
		board.addToPlayerList(suggester);
		
		// make a solution that no players can disprove
		Card suggestionRoomNull = new Card(CardType.ROOM, "Trads");
		Card suggestionWeaponNull = new Card(CardType.WEAPON, "Blue Key");
		Card suggestionPersonNull = new Card(CardType.PERSON, "Adam");
		Solution noneDisprove = new Solution(suggestionRoomNull, suggestionPersonNull, suggestionWeaponNull);
		assertEquals(null, board.handleSuggestion(noneDisprove, suggester));
		
		// makes a solution that player 1 can disprove
		Card suggestionPlayer1 = new Card(CardType.PERSON, "Katherine");
		Solution oneDisproves = new Solution(suggestionRoomNull, suggestionPlayer1, suggestionWeaponNull);
		assertEquals(suggestionPlayer1, board.handleSuggestion(oneDisproves, suggester));
		
		// makes a solution that player 2 and 3 can disprove, make sure only 2
		Card suggestionPlayer2 = new Card(CardType.WEAPON, "Mines Market Fork");
		Card suggestionPlayer3 = new Card(CardType.ROOM, "Library");
		Solution twoDisproves = new Solution(suggestionPlayer3, suggestionPersonNull, suggestionPlayer2);
		assertEquals(suggestionPlayer2, board.handleSuggestion(twoDisproves, suggester));
	
	}
}

