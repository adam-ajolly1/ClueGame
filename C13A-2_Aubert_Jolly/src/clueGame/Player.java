package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color color;
	protected int row = 0;
	protected int column = 0;
	private HashSet<Card> hand = new HashSet<Card>();
	private HashSet<Card> seenList = new HashSet<Card>();
	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}
	
	public void updateHand(Card card) {
		this.hand.add(card);
		this.seenList.add(card);
	}
	
	@Override
	public String toString() {
		return "Player [row=" + row + ", column=" + column + "]";
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
	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public BoardCell getLocation() {
		return Board.getInstance().getCell(row, column);
	}
	
	public HashSet<Card> getHand() {
		return hand;
	}
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matches = new ArrayList<Card>();
		
		for(Card c: this.getHand()) {
			//System.out.println(c.getCardName().substring(1));
			//System.out.println(suggestion.getRoom().getCardName());
			if(c.getCardName().substring(1).equals(suggestion.getRoom().getCardName()) || c.equals(suggestion.getPerson()) || c.equals(suggestion.getWeapon())) {
				matches.add(c);
			}
		}
		if(matches.size() == 0) {
			return null;
		}
		Random rand = new Random();
		Card toShow = matches.get(rand.nextInt(matches.size()));

		return toShow;
	}
	public void updateSeen(Card seenCard) {
		this.seenList.add(seenCard);
	}
	public HashSet<Card> getSeenList(){
		return seenList;
	}

	public void draw(int width, int height, int offset, Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.color);
		g.fillOval(this.column * width + offset, this.row * height + offset, width, height);
	}

	protected abstract Solution createSuggestion(Card correspondingRoom);
	
	
	
}
