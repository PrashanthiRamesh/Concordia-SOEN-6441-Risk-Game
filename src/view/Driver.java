package view;

import controller.GamePlay;
import model.Card;
import model.RiskMap;
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
public class Driver {

	/**
	 * Holds all the players in the game
	 */
	private ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * A RiskMap instance
	 */
	public static RiskMap map = null;

	/**
	 * A Scanner instance to read and parse various primitive values.
	 */
	private static Scanner scanner = new Scanner(System.in);

	public static GamePlay gamePlay;
	

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * Initiates the game
	 * 
	 * @param args
	 *            command line arguments
	 * @throws Exception when exception occurs
	 */
	public static void main(String[] args) throws Exception {
		(new Driver()).startGame();
	}

	/**
	 * Implements the game phases according to the Risk rules
	 * @throws Exception 
	 */
	private void startGame() throws Exception {
		System.out.println("\n\n#### Team 23 Conquest Game ####\n\n1.Normal Mode\n2.Tournament Mode\nEnter:");
		int ch = scanner.nextInt();
		if (ch == 1) {
			normalMode();
		}else {
			(new TournamentDriver()).startGame();
		}
	}
	
	private void normalMode() throws Exception {
		try {
			boolean mapFlag = false;
			while (!mapFlag) {
				map = new RiskMap();
				System.out.println("Select 1 if u want to Load the Map from Last Saved Map , 2 to continue a new Game");
				int ch = scanner.nextInt();
				if (ch == 1) {
					gamePlay = new GamePlay(this.players, map);
					gamePlay.retrieveMap();
					gamePlay.addObserver(new PlayerWorldDomination());
					gamePlay.start(gamePlay);

				} else {
					getMapInput();
					boolean editMapFlag = false;
					while (!editMapFlag) {
						if (map.areContinentsConnected() && map.isMapConnected()) {
							System.out.println("\nContinents and its countries: " + map.getContinentsWithCountries());
							System.out.println("\nCountries and its neighbours: " + map.getAdjCountries());
							System.out.println("\n****Map that is loaded is connected and valid!****\n");
							System.out.println("Do you want to edit the map?\nYes\nNo");
							boolean editMapChoiceFlag = false;
							while (!editMapChoiceFlag) {
								String editMapChoice = scanner.next();
								if (editMapChoice.equals("Yes")) {
									editMapChoiceFlag = true;
									map.editMap();
									map.writeTheMapToTheTextFile();
								} else if (editMapChoice.equals("No")) {
									editMapChoiceFlag = true;
									editMapFlag = true;
									this.getPlayersInput();

									/*
									 * Observer Pattern 1. Phase View to display name of the game phase, current
									 * player's name, information about actions that are taking place in the phase
									 * which should declared at the beginning of every phase 2. Player World
									 * Domination View to display the % of map controlled by every player,
									 * continents controlled by every player, total number of armies owned by every
									 * player
									 */
									gamePlay = new GamePlay(this.players, map);
									gamePlay.addObserver(new PlayerWorldDomination());
									gamePlay.start(gamePlay);
								} else {
									System.out.println("Invalid! Enter either Yes or No: ");
								}
							}
							mapFlag = true;
						} else {
							editMapFlag = true;
							mapFlag = false;
							System.out.println("\n**Map Overview**\n\nContinents and its countries and its neighbours: "
									+ map.getContinentsWithCountriesAndNeighbours());
							System.out.println("\nContinents and its countries: " + map.getContinentsWithCountries());
							System.out.println("\nCountries and its neighbours: " + map.getAdjCountries());
							System.out.println(
									"\n**** Map is not connected or one of the Continents is not connected and is invalid! Please start again! ****\n");
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Unhandled Exception: " + ex);
			System.out.println("Start Again");
			startGame();
		}

	}

	/**
	 * Game starts by user creation of a map file.
	 * 
	 * @throws IOException
	 *             on user input
	 */
	private static void getMapInput() throws IOException {
		System.out.println("Get Ready to play Conquest!\n");

		System.out.println("1.Create RiskMap From Console \n2.Load from a File");

		boolean choiceFlag = false;
		while (!choiceFlag) {
			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				choiceFlag = true;
				switch (choice) {
				case 1: {
					map.populateMap();
					map.writeTheMapToTheTextFile();
					break;
				}
				case 2: {
					boolean fileFlag = false;
					System.out.println("Enter the RiskMap File Name [eg: input.txt]: ");
					while (!fileFlag) {
						String inputMapFile = scanner.next();
						File mapFile = new File(inputMapFile.trim());
						if (mapFile.exists()) {
							fileFlag = true;
							map.loadMap(inputMapFile.trim());
							map.writeTheMapToTheTextFile();
							System.out.println("Risk Map Loaded!");
						
						} else {
							System.out.println("File does not exist! Enter again [eg: input.txt]: ");
						}
					}
					break;
				}
				default: {
					System.out.println("Invalid choice! Enter either 1 or 2:");
					choiceFlag = false;
				}
				}
			} else {
				System.out.println("Invalid characters! Enter either 1 or 2: ");
				scanner.next();
			}
		}
	}

	/**
	 * User chooses the number of players, then all countries are randomly assigned
	 * to players.
	 */
	private void getPlayersInput() {
		boolean playersFlag = false;
		System.out.println("Enter the number of players [2 to 6]: ");
		while (!playersFlag) {
			if (scanner.hasNextInt()) {
				playersFlag = true;
				int noOfPlayers = scanner.nextInt();
				if (noOfPlayers >= 2 && noOfPlayers <= 6) {
					this.players = new ArrayList<>(noOfPlayers);
					ArrayList<String> playerCountries = new ArrayList<>();

					for (int i = 0; i < noOfPlayers; i++) {
						ArrayList<Card> cards = new ArrayList<>();
						System.out.println("Enter player " + (i + 1) + " name: ");
						String playerName = scanner.next();
						Player newPlayer=(new Player(playerName, 0, playerCountries, cards, 0, 0, 0));
						// TODO give choice of strategy and let the user choose one
						// save that in player instance . setPlayerStrategy()
						System.out.println("#### Choose the player's strategy for the game ####\n1. Human"
								+ "\n2. Aggressive\n3. Benevolent\n4. Random\n5. Cheater\n");
						boolean choiceFlag = false;
						while (!choiceFlag) {
							if (scanner.hasNextInt()) {
								int playerStrategy = scanner.nextInt();
								choiceFlag = true;
								switch (playerStrategy) {
									case 1:
										newPlayer.setPlayerStrategy(new Human());
										newPlayer.setHuman(true);
										newPlayer.setPlayerStrategyCharacter("h");
										break;
									case 2:
										newPlayer.setPlayerStrategy(new Aggressive());
										newPlayer.setHuman(false);
										newPlayer.setPlayerStrategyCharacter("a");
										break;
									case 3:
										newPlayer.setPlayerStrategy(new Benevolent());
										newPlayer.setHuman(false);
										newPlayer.setPlayerStrategyCharacter("b");
										break;
									case 4:
										newPlayer.setPlayerStrategy(new Random());
										newPlayer.setHuman(false);
										newPlayer.setPlayerStrategyCharacter("r");
										break;
									case 5:
										newPlayer.setPlayerStrategy(new Cheater());
										newPlayer.setHuman(false);
										newPlayer.setPlayerStrategyCharacter("c");
										break;
									default:
										System.out.println("Invalid choice! Enter a value from 1-5:");
										choiceFlag = false;									
								}

							} else {
								System.out.println("Invalid characters! Enter one number from 1-5: ");
								scanner.next();
							}
						}
						this.players.add(newPlayer);
					}
				} else {
					System.out.println("Invalid no of players! Enter again [2 to 6]:");
					playersFlag = false;
				}

			} else {
				System.out.println("Invalid characters! Enter again [2 to 6]:");
				scanner.next();
			}
		}
		System.out.println("\n** Randomly Assigning Countries to Players **\n");
		map.assignPlayersToCountries(this.players, this.players.size());
		System.out.println(map.getCountries());

		System.out.println("\n** Assigning Initial Armies to Players based on the number of players in the game **\n");
		players = (new Player()).setInitialArmies(this.players, map.getCountries());
	}

}
