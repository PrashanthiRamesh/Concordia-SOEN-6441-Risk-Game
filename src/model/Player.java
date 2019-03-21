
package model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
     * Array list that holds instances of all players
     */
    public ArrayList<Player> players;

    /**
     * Instance of RiskMap
     */
    public RiskMap map=new RiskMap();
     
    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);
    /**
     * Creates a player
     */
    public Player() {
    }
    
    public Player(ArrayList<Player> players, RiskMap map)
    {
    	 this.players = players;
         this.map = map;
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
    public static ArrayList<Player> setReinforcementArmies(ArrayList<Player> players, ArrayList<Country> countries) {
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
            player.armies = calculateReinforcementArmies(player_countries_count);
            player.no_of_countries=player_countries_count;
            System.out.println();
            System.out.println("**** Player- "+player.player_name+" ****");
            System.out.println("Countries: "+player.no_of_countries);
            System.out.println("Armies: "+player.armies);
        }
        return players;
    }

    /**
     * This method sets the initial number of armies as per number of players playing in a game.
     * @param players list of players
     * @param countries list of countries
     * @return ArrayList of players
     */
    public ArrayList<Player> setInitialArmies(ArrayList<Player> players, ArrayList<Country> countries) {

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
            player.armies = calculateInitialArmies(players.size());
            player.no_of_countries=player_countries_count;
            System.out.println();
            System.out.println("**** Player- "+player.player_name+" ****");
            System.out.println("Countries: "+player.no_of_countries);
            System.out.println("Armies: "+player.armies);
        }
        return players;

    }

    /**
     * Calculate the number of initial armies for each player based on the total number of players in the game
     * @param no_of_players Number of players
     * @return no of armies for each player
     */
    public int calculateInitialArmies(int no_of_players){
        int initial_armies=0;
        if (no_of_players == 2) {
            initial_armies=40;
        } else if (no_of_players == 3) {
            initial_armies = 35;
        } else if (no_of_players == 4) {
            initial_armies = 30;
        } else if (no_of_players == 5) {
            initial_armies = 25;
        } else if(no_of_players==6){
            initial_armies = 20;
        }
        return initial_armies;
    }

    /**
     * Calculate the reinforcement armies of each player based on the total number of countries that a player owns
     * @param player_countries_count total number of countries that a player owns
     * @return number of reinforcement armies
     */
    public static int calculateReinforcementArmies(int player_countries_count){
        return (int) Math.floor(player_countries_count / 3.0);
    }
    
    /**
     * Implementation of reinforcement phase of the game
     */
    public void reinforcement(Player player) {
        int player_armies = player.getArmies();
        ArrayList<String> player_countries = player.getCountries();
        //System.out.println("\n**Reinforcement**\n");
        System.out.println("\nNo of armies to place: " + player_armies);
        System.out.println("Countries you own: " + player_countries);
        while (player_armies > 0) {
            System.out.println("Enter the name of the country to place armies in: ");
            boolean country_name_flag = false;
            while (!country_name_flag) {
                String country_name = scanner.next();
                if (player_countries.contains(country_name)) {
                    country_name_flag = true;
                    System.out.println("Enter the number of armies you want to place in this country: ");
                    boolean no_of_armies_flag = false;
                    while (!no_of_armies_flag) {
                        if (scanner.hasNextInt()) {
                            int no_of_armies_to_place = scanner.nextInt();
                            if (no_of_armies_to_place <= player_armies && no_of_armies_to_place>0) {
                                no_of_armies_flag = true;
                                System.out.println("Placing " + no_of_armies_to_place + " armies in country " + country_name);
                                ArrayList<Country> countries = map.getCountries();
                                for (Country country : countries) {
                                    if(country.getCountryName().equals(country_name)){
                                        int country_no_armies=country.getArmies();
                                        country.setArmies(country_no_armies+no_of_armies_to_place);
                                        System.out.println("Placed Successfully! Number of Armies in Country "+country_name+" is "+country.getArmies()+"\n");
                                    }
                                }
                                player_armies = player_armies - no_of_armies_to_place;
                                player.setArmies(player_armies);
                                System.out.println("Remaining armies to place: "+player_armies);
                            } else {
                                System.out.println("Ops Invalid! You don't have enough armies: Armies you own: " + player_armies + "\nEnter again: ");
                            }

                        } else {
                            System.out.println("Invalid characters! Enter numbers only: ");
                            scanner.next();
                        }
                    }
                } else {
                    System.out.println("Invalid! You don't own the entered country! Please enter again: ");
                }
            }
        }
        System.out.println("You have successfully placed all armies in your countries!");
        System.out.println(map.getCountries());
    }

    /**
     * Implementation of attack phase of game
     */
    public void attack() {

    }

    /**
     * Implementation of fortification phase of game
     */
    public void fortification(Player player) {
        ArrayList<String> player_countries = player.getCountries();
        ArrayList<Country> countries = map.getCountries();
      //  System.out.println("\n** Fortification **\n");
        System.out.println("\nCountries you own: "+player_countries);
        for(Country country:countries){
            if(player_countries.contains(country.getCountryName())){
                System.out.println("Country "+country.getCountryName()+" has "+country.getArmies()+ " armies");
            }
        }
        System.out.println("Enter the country from which you want to move armies from: ");
        boolean player_from_country_flag=false;
        while (!player_from_country_flag){
            String move_armies_from=scanner.next();
            if(noOfArmiesInCountry(move_armies_from)>0){
                if(player_countries.contains(move_armies_from)){
                    if(containsOtherPlayerCountriesAsNeighbours(move_armies_from,player)){
                        player_from_country_flag=true;
                        LinkedHashMap<String, ArrayList<String>> all_countries_with_neighbours=map.getAdj_countries();
                        ArrayList<String> selected_country_neighbours=all_countries_with_neighbours.get(move_armies_from);
                        System.out.println("Neighbours to "+move_armies_from+" : "+selected_country_neighbours);
                        System.out.println("Enter the country to which you want to move armies to: ");
                        boolean player_to_country_flag=false;
                        while (!player_to_country_flag){
                            String move_armies_to=scanner.next();
                            if(selected_country_neighbours.contains(move_armies_to)){
                                if(!player_countries.contains(move_armies_to)){
                                    player_to_country_flag=true;
                                    int no_of_armies_from=noOfArmiesInCountry(move_armies_from);
                                    System.out.println("No of armies in your country "+move_armies_from+" : "+ no_of_armies_from);
                                    System.out.println("Enter the number of armies to move: ");
                                    boolean no_of_armies_flag=false;
                                    while (!no_of_armies_flag){
                                        if (scanner.hasNextInt()) {
                                            int no_of_armies_to_move = scanner.nextInt();
                                            if(no_of_armies_to_move<=no_of_armies_from && no_of_armies_to_move>0){
                                                no_of_armies_flag=true;
                                                System.out.println("** Before moving armies **\n");
                                                System.out.println("No of armies in country "+ move_armies_from+" (from): "+noOfArmiesInCountry(move_armies_from));
                                                System.out.println("No of armies in country "+ move_armies_to+" (to): "+noOfArmiesInCountry(move_armies_to));
                                                for(Country country:countries){
                                                    if(country.getCountryName().equals(move_armies_from)){
                                                        country.setArmies(country.getArmies()-no_of_armies_to_move);
                                                    }
                                                    if(country.getCountryName().equals(move_armies_to)){
                                                        country.setArmies(country.getArmies()+no_of_armies_to_move);
                                                    }
                                                }
                                                System.out.println("** After moving armies **\n");
                                                System.out.println("No of armies in country "+ move_armies_from+" (from): "+noOfArmiesInCountry(move_armies_from));
                                                System.out.println("No of armies in country "+ move_armies_to+" (to): "+noOfArmiesInCountry(move_armies_to));

                                            }else{
                                                System.out.println("Invalid! Enter again: ");
                                            }
                                        }else{
                                            System.out.println("Invalid characters! Enter again: ");
                                            scanner.next();
                                        }
                                    }
                                }else{
                                    System.out.println("Invalid! You can move armies to your own country! Enter again: ");
                                }
                            }else{
                                System.out.println("Invalid! Not a neighbouring country! Enter again: ");
                            }
                        }
                    }else{
                        System.out.println("Invalid! You own all neighbouring countries! Enter a different country:");
                    }

                }else{
                    System.out.println("Invalid! You don't own this country! Enter again: ");
                }
            }else{
                System.out.println("Invalid! No armies in this country! Enter again: ");
            }

        }
    }

    /**
     * Calculate the number of armies that a country has at a particular time in the game
     * @param country_name name of the country
     * @return number of armies in the country
     */
    public int noOfArmiesInCountry(String country_name){
       
    	ArrayList<Country> countries = map.getCountries();
        for(Country country:countries){
            if(country.getCountryName().equals(country_name)){
                return country.getArmies();
            }
        }
        return -1;
    }

    /**
     * Checks if player contains neighbor countries that are owned by a different player
     * @param country_name name of country
     * @param player player instance
     * @return true if at least one neighbour country is owned by a different player, else false
     */
    public boolean containsOtherPlayerCountriesAsNeighbours(String country_name, Player player){
        LinkedHashMap<String, ArrayList<String>> all_countries_with_neighbours=map.getAdj_countries();
        ArrayList<String> neighbours=all_countries_with_neighbours.get(country_name);
        for (String neighbour:neighbours){
            if(!player.getCountries().contains(neighbour)){
                return true;
            }
        }
        return false;
    }
}
