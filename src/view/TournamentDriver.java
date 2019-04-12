package view;

import controller.GamePlay;
import controller.TournamentGamePlay;
import model.Card;
import model.RiskMap;
import model.Tournament;
import player.Aggressive;
import player.Benevolent;
import player.Cheater;
import player.Human;
import player.Random;
import model.Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver Class to initiate the game
 *
 * @author Prashanthi
 * @version 1.2
 * @since 1.0
 */
public class TournamentDriver {

	/**
	 * Holds all the players in the game
	 */
	private ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * A RiskMap instance
	 */
	public RiskMap map = null;
	
	/**
	 * Stores tournament results to display finally
	 */
	ArrayList<Tournament> tournaments;

	/**
	 * A Scanner instance to read and parse various primitive values.
	 */
	private static Scanner scanner = new Scanner(System.in);

	public static TournamentGamePlay gamePlay;

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void startGame() throws Exception {
		System.out.println("Enter number of maps[1-5]: ");
		int M = scanner.nextInt();
		ArrayList<String> mapFiles = new ArrayList<String>();
		for (int i = 0; i < M; i++) {
			System.out.println("Enter map file " + (i + 1) + " name with extension:");
			String mapFilename = scanner.next();
			mapFiles.add(mapFilename);
		}
		System.out.println("Enter number of computer player strategies[1-4]: ");
		int P = scanner.nextInt();
		System.out.println("\n1.Aggressive\n2.Benevolent\n3.Random\n4.Cheater\nChoose from the list:");
		ArrayList<Integer> stategyNumbers = new ArrayList<Integer>();
		for (int i = 0; i < P; i++) {
			System.out.println("Enter strategy number" + (i + 1) + " :");
			int strategyNumber = scanner.nextInt();
			stategyNumbers.add(strategyNumber);
		}
		System.out.println("Enter number of games to be played in each map[1-5]:");
		int G = scanner.nextInt();
		System.out.println("Enter number of turns for each player in a game[10-50]:");
		int D = scanner.nextInt();

		System.out.println("\n#### Tournament Begins ####\n");
		tournaments=new ArrayList<Tournament>();
		// maps
		for (int m = 0; m < M; m++) {
			// parse map from RiskMap
			this.map = new RiskMap();
			this.map.loadMap(mapFiles.get(m).trim());
			this.map.writeTheMapToTheTextFile();
			System.out.println("Risk Map Loaded!" + this.map.getContinentsWithCountriesAndNeighbours());
			// games
			for (int g = 0; g < G; g++) {
				Tournament currentTournament=new Tournament((g+1),false,null,mapFiles.get(m));
				System.out.println("\n**Game " + (g + 1) + " in map= " + mapFiles.get(m) + " **\n");
				// create players
				ArrayList<String> playerCountries = new ArrayList<>();
				ArrayList<Card> cards = new ArrayList<>();
				for (int i = 0; i < P; i++) {
					String playerStrategyName = (new Player()).getStrategyName(stategyNumbers.get(i));
					Player newPlayer = (new Player(playerStrategyName, 0, playerCountries, cards, 0, 0, 0));
					// TODO give choice of strategy and let the user choose one
					// save that in player instance . setPlayerStrategy()

					switch (stategyNumbers.get(i)) {

					case 1:
						newPlayer.setPlayerStrategy(new Aggressive());
						newPlayer.setHuman(false);
						newPlayer.setPlayerStrategyCharacter("a");
						break;
					case 2:
						newPlayer.setPlayerStrategy(new Benevolent());
						newPlayer.setHuman(false);
						newPlayer.setPlayerStrategyCharacter("b");
						break;
					case 3:
						newPlayer.setPlayerStrategy(new Random());
						newPlayer.setHuman(false);
						newPlayer.setPlayerStrategyCharacter("r");
						break;
					case 4:
						newPlayer.setPlayerStrategy(new Cheater());
						newPlayer.setHuman(false);
						newPlayer.setPlayerStrategyCharacter("c");
						break;

					}

					this.players.add(newPlayer);
				}
				System.out.println("\n** Randomly Assigning Countries to Players **\n");
				this.map.assignPlayersToCountries(this.players, this.players.size());
				System.out.println(this.map.getCountries());

				System.out.println(
						"\n** Assigning Initial Armies to Players based on the number of players in the game **\n");
				this.players = (new Player()).setInitialArmies(this.players, this.map.getCountries());
				gamePlay = new TournamentGamePlay(this.players, this.map);
				gamePlay.addObserver(new TournamentPlayerWorldDomination());
				gamePlay.start(gamePlay, D);
				//from gamePlay instance check which player won / draw and save it in some variable
				if(gamePlay.isDraw()) {
					currentTournament.setDraw(true);
				}else {
					currentTournament.setDraw(false);
					currentTournament.setWinner(gamePlay.getWinner());
				}
				tournaments.add(currentTournament);
			}

		}
		
		System.out.println("\n\n#### Tournament Over ####\nResults\n");
		for(Tournament tournament:tournaments) {
			System.out.println("\n\nMap: "+tournament.getMapName());
			System.out.println("Game Number: "+tournament.getGameNumber());
			if(tournament.isDraw()) {
				System.out.println("Result of Game: Draw");
			}else {
				System.out.println("Winner of Game: "+tournament.getWinner().getPlayerName());
			}
		}
	}

}
