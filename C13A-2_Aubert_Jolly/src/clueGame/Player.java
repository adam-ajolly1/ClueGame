package clueGame;

import java.awt.Color;
import java.util.HashSet;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	private HashSet<Card> hand = new HashSet<Card>();
	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}
	
	public void updateHand(Card card) {
		this.hand.add(card);
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public HashSet<Card> getHand() {
		return hand;
	}
	
	
	
	
}
