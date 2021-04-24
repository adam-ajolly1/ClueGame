package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	// 6 private instance variables:
	// 3 panels
	private GameControlPanel gamePanel = new GameControlPanel();
	private CardsPanel cardPanel;
	// instance of the board object
	private Board gameBoard;
	
	private static ClueGame theInstance = new ClueGame();
	
	public ClueGame() {
		super();
	}
		
	public static ClueGame getInstance() {
		return theInstance;
	}

	public void initialize() {
		// Initialize board
				gameBoard = Board.getInstance();
				gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
				gameBoard.initialize();
				
				
				// setting up and adding the bottom panel in the south
				add(gamePanel, BorderLayout.SOUTH);
				
				// setting up and adding the card panel in the east
				for (Player p: gameBoard.getPlayerList()) {
					if (p instanceof HumanPlayer) {
						cardPanel = new CardsPanel(p.getHand());
					}
				}
				add(cardPanel, BorderLayout.EAST);
				
				add(gameBoard, BorderLayout.CENTER);
				
				// setting up layout for the window 
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
				setSize(820, 880);
				setLocationRelativeTo(null);
				setVisible(true);
			
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Welcome to Clue");
		frame.setSize(500, 500);
		JOptionPane.showMessageDialog(frame, "Oh no! Blaster the Burro has been kidnapped from the Mines campus! Can you find who did it before the Computer players? ");
		ClueGame panel = new ClueGame();  // create the panel
		panel.initialize();
	}




	
}
