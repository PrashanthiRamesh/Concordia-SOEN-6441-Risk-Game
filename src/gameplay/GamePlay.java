package gameplay;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Scanner;

import country.Country;
import map.RiskMap;
import player.Player;


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
	
	private String current_player;
	
	private float percentage_map;
	
	private double continents_controlled;
	
	private int total_armies;
	
	
	public float getPercentage_map() {
		return percentage_map;
	}

	public void setPercentage_map(float percentage_map) {
		this.percentage_map = percentage_map;
	}

	public double getContinents_controlled() {
		return continents_controlled;
	}

	public void setContinents_controlled(double continents_controlled) {
		this.continents_controlled = continents_controlled;
	}

	public int getTotal_armies() {
		return total_armies;
	}

	public void setTotal_armies(int total_armies) {
		this.total_armies = total_armies;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public String getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(String current_player) {
		this.current_player = current_player;
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
    public GamePlay()
    { 
    	
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
    	
    	
        System.out.println("\n**** Game has started ****\n");
        boolean game_over = false;
        while (!game_over) {
        	
        	
            System.out.println("** New Round Begins **");
            
            for (Player player : play.players) {
            	phase=1;
            	current_player=player.getPlayer_name();
            
            	percentage_map=play.map.No_of_countries_player_owns(player.getPlayer_name())/play.map.adj_countries.size();
            	total_armies=player.getArmies();
            	setChanged();
            	notifyObservers(this);
            	
                play.reinforcement(player);
            }
            for (Player player : players) {
            	phase=2;
            	current_player=player.getPlayer_name();
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
                    //calculate reinforcement armies
                    this.players=Player.setReinforcementArmies(players,map.getCountries());
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
