package controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import model.Country;
import model.RiskMap;
import model.Player;


/**
 * GamePlay Class represents a game
 *
 * @author Prashanthi
 * @author Suthakhar
 * @version 1.0
 * @since 2019-02-27
 */
public class GamePlay {

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Array list that holds instances of all players
     */
    private ArrayList<Player> players;

    /**
     * Instance of RiskMap
     */
    public RiskMap map=new RiskMap();

    /**
     * Create a game play
     */
    public GamePlay()
    { }

    /**
     * Creates a game play with specified value of players and map
     * @param players list of all players in the game
     * @param map instance of RiskMap
     */
    public GamePlay(ArrayList<Player> players, RiskMap map) {
        this.players = players;
        this.map = map;
    }

    /**
     * Driver method to initiate the game phases in a round robin fashion for every player
     */
    public void start() {
        System.out.println("\n**** Game has started ****\n");
        boolean game_over = false;
        while (!game_over) {
            System.out.println("** New Round Begins **");
            System.out.println("\n** Reinforcement **");
            for (Player player : players) {
                System.out.println("\n** Player- " + player.getPlayer_name() + " **");
                reinforcement(player);
            }
            System.out.println("\n** Fortification **");
            for (Player player : players) {
                System.out.println("\n** Player- " + player.getPlayer_name() + " **");
                fortification(player);
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

    /**
     * Implementation of reinforcement phase of the game
     */
    private void reinforcement(Player player) {
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
    private void attack() {

    }

    /**
     * Implementation of fortification phase of game
     */
    private void fortification(Player player) {
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
    private boolean containsOtherPlayerCountriesAsNeighbours(String country_name, Player player){
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
