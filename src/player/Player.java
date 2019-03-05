package player;

import country.Country;

import java.util.ArrayList;

/**
 * Player Class represents a player
 *
 * @author Manasa
 * @version 1.0
 * @since 2019-02-27
 */
public class Player {

    /**
     * Stores the player's name
     */
    private String player_name;

    /**
     * Stores the number of armies of the player
     */
    private int armies;

    /**
     * Store the number of countries of the player
     */
    private int no_of_countries;

    /**
     * Store the countries the player owns
     */
    private ArrayList<String> countries;

    /**
     * Creates a player
     */
    public Player() {
    }

    /**
     * Creates a player with the specified player's name and armies
     * @param player_name  A String representing the name of the player
     * @param armies An integer representing the number of armies of player
     * @param countries An ArrayList representing the list of countries that the player owns
     */
    public Player(String player_name, int armies, ArrayList<String> countries) {
        this.player_name = player_name;
        this.armies = armies;
        this.countries=countries;
    }

    /**
     * Getter method to get the name of the player
     * @return player name
     */
    public String getPlayer_name() {
        return player_name;
    }

    /**
     * Setter method to assign a string value to the name of the player
     * @param player_name name of the player
     */
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    /**
     * Getter method to get the number of armies of player
     * @return player's number of armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Setter method to assign an integer value to the number of armies to the player
     * @param armies number of armies of player
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * Getter method to get the number of countries that a player owns
     * @return number of armies that a player owns
     */
    public int getNo_of_countries() {
        return no_of_countries;
    }

    /**
     * Setter method to assign the number of countries that a player owns
     * @param no_of_countries number of countries that a player owns
     */
    public void setNo_of_countries(int no_of_countries) {
        this.no_of_countries = no_of_countries;
    }

    /**
     * Getter method to get the list of countries the player owns
     * @return countries that the player owns
     */
    public ArrayList<String> getCountries() {
        return countries;
    }

    /**
     * Setter method to assign the list of countries that the player owns
     * @param countries list of countries the player owns
     */
    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    /**
     * Calculation of correct number of reinforcement armies according to the Risk rules.
     * @param players List of players in the game
     * @param countries List of countries in the game
     */
    public ArrayList<Player> calculateReinforcementArmies(ArrayList<Player> players, ArrayList<Country> countries) {
        for (Player player : players) {
            int player_countries_count=0;
            ArrayList<String> player_countries=new ArrayList<>();
            for (Country country : countries) {
                if (country.getBelongsTo().equals(player.player_name)) {
                    player_countries_count++;
                    player_countries.add(country.getCountryName());
                }
            }
            player.countries=player_countries;
            player.armies =(int) Math.floor(player_countries_count / 3.0);
            player.no_of_countries=player_countries_count;
            System.out.println();
            System.out.println("**** Player- "+player.player_name+" ****");
            System.out.println("Countries: "+player.no_of_countries);
            System.out.println("Armies: "+player.armies);
        }
        return players;
    }

}
