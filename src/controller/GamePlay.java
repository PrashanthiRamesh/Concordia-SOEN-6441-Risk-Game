package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import model.RiskMap;
import model.Player;
import view.CardExchange;
import view.Phase;


/**
 * GamePlay Class represents a game
 *
 * @author Prashanthi
 * @author Suthakhar
 * @version 1.0
 * @since 2019-02-27
 */
public class GamePlay extends Observable{

	/**
	 *
	 */
	private int phase;

	/**
	 *
	 */
	private String currentPlayer;

	/**
	 *
	 */
	private float percentageMap;

	/**
	 *
	 */
	private ArrayList continentsControlled;

	/**
	 *
	 */
	private int totalArmies;

	/**
	 *
	 * @return
	 */
	public float getPercentageMap() {
		return percentageMap;
	}

	/**
	 *
	 * @param percentageMap
	 */
	public void setPercentageMap(float percentageMap) {
		this.percentageMap = percentageMap;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList getContinentsControlled() {
		return continentsControlled;
	}

	/**
	 *
	 * @param continentsControlled
	 */
	public void setContinentsControlled(ArrayList continentsControlled) {
		this.continentsControlled = continentsControlled;
	}

	/**
	 *
	 * @return
	 */
	public int getTotalArmies() {
		return totalArmies;
	}

	/**
	 *
	 * @param totalArmies
	 */
	public void setTotalArmies(int totalArmies) {
		this.totalArmies = totalArmies;
	}

	/**
	 *
	 * @return
	 */
	public int getPhase() {
		return phase;
	}

	/**
	 *
	 * @param phase
	 */
	public void setPhase(int phase) {
		this.phase = phase;
	}

	/**
	 *
	 * @return
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 *
	 * @param currentPlayer
	 */
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

	/**
	 *
	 */
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
    public void start(GamePlay gamePlay) {
        System.out.println("\n**** Game has started ****");
		currentPlayer=null;
        boolean initialGameFlag =true;
		for (Player player : play.players) {
			phase=1;
			setPlayerDetailsForPhase(player);
			play.deployArmies(player);
		}
        boolean gameOver = false;
		Phase phaseView=new Phase();
        while (!gameOver) {
            System.out.println("\n**** New Round Begins ****");
            /*
             * Reinforcement Phase
             */
            if(!initialGameFlag){
            	gamePlay.deleteObserver(phaseView);
				gamePlay.addObserver(phaseView);
				for (Player player : play.players) {
					phase=2;
					CardExchange cardExchange=new CardExchange();
					play.addObserver(cardExchange);
					setPlayerDetailsForPhase(player);
					play.reinforcement(player);		//send play.map for map object
					play.deleteObserver(cardExchange);

				}
			}
            initialGameFlag =false;
			/*
			 * Attack Phase
			 */
			gamePlay.deleteObserver(phaseView);
			gamePlay.addObserver(phaseView);
			for (Player player : play.players) {
				phase=3;

				setPlayerDetailsForPhase(player);
				play.attack(player, play.map); //after leaving behind, country should belong to winner


			}
			/*
			 * Fortification Phase
			 */
			gamePlay.deleteObserver(phaseView);
			gamePlay.addObserver(phaseView);
            for (Player player : play.players) {
            	phase=4;
				setPlayerDetailsForPhase(player);
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
                    gameOver = true;
                } else {
                    System.out.println("Invalid choice! Enter either Yes or No: ");
                    scanner.next();
                }
            }
        }
        System.out.println("\n***Game Over***\n");
    }

	/**
	 * Refactored method 1
	 * @param player
	 */
	private void setPlayerDetailsForPhase(Player player) {
		currentPlayer = player.getPlayerName();
		percentageMap = (float) play.map.noOfCountriesPlayerOwns(player.getPlayerName()) / play.map.adjCountries.size();
		totalArmies = player.getArmies();
		continentsControlled = play.map.continentsControlledByPlayer(player.getCountries());
		setChanged();
		notifyObservers(this);
	}


}
