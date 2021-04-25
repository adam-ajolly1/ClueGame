package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel implements ActionListener {
	JTextField outName = new JTextField(10);
	JTextField outRoll = new JTextField(5);
	static JTextField outGuess = new JTextField(20);
	static JTextField outResult  = new JTextField(20);
	public static boolean hasFinished = true;
	public static int currPlayerNum = 0;
	private Player currPlayer;
	public static Solution accusationFlag = null;
	private JComboBox<String> weaponDropDown = new JComboBox();


	public JComboBox<String> getWeaponDropDown() {
		return weaponDropDown;
	}
	private JComboBox<String> personDropDown = new JComboBox();
	private JComboBox<String> roomsDropDown = new JComboBox();


	public JComboBox<String> getPersonDropDown() {
		return personDropDown;
	}
	public Player getCurrPlayer() {
		return currPlayer;
	}

	public void setCurrPlayer(Player currPlayer) {
		this.currPlayer = currPlayer;
	}
	public void setAccusationFlag(Solution accusationFlag) {
		this.accusationFlag = accusationFlag;
	}
	public Solution getAccusationFlag() {
		return accusationFlag;
	}

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
		makeAccusation.addActionListener(this);
		JButton next = new JButton("NEXT!");
		next.addActionListener(this);
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
	private void setTurn(Player p, int roll) {
		outRoll.setText(String.valueOf(roll));
		outName.setText(p.getName());
		outName.setBackground(p.getColor());
	}
	public static void setGuess(String guess) {
		outGuess.setText(guess);
	}
	public static void setGuessResult(String result) {
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
		//panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		frame.setVisible(true);




	}

	@Override
	//if next is clicked
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("NEXT!")) {
			this.setGuess(" ");
			this.setGuessResult(" ");
			Board.getInstance().repaint();
			// TODO Auto-generated method stub
			//if a player has not finished their turn
			if (!hasFinished) {
				System.out.println("You need to finish your turn first.");
			}
			else {
				//get a random number for the roll and set the current player. Set the turn and roll
				Random rand = new Random() ;
				currPlayer = Board.getInstance().getPlayerList().get(++currPlayerNum % 6);
				int roll = rand.nextInt(6) + 1;
				setTurn(currPlayer, roll);
				Board.getInstance().calcTargets(Board.getInstance().grid[currPlayer.row][currPlayer.column], roll);
				HashSet<BoardCell> targets = Board.getInstance().getTargets();


				if (currPlayer instanceof HumanPlayer){


					GameControlPanel.hasFinished = false;
					for (BoardCell c: targets) {
						c.setTarget(true);
					}

					Board.getInstance().repaint();

				}
				if(currPlayer instanceof ComputerPlayer) {
					// choose a random target
					// implement handle and create suggestion
					if(accusationFlag != null) {
						if(!(currPlayer.getHand().contains(accusationFlag.getRoom()) || currPlayer.getHand().contains(accusationFlag.getPerson()) || currPlayer.getHand().contains(accusationFlag.getWeapon()))) {
							Board.getInstance().checkAccusation(accusationFlag.getRoom(), accusationFlag.getPerson(), accusationFlag.getWeapon());
							computerWinsScreen(accusationFlag, currPlayer);
						}
					}
					Random rand2 = new Random();
					int randTarget = rand2.nextInt(targets.size());
					Board.getInstance().grid[currPlayer.getRow()][currPlayer.getColumn()].setOccupied(false);
					int count = 0;
					BoardCell finalTarget = null;
					for (BoardCell c: targets) {
						// gets the BoardCell of the random target
						// need to say if its a room, go to that room and not a random target
						if (c.getIsRoom() && (currPlayer.getRow() != c.getRow() && currPlayer.getColumn() != c.getCol())) {
							finalTarget = c;
							break;
						}
						if (count++ == randTarget) {
							finalTarget = c;

							break;
						}
					}
					currPlayer.setLocation(finalTarget.getRow(), finalTarget.getCol());
					finalTarget.setOccupied(true);
					BoardCell newLocation = Board.getInstance().grid[currPlayer.getRow()][currPlayer.getColumn()];
					if(newLocation.getIsRoom()) {

						Solution suggestion = currPlayer.createSuggestion(Board.getInstance().roomToCard(newLocation.getCorrespondingRoom()));

						// moves the player that was suggested into that room
						Card playerToMove = suggestion.getPerson();
						Player playerMove = null;

						for (Player p: Board.getInstance().getPlayerList()) {
							if (playerToMove.getCardName().contentEquals(p.getName()))
							{
								playerMove = p;
							}
						}
						Board.getInstance().grid[playerMove.getRow()][playerMove.getColumn()].setOccupied(false);
						playerMove.setLocation(currPlayer.getRow(), currPlayer.getColumn());
						Board.getInstance().grid[playerMove.getRow()][playerMove.getColumn()].setOccupied(true);
						GameControlPanel.setGuess(suggestion.toString());
						Card guessResult = Board.getInstance().handleSuggestion(suggestion, currPlayer);
						if(guessResult == null) {
							GameControlPanel.setGuessResult("None");
							accusationFlag = suggestion;
						}

					}



					// repaints the players in the correct location
					ClueGame.getInstance().repaint();
					revalidate();



				}
			}
		}
		if(e.getActionCommand().equals("Make Accusation")) {
			System.out.println("Tried to make an accusation");
			if(!(currPlayer instanceof HumanPlayer)) {
				System.out.println("It is not your turn to make an accusation.");
			}
			else {
				
				Solution accusation = paintAccusationBox(currPlayer.getLocation().getCorrespondingRoom());
				}
				
			}
		if (e.getActionCommand().equals("Accuse")) {
			//set the current player
			HumanPlayer currPlayer = (HumanPlayer) Board.getInstance().getPlayerList().get(GameControlPanel.currPlayerNum % 6);
			//get the suggestion from the drop down menus
			currPlayer.setPersonSuggestion((String) this.personDropDown.getSelectedItem());
			currPlayer.setWeaponSuggestion((String) this.weaponDropDown.getSelectedItem());
			currPlayer.setRoomSuggestion((String) this.roomsDropDown.getSelectedItem());
			
			boolean personMatches = Board.getInstance().getTheAnswer().getPerson().getCardName().equals(currPlayer.getPersonSuggestion());
			boolean weaponMatches = Board.getInstance().getTheAnswer().getWeapon().getCardName().equals(currPlayer.getWeaponSuggestion());
			boolean roomMatches = Board.getInstance().getTheAnswer().getRoom().getCardName().substring(1).equals(currPlayer.getRoomSuggestion());
			
			
			System.out.println(Board.getInstance().getTheAnswer().getRoom().getCardName().substring(1));
			System.out.println(currPlayer.getRoomSuggestion());
			System.out.println("answers " + personMatches + roomMatches +  weaponMatches);
			
			if(personMatches && roomMatches && weaponMatches) {
				playerWinsScreen(Board.getInstance().getTheAnswer(), currPlayer);
			}
			else {
				playerLosesScreen(Board.getInstance().getTheAnswer(), currPlayer);
			}
		}
	}
	

	public void computerWinsScreen(Solution accusation, Player winner) {
		JFrame frame = new JFrame("You lost! :(");
		frame.setSize(500, 500);
		String winningSentence = winner.getName() + " won the game by guessing the correct solution: " + accusation;
		JOptionPane.showMessageDialog(frame, winningSentence);
	}
	public void playerWinsScreen(Solution accusation, Player winner) {
		JFrame frame = new JFrame("You won! :)");
		frame.setSize(500, 500);
		String winningSentence = "You won the game by guessing the correct solution: " + accusation;
		JOptionPane.showMessageDialog(frame, winningSentence);
	}
	public void playerLosesScreen(Solution accusation, Player winner) {
		JFrame frame = new JFrame("You lose! :(");
		frame.setSize(500, 500);
		String losingSentence = "You lost the game by guessing the incorrect solution. The Solution was: " + accusation;
		JOptionPane.showMessageDialog(frame, losingSentence);
		
	}
	
	JFrame frame = new JFrame("Make an Accusation");
	public Solution paintAccusationBox(Room r) {
		Solution ans = null;
		frame.setSize(500, 350);
		frame.setLayout(new GridLayout(4,2));
		JTextField roomText = new JTextField("Current room");
		frame.add(roomText);
		
		String[] rooms = new String[Board.getInstance().getRoomMap().size()];
		int index = 0;
		for(Entry<Room, Character> entry : Board.getInstance().getRoomMap().entrySet()) {
			rooms[index] = entry.getKey().getName();
			index ++;
		}
		roomsDropDown = new JComboBox<String>(rooms);
		frame.add(roomsDropDown);
		
		JTextField personText = new JTextField("Person");
		frame.add(personText);
		
		String[] people = new String[Board.getInstance().getPlayerList().size()];
		index = 0;
		for(Player x: Board.getInstance().getPlayerList()) {
			people[index] = x.getName();
			index ++;
		}
		personDropDown = new JComboBox<String>(people);
		frame.add(personDropDown);
		
		JTextField weaponText = new JTextField("Weapon");
		frame.add(weaponText);
		String[] weapons = new String[6];
		int weaponIndex = 0;
		for(Card x: Board.getInstance().getDeck()) {
			if(x.getCardType() == CardType.WEAPON) {
				weapons[weaponIndex] = x.getCardName();
				weaponIndex ++;
			}
		}
		weaponDropDown = new JComboBox<String>(weapons);
		frame.add(weaponDropDown);
		
		JButton submit = new JButton("Accuse");
		submit.addActionListener(this);
		frame.add(submit);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		frame.add(cancel);
		
		
		
		//JComboBox<String> cb = new JComboBox<String>(choices);
		frame.setVisible(true);
		return ans;
	}


}