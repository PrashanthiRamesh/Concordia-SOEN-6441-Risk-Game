package model;

import java.util.ArrayList;

public class Tournament {
	
	int gameNumber;
	
	boolean isDraw;
	
	Player winner;
	
	String mapName;
	

	public Tournament(int gameNumber, boolean isDraw, Player winner, String mapName) {
		super();
		this.gameNumber = gameNumber;
		this.isDraw = isDraw;
		this.winner = winner;
		this.mapName = mapName;
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	
	
}
