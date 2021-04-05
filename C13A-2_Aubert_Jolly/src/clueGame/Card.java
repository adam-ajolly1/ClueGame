package clueGame;

public class Card {
	private CardType cardType; 
	private String cardName;
	
	
	
	public Card(CardType cardType, String cardName) {
		super();
		this.cardType = cardType;
		this.cardName = cardName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
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
