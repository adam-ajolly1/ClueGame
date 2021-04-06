package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	JTextField outName = new JTextField(10);
	JTextField outRoll = new JTextField(5);
	JTextField outGuess = new JTextField(20);
	JTextField outResult  = new JTextField(20);
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		// create the game control panel as well as creates the top and bottom panels
		setLayout(new GridLayout(2,0));
		JPanel first = createTopPanel();
		add(first, BorderLayout.NORTH);
		JPanel second = createBottomPanel();
		add(second, BorderLayout.SOUTH);
	}
	
	// creates the top panel, with the name of the player whose turn it is and the roll #
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		// creates left panel for the name of the turn and sets it
		JPanel left = new JPanel();
		JLabel name = new JLabel("Whose turn");
		left.add(name, BorderLayout.NORTH);
		left.add(outName, BorderLayout.SOUTH);
		// creates right panel for the roll # and sets it
		JPanel right = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		right.add(rollLabel, BorderLayout.WEST);
		right.add(outRoll, BorderLayout.EAST);
		JButton makeAccusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		topPanel.add(left);
		topPanel.add(right);
		topPanel.add(makeAccusation);
		topPanel.add(next);
		
		return topPanel;
	}
	
	//create the bottom, 0x2 panel
	private JPanel createBottomPanel() {
		//make new panel
		JPanel bottomPanel = new JPanel();
		//format it to 0x2
		bottomPanel.setLayout(new GridLayout(0, 2));
		//make a left side to output if there is a guess
		JPanel left = new JPanel(new GridLayout(0, 1));
		//add border
		left.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		left.add(outGuess);
		
		//make right half, side with guess result
		JPanel right = new JPanel(new GridLayout(0,2));
		right.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		right.add(outResult);
		
		//add both of them to the bottom panel and return
		bottomPanel.add(left);
		bottomPanel.add(right);
		return bottomPanel;
	}
	//help change the displayed text
	private void setTurn(ComputerPlayer p, int roll) {
		outRoll.setText(String.valueOf(roll));
		outName.setText(p.getName());
		outName.setBackground(p.getColor());
	}
	private void setGuess(String guess) {
		outGuess.setText(guess);
	}
	private void setGuessResult(String result) {
		outResult.setText(result);
	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
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