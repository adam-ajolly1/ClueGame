package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	// 6 private instance variables:
	// 3 panels
	private GameControlPanel gamePanel = new GameControlPanel();
	private CardsPanel cardPanel;
	// instance of the board object
	private Board gameBoard;
	
	
	public ClueGame() {
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
		ClueGame panel = new ClueGame();  // create the panel
		




	}
}
