package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}
	
	String personSuggestion;
	String weaponSuggestion;
	String roomSuggestion;
	
	public String getRoomSuggestion() {
		return roomSuggestion;
	}

	public void setRoomSuggestion(String roomSuggestion) {
		this.roomSuggestion = roomSuggestion;
	}

	@Override
	protected Solution createSuggestion(Card correspondingRoom) {
		// TODO Auto-generated method stub
		Card personCard = null;
		Card weaponCard = null;
		
		
		
		//if the inputted string is the same as the card, set the card
		for (Card c: Board.getInstance().getDeck()) {
			if (c.getCardName().equals(personSuggestion)) {
				personCard = c;
			} else if (c.getCardName().equals(weaponSuggestion)) { 
				weaponCard = c;
			}
		}
		return new Solution(correspondingRoom, personCard, weaponCard);
	}

	public String getPersonSuggestion() {
		return personSuggestion;
	}

	public String getWeaponSuggestion() {
		return weaponSuggestion;
	}

	public void setPersonSuggestion(String personSuggestion) {
		this.personSuggestion = personSuggestion;
	}

	public void setWeaponSuggestion(String weaponSuggestion) {
		this.weaponSuggestion = weaponSuggestion;
	}


}
