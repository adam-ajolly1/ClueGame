package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Map.Entry;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	public BoardCell selectTargets() {
		return new BoardCell();
	}
	public Solution createSuggestion(Card room) {

		ArrayList<Card> possibleWeapons = new ArrayList<Card>();
		ArrayList<Card> possiblePeople = new ArrayList<Card>();
		// adds all cards that are possible as suggestions from the deck to
		// appropriate person / weapon lists
		for(Card c: Board.getInstance().getDeck()) {
			if(c.getCardType() == CardType.ROOM) {
				continue;
			}
			int counter = 0;
			for(Card seen: this.getSeenList()) {
				if(seen.equals(c)) {
					counter ++;
				}
			}
			if(counter > 0) {
				continue;
			}
			if(c.getCardType() == CardType.PERSON) {
				possiblePeople.add(c);
			}
			else {
				possibleWeapons.add(c);
			}
		}
		Random rand = new Random();
		Card randomWeapon = possibleWeapons.get(rand.nextInt(possibleWeapons.size()));
		Card randomPerson = possiblePeople.get(rand.nextInt(possiblePeople.size()));
		return new Solution(room, randomPerson, randomWeapon);
	}
	public BoardCell selectTarget(HashSet<BoardCell> targets) {
		for(BoardCell t: targets) {
			if(t.getIsRoom() == true) {
				Room roomOfInterest = null;
				char r = t.getInitial();
				for (Entry<Room, Character> entry : Board.getInstance().getRoomMap().entrySet()) {
					if(r == entry.getValue()) {
						roomOfInterest = entry.getKey();
					}
				}
				Card roomCard = Board.getInstance().roomToCard(roomOfInterest);
				int counter = 0;
				for(Card x: this.getSeenList()) {
					if(x.equals(roomCard)) {
						counter ++;
					}
				}
				if(counter > 0) {
					continue;
				}
				else {
					return t;
				}
			}
		}
			// should make it here if there are no rooms
			// returns a random member of the targets set
			int size = targets.size();
			int item = new Random().nextInt(size); 
			int i = 0;
			for(BoardCell cell : targets)
			{
				if (i == item)
					return cell;
				i++;
			}

			return new BoardCell();
		}


	}
