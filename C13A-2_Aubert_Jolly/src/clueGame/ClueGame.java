package clueGame;

import java.awt.Color;

import javax.swing.JFrame;

public class ClueGame {
	
	
	
	
	public static void main(String[] args) {
		ClueGame panel = new ClueGame();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// test filling in the data
		
		//change displayed names and guess status
		panel.setTurn(new ComputerPlayer( "Miss Scarlet", 0, 0, Color.RED), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		frame.setVisible(true);
		



	}
}
