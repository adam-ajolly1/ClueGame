package clueGame;

public class Card {
	private CardType cardType; 
	private String cardName;
	
	
	
	public Card(CardType cardType, String cardName) {
		super();
		this.cardType = cardType;
		this.cardName = cardName;
	}
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public Card() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", cardName=" + cardName + "]";
	}

	
}
