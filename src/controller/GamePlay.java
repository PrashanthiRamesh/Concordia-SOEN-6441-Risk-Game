package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import model.RiskMap;
import model.Player;
import view.CardExchange;


/**
 * GamePlay Class represents a game
 *
 * @author Prashanthi
 * @author Suthakhar
 * @version 1.0
 * @since 2019-02-27
 */
public class GamePlay extends Observable{

	private int phase;
	
	private String currentPlayer;
	
	private float percentageMap;
	
	private ArrayList continentsControlled;
	
	private int totalArmies;
	
	
	public float getPercentageMap() {
		return percentageMap;
	}

	public void setPercentageMap(float percentageMap) {
		this.percentageMap = percentageMap;
	}

	public ArrayList getContinentsControlled() {
		return continentsControlled;
	}

	public void setContinentsControlled(ArrayList continentsControlled) {
		this.continentsControlled = continentsControlled;
	}

	public int getTotalArmies() {
		return totalArmies;
	}

	public void setTotalArmies(int totalArmies) {
		this.totalArmies = totalArmies;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Array list that holds instances of all players
     */
    private ArrayList<Player> players;
    
    private Player play;

    /**
     * Instance of RiskMap
     */
    public RiskMap map=new RiskMap();

    /**
     * Create a game play
     */
    public GamePlay() {

    }

    /**
     * Creates a game play with specified value of players and map
     * @param players list of all players in the game
     * @param map instance of RiskMap
     */
    public GamePlay(ArrayList<Player> players, RiskMap map) {
       
    	play=new Player(players,map);
    }

    /**
     * Driver method to initiate the game phases in a round robin fashion for every player
     */
    public void start() {
        System.out.println("\n**** Game has started ****");
		currentPlayer=null;
        boolean initial_game_flag=true;
		for (Player player : play.players) {
			phase=1;
			currentPlayer =player.getPlayerName();
			percentageMap =(float)play.map.noOfCountriesPlayerOwns(player.getPlayerName())/play.map.adjCountries.size();
			totalArmies =player.getArmies();
			continentsControlled=play.map.continentsControlledByPlayer(player.getCountries());
			setChanged();
			notifyObservers(this);
			play.initialDeployment(player);
		}
        boolean game_over = false;
        while (!game_over) {
            System.out.println("\n**** New Round Begins ****");
            /*
             * Reinforcement Phase
             */
            if(!initial_game_flag){

				for (Player player : play.players) {
					phase=2;
					CardExchange cardExchange=new CardExchange();
					play.addObserver(cardExchange);
					currentPlayer =player.getPlayerName();
					percentageMap =(float)play.map.noOfCountriesPlayerOwns(player.getPlayerName())/play.map.adjCountries.size();
					totalArmies =player.getArmies();
					continentsControlled=play.map.continentsControlledByPlayer(player.getCountries());
					setChanged();
					notifyObservers(this);
					play.reinforcement(player);
					play.deleteObserver(cardExchange);

				}
			}
            initial_game_flag=false;
			/*
			 * Attack Phase
			 */
			for (Player player : play.players) {
				phase=3;
				currentPlayer =player.getPlayerName();
				percentageMap =(float)play.map.noOfCountriesPlayerOwns(player.getPlayerName())/play.map.adjCountries.size();
				totalArmies =player.getArmies();
				continentsControlled=play.map.continentsControlledByPlayer(player.getCountries());
				setChanged();
				notifyObservers(this);
				play.attack(player);
			}
			/*
			 * Fortification Phase
			 */
            for (Player player : play.players) {
            	phase=4;
            	currentPlayer =player.getPlayerName();
				percentageMap =(float)play.map.noOfCountriesPlayerOwns(player.getPlayerName())/play.map.adjCountries.size();
				totalArmies =player.getArmies();
				continentsControlled=play.map.continentsControlledByPlayer(player.getCountries());
            	setChanged();
            	notifyObservers(this);
                play.fortification(player);
            }
            System.out.println("\nContinue the game?\nYes\nNo");
            boolean continue_game_flag = false;
            while (!continue_game_flag) {
                String choice = scanner.next();
                if (choice.equals("Yes")) {
                    continue_game_flag = true;
                } else if (choice.equals("No")) {
                    continue_game_flag = true;
                    game_over = true;
                } else {
                    System.out.println("Invalid choice! Enter either Yes or No: ");
                    scanner.next();
                }
            }
        }
        System.out.println("\n***Game Over***\n");
    }



}
