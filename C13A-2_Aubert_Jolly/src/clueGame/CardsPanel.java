package clueGame;
import java.awt.BorderLayout;
import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel {
	// creates JPanels as instance variables so they can be modified within main 
	JPanel seenWeapon = new JPanel();
	JPanel seenPerson = new JPanel();
	JPanel seenRoom = new JPanel();
	JPanel handWeapon = new JPanel();
	JPanel handRoom = new JPanel();
	JPanel handPerson = new JPanel();
	
	// static variables for the starting size of people / weapon / room panels
	public static int numWeapons = 2;
	public static int numPeople = 2;
	public static int numRooms = 2;
	JTextField none = new JTextField("none");
	
	public CardsPanel(HashSet<Card> hand) {
		setLayout(new GridLayout(1,0));
		
		JPanel knownCards = createKnownCardsPanel();
		add(knownCards);
		
		// adds cards to "in hand"
		makeHand(hand);
	}
	
	// adds each card to the appropriate hand jpanel
	private void makeHand(HashSet<Card> hand) {
		for (Card c: hand) {
			if(c.getCardType() == CardType.WEAPON) {
				handWeapon.remove(none);
				handWeapon.add(new JTextField(c.getCardName()));
			}
			else if(c.getCardType() == CardType.PERSON) {
				handPerson.remove(none);
				handPerson.add(new JTextField(c.getCardName()));
			}
			else if(c.getCardType() == CardType.ROOM) {
				handRoom.remove(none);
				handRoom.add(new JTextField(c.getCardName()));
			}
		}
	}
	// creates the overarching known cards panel and adds people, weapons, and rooms to it 
	private JPanel createKnownCardsPanel() {
		JPanel knownCards = new JPanel();
		knownCards.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		knownCards.setLayout(new GridLayout(3, 0));
		JPanel people = createPeoplePanel();
		knownCards.add(people);
		JPanel weapons = createWeaponsPanel();
		knownCards.add(weapons);
		JPanel rooms = createRoomsPanel();
		knownCards.add(rooms);
		return knownCards;
	}
	
	
	
	// creates the rooms panel and adds seen rooms and rooms in hand
	private JPanel createRoomsPanel() {
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridLayout(2,0));
		handRoom.setLayout(new GridLayout(3,0));
		JLabel handLabel = new JLabel("In Hand:");
		handRoom.add(handLabel);
		handRoom.add(none);
		

		seenRoom.setLayout(new GridLayout(numRooms,0));
		JLabel seenLabel = new JLabel("Seen:");
		seenRoom.add(seenLabel);
		seenRoom.add(none);
		
		
		rooms.add(handRoom);
		rooms.add(seenRoom);
		return rooms;
	}

	// creates the weapons panel and adds seen weapons and weapons in hand
	private JPanel createWeaponsPanel() {
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weaponsPanel.setLayout(new GridLayout(2, 0)); // 2 slots for in hand and seen 
		handWeapon.setLayout(new GridLayout(3, 0));
		JLabel handLabel = new JLabel("In Hand: ");
		handWeapon.add(handLabel);
		handWeapon.add(none);
		
		
		seenWeapon.setLayout(new GridLayout(numWeapons, 0));
		JLabel seenLabel = new JLabel("Seen: ");
		seenWeapon.add(seenLabel);
		seenWeapon.add(none);
		
		
		weaponsPanel.add(handWeapon);
		weaponsPanel.add(seenWeapon);
		return weaponsPanel;
	}
	
	// creates the people panel and adds seen people and people in hand
	private JPanel createPeoplePanel() {
		//panel used to display people cards
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People")); //add border
		peoplePanel.setLayout(new GridLayout(2, 0)); // 2 slots for in hand and seen 
		
		//create what the player may have in their hand
		handPerson.setLayout(new GridLayout(3, 0));
		JLabel handLabel = new JLabel("In Hand: ");
		handPerson.add(handLabel);
		handPerson.add(none);
		

		
		//create panel that shows people cards that will be seen
		seenPerson.setLayout(new GridLayout(numPeople, 0));
		JLabel seenLabel = new JLabel("Seen: ");
		seenPerson.add(seenLabel);
		seenPerson.add(none);
		
		peoplePanel.add(handPerson);
		peoplePanel.add(seenPerson);
		return peoplePanel;
	}
	
	public void addSeen(Card c , Color col) {
		//classifying each card type
		if(c.getCardType() == CardType.WEAPON) {
			//add a card to the seen list, and make the list bigger
			seenWeapon.setLayout(new GridLayout(++numWeapons, 0));
			seenWeapon.remove(none);
			JTextField card = new JTextField(c.getCardName());
			card.setBackground(col);
			seenWeapon.add(card);
		}
		else if(c.getCardType() == CardType.PERSON) {
			//add a card to the seen list, and make the list bigger
			seenPerson.setLayout(new GridLayout(++numPeople, 0));
			seenPerson.remove(none);
			JTextField card = new JTextField(c.getCardName());
			card.setBackground(col);
			seenPerson.add(card);
		}
		else if(c.getCardType() == CardType.ROOM) {
			//add a card to the seen list, and make the list bigger
			seenRoom.setLayout(new GridLayout(++numRooms, 0));
			seenRoom.remove(none);
			JTextField card = new JTextField(c.getCardName());
			card.setBackground(col);
			seenRoom.add(card);
		}
	}






	public static void main(String[] args) {
		//used to simulate being passed in the HumanPlayer's hand and adding all the cards
		HashSet<Card> cardsList = new HashSet<Card>();
		cardsList.add(new Card(CardType.WEAPON, "Starbucks Cup"));
		cardsList.add(new Card(CardType.PERSON, "Katherine"));
		cardsList.add(new Card(CardType.ROOM, "Trads"));
		cardsList.add(new Card(CardType.PERSON, "Marvin"));
		cardsList.add(new Card(CardType.ROOM, "Mines Market"));
		CardsPanel display = new CardsPanel(cardsList);
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(display); // put the panel in the frame
		frame.setSize(250, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		
		
		// test filling in the data
		//add cards that are seen, pass in the card and the color of the person who showed you
		display.addSeen(new Card(CardType.WEAPON, "Mines Market Fork"), Color.cyan);
		display.addSeen(new Card(CardType.PERSON, "PCJ"), Color.orange);
		display.addSeen(new Card(CardType.ROOM, "Rec Center"), Color.magenta);
		display.addSeen(new Card(CardType.PERSON, "Marlene"), Color.pink);
		display.addSeen(new Card(CardType.PERSON, "Adam"),Color.green);
		
		
		frame.setVisible(true);
		
		



	}
}