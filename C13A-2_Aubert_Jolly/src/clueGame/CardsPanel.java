package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel {
	
	public CardsPanel() {
		setLayout(new GridLayout(0,1));
		
		JPanel knownCards = createKnownCardsPanel();
		add(knownCards);
	}
	
	private JPanel createKnownCardsPanel() {
		JPanel knownCards = new JPanel();
		knownCards.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		knownCards.setLayout(new GridLayout(0, 3));
		JPanel people = createPeoplePanel();
		knownCards.add(people);
		JPanel weapons = createWeaponsPanel();
		knownCards.add(weapons);
		JPanel rooms = createRoomsPanel();
		knownCards.add(rooms);
		return knownCards;
	}
	
	
	
	
	private JPanel createRoomsPanel() {
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0,2));
		JPanel inHand = new JPanel();
		inHand.setLayout(new GridLayout(0,4));
		JLabel handLabel = new JLabel("In Hand:");
		inHand.add(handLabel);
		
		JPanel seenCards = new JPanel();
		seenCards.setLayout(new GridLayout(0,7));
		JLabel seenLabel = new JLabel("Seen:");
		seenCards.add(seenLabel);
		
		
		
		rooms.add(inHand);
		rooms.add(seenCards);
		return rooms;
	}

	private JPanel createWeaponsPanel() {
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setLayout(new GridLayout(0, 4)); // 2 slots for in hand and seen 
		JPanel inHand = new JPanel();
		inHand.setLayout(new GridLayout(0, 7));
		JLabel handLabel = new JLabel("In Hand");
		inHand.add(handLabel);
		
		// only has titles. still need to add weapons in hand
		
		JPanel seen = new JPanel();
		seen.setLayout(new GridLayout(0, 7));
		JLabel seenLabel = new JLabel("Seen");
		seen.add(seenLabel);
		
		// only has titles. still need to add seen weapons.
		
		return weaponsPanel;
	}

	private JPanel createPeoplePanel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	






	public static void main(String[] args) {
		CardsPanel display = new CardsPanel();
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(display); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// test filling in the data
		
		frame.setVisible(true);
		
		



	}
}