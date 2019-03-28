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
 * @version 1.1
 * @since 1.0
 */
public class GamePlay extends Observable {

    /**
     * Stores the value of the each phase in the game
     */
    private int phase;

    /**
     * Stores the name of the current player who is playing
     */
    private String currentPlayer;

    /**
     * Stores the value of the % of map the player controls at any particular time in the game
     */
    private float percentageMap;

    /**
     * Stores a list of continents the player controls at any particular time in the game
     */
    private ArrayList continentsControlled;

    /**
     * Stores the total number of armies that the player has to be placed in countries
     */
    private int totalArmies;

    /**
     * getter method to return the percentage of map controlled by player
     *
     * @return percentage of map controlled by player
     */
    public float getPercentageMap() {
        return percentageMap;
    }

    /**
     * setter method to set the value of percentage of map controlled by the player
     *
     * @param percentageMap decimal value of percentage of map controlled by player
     */
    public void setPercentageMap(float percentageMap) {
        this.percentageMap = percentageMap;
    }

    /**
     * getter method to get the list of continents controlled by the player
     *
     * @return list of continents controlled by the player
     */
    public ArrayList getContinentsControlled() {
        return continentsControlled;
    }

    /**
     * setter method to set the list of continents controlled by the player
     *
     * @param continentsControlled list of continents controlled by the player
     */
    public void setContinentsControlled(ArrayList continentsControlled) {
        this.continentsControlled = continentsControlled;
    }

    /**
     * getter method to return the total number of armies owned by the player
     *
     * @return total number of armies a player owns
     */
    public int getTotalArmies() {
        return totalArmies;
    }

    /**
     * setter method to assign the total number of armies owned by a player
     *
     * @param totalArmies Integer value of armies owned by a player
     */
    public void setTotalArmies(int totalArmies) {
        this.totalArmies = totalArmies;
    }

    /**
     * getter method to return the value of the phase of the game
     *
     * @return phase value
     */
    public int getPhase() {
        return phase;
    }

    /**
     * setter method to set the value of the phase
     *
     * @param phase value of phase of game
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * getter method to get the name of the current player
     *
     * @return name of the player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * setter method to set the name of the current player
     *
     * @param currentPlayer name of the player
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
     * Instance of player
     */
    private Player play;

    /**
     * Instance of RiskMap
     */
    public RiskMap map = new RiskMap();

    /**
     * Create a game play
     */
    public GamePlay() {

    }

    /**
     * Creates a game play with specified value of players and map
     *
     * @param players list of all players in the game
     * @param map     instance of RiskMap
     */
    public GamePlay(ArrayList<Player> players, RiskMap map) {

        play = new Player(players, map);
    }

    /**
     * Driver method to initiate the game phases in a round robin fashion for every player
     * @param gamePlay instance of game
     */
    public void start(GamePlay gamePlay) {
        System.out.println("\n**** Game has started ****");
        currentPlayer = null;
        Phase phaseView = new Phase();
        boolean initialGameFlag = true;
        gamePlay.deleteObserver(phaseView);
        gamePlay.addObserver(phaseView);
        for (Player player : play.players) {
            phase = 1;
            setPlayerDetailsForPhase(player);
            play.deployArmies(player, play.map);
        }
        boolean gameOver = false;
        while (!gameOver) {

            for (Player player : play.players) {
                System.out.println("\n**** New Round Begins ****");
                /*
                 * Reinforcement Phase
                 */
                if (!initialGameFlag) {
                    gamePlay.deleteObserver(phaseView);
                    gamePlay.addObserver(phaseView);

                    phase = 2;
                    CardExchange cardExchange = new CardExchange();
                    play.addObserver(cardExchange);
                    setPlayerDetailsForPhase(player);
                    play.reinforcement(player, play.map);        //send play.map for map object
                    play.deleteObserver(cardExchange);


                }
                initialGameFlag = false;
                /*
                 * Attack Phase
                 */
                gamePlay.deleteObserver(phaseView);
                gamePlay.addObserver(phaseView);

                phase = 3;

                setPlayerDetailsForPhase(player);
                play.attack(player, play.map); //after leaving behind, country should belong to winner


                /*
                 * Fortification Phase
                 */
                gamePlay.deleteObserver(phaseView);
                gamePlay.addObserver(phaseView);

                phase = 4;
                setPlayerDetailsForPhase(player);
                play.fortification(player, play.map);

            }
            System.out.println("\nContinue the game?\nYes\nNo");
            boolean continueGameFlag = false;
            while (!continueGameFlag) {
                String choice = scanner.next();
                if (choice.equals("Yes")) {
                    continueGameFlag = true;
                } else if (choice.equals("No")) {
                    continueGameFlag = true;
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
     * Refactored method- to set the player details in every phase
     * @param player instance of the current player
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
