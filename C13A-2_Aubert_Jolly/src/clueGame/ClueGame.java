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
		// Board is singleton, get the only instance
		gameBoard = Board.getInstance();
		// set the file names to use my config files
		gameBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		gameBoard.initialize();
		
		// set up three panels and pass in board object to board panel
		
		
		add(gamePanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setSize(1000, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
	}



	public static void main(String[] args) {
		ClueGame panel = new ClueGame();  // create the panel
		//JFrame frame = new JFrame();  // create the frame 
		//frame.setContentPane(panel); // put the panel in the frame
		//frame.setSize(750, 180);  // size the frame
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// test filling in the data

		//frame.setVisible(true);




	}
}
