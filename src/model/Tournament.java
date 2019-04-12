package model;

import java.util.ArrayList;

/**
 * Tournament class initiates the tournament with palyers and their armies
 *
 * @author Mutesham
 * @version 1.2
 * @since 1.2
 */

public class Tournament {

	/**
	 * Stores the gameNumber
	 */
	int gameNumber;

	/**The boolean variable holds boolean value for draw
	 *
	 */
	boolean isDraw;

	/**
	 * Winner is an object of Player class
	 */
	Player winner;

	/**
	 * The string stores the name of map being initialised
	 */
	String mapName;



	/**
	 * Initialises the constructor
	 *
	 * @param gameNumber stores the gameNumber
	 * @param isDraw is a boolean value for draw function
	 * @param winner is an object for Player class
	 * @param mapName is a String variable
	 */
	public Tournament(int gameNumber, boolean isDraw, Player winner, String mapName) {
		super();
		this.gameNumber = gameNumber;
		this.isDraw = isDraw;
		this.winner = winner;
		this.mapName = mapName;
	}

	/**
	 * Getter method to get the GameNumber
	 * @return the gameNumber
	 */
	public int getGameNumber() {
		return gameNumber;
	}

	/**
	 * Setter method used to set the gameNumber
	 * @param gameNumber is an integer value
	 */
	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}

	/**
	 * Method to check the boolean value initialised or not
	 * @return the boolean value for draw
	 */
	public boolean isDraw() {
		return isDraw;
	}

	/**
	 * Setter method to set the value of draw
	 * @param isDraw is a boolean value
	 */
	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}

	/**
	 * Getter method to get the winner
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * Setter method to set the winner
	 * @param winner is an object of Player class
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	/**
	 * Getter method to get mapName
	 * @return the name of map
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * Setter method to set the mapname
	 * @param mapName is a String variable
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	
	
}
