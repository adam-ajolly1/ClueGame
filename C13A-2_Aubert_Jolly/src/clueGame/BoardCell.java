package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;

import experiment.TestBoardCell;


public class BoardCell {
	// private instance variables for BoardCell
	private int row;
	private int col;
	private HashSet<BoardCell> adjacencyList = new HashSet<BoardCell>();
	private char initial;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isUnused;
	private char secretPassage = 'a';
	private DoorDirection doordirection = DoorDirection.NONE;
	private boolean doorWay;
	private Room correspondingRoom = null;
	boolean isOccupied;
	boolean isRoom = false;
	boolean isTarget = false;

	public boolean isTarget() {
		return isTarget;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}

	// BoardCell default constructor
	public BoardCell() {
		row = 0;
		col = 0;
	}

	// BoardCell parameterized constructor
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}


	// adds a cell to adjacency list of another cell 
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}
	//to string method used for debugging
	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}



	//GETTERS
	public boolean isUnused() {
		return isUnused;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public char getInitial() {
		return initial;
	}
	public boolean isLabel() {
		return roomLabel;
	}
	public DoorDirection getDoordirection() {
		return doordirection;
	}
	public boolean isDoorway() {
		return doorWay;
	}
	public DoorDirection getDoorDirection() {
		return doordirection;
	}
	public boolean isRoomCenter() {
		return roomCenter;
	}
	public char getSecretPassage() {
		return secretPassage;
	}
	public HashSet<BoardCell> getAdjList() {
		return adjacencyList;
	}
	public boolean getIsOccupied() {
		return isOccupied;
	}
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean getIsRoom() {
		return isRoom;
	}
	public Room getCorrespondingRoom() {
		return correspondingRoom;
	}

	//SETTERS
	public void setCorrespondingRoom(Room correspondingRoom) {
		this.correspondingRoom = correspondingRoom;
	}
	public void setUnused(boolean isUnused) {
		this.isUnused = isUnused;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public void setDoorway(boolean doorway) {
		doorWay = doorway;
	}
	public void setInitial(char initial) {
		this.initial = initial;
	}
	public boolean isRoomLabel() {
		return roomLabel;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	public void setDoordirection(DoorDirection doordirection) {
		this.doordirection = doordirection;
	}
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void draw(int width, int height, int offset, Graphics g) {
		if (this.isRoom && !this.isUnused) {
			g.setColor(new Color(230, 176, 170));
		}
		else if (this.isUnused) {
			g.setColor(new Color(46, 134, 193));
		} 
		else {
			g.setColor(new Color(249, 231, 159 ));
		}
		if (this.isTarget) {
			g.setColor(new Color(125, 206, 160));
		}
		g.fillRect(this.col * width + offset, this.row * height + offset, width, height);
		if (!this.isRoom) {
			g.setColor(Color.BLACK);
			g.drawRect(this.col * width + offset, this.row * height + offset, width, height);
		}
		

		if (this.isDoorway()) {
			g.setColor(new Color(125, 206, 160));
			switch(this.doordirection) {
			case UP:
				g.fillRect(this.col * width + offset, this.row*height + offset, width, 5);
				break;


			case DOWN:
				g.fillRect(this.col*width + offset, this.row*height + height - 5 + offset, width, 5);
				break;

			case LEFT:
				g.fillRect(this.col * width + offset, this.row*height + offset, 5, height);
				break;
			case RIGHT:
				g.fillRect(this.col * width + width -5 + offset, this.row*height + offset, 5, height);
				break;
			}
		}


	}

	public void drawRoomLabel(int width, int height, int offset, Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(this.getCorrespondingRoom().getName(), this.col*width + offset, this.row*height+height/2 + offset);
		
	}
}
