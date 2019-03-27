
package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import util.Util;

/**
 * Player Class represents a player
 *
 * @author Manasa
 * @version 1.0
 * @since 2019-02-27
 */
public class Player extends Observable {

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
     * Stores the cards the player owns
     */
    private ArrayList<Card> cards;

    /**
     * Array list that holds instances of all players
     */
    public ArrayList<Player> players;

    private Player currentPlayer;

    private int infantryCount;

    private int cavalryCount;

    private int cannonCount;

    /**
     * Instance of RiskMap
     */
    public RiskMap map = new RiskMap();

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Creates a player
     */
    public Player() {
    }

    public Player(ArrayList<Player> players, RiskMap map) {
        this.players = players;
        this.map = map;
    }

    /**
     * Creates a player with the specified player's name and armies
     *
     * @param player_name A String representing the name of the player
     * @param armies      An integer representing the number of armies of player
     * @param countries   An ArrayList representing the list of countries that the player owns
     */
    public Player(String player_name, int armies, ArrayList<String> countries,
                  ArrayList<Card> cards, int infantryCount, int cavalryCount, int cannonCount) {
        this.player_name = player_name;
        this.armies = armies;
        this.countries = countries;
        this.cards = cards;
        this.infantryCount=infantryCount;
        this.cavalryCount=cavalryCount;
        this.cannonCount=cannonCount;
    }

    /**
     * Getter method to get the name of the player
     *
     * @return player name
     */
    public String getPlayerName() {
        return player_name;
    }

    /**
     * Setter method to assign a string value to the name of the player
     *
     * @param player_name name of the player
     */
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    /**
     * Getter method to get the number of armies of player
     *
     * @return player's number of armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Setter method to assign an integer value to the number of armies to the player
     *
     * @param armies number of armies of player
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * Getter method to get the number of countries that a player owns
     *
     * @return number of armies that a player owns
     */
    public int getNo_of_countries() {
        return no_of_countries;
    }

    /**
     * Setter method to assign the number of countries that a player owns
     *
     * @param no_of_countries number of countries that a player owns
     */
    public void setNo_of_countries(int no_of_countries) {
        this.no_of_countries = no_of_countries;
    }

    /**
     * Getter method to get the list of countries the player owns
     *
     * @return countries that the player owns
     */
    public ArrayList<String> getCountries() {
        return countries;
    }

    /**
     * Setter method to assign the list of countries that the player owns
     *
     * @param countries list of countries the player owns
     */
    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getInfantryCount() {
        return infantryCount;
    }

    public void setInfantryCount(int infantryCount) {
        this.infantryCount = infantryCount;
    }

    public int getCavalryCount() {
        return cavalryCount;
    }

    public void setCavalryCount(int cavalryCount) {
        this.cavalryCount = cavalryCount;
    }

    public int getCannonCount() {
        return cannonCount;
    }

    public void setCannonCount(int cannonCount) {
        this.cannonCount = cannonCount;
    }


    /**
     * Calculation of correct number of reinforcement armies according to the Risk rules.
     *
     * @param player    A Player in the game
     * @param countries List of countries in the game
     */
    private static void setReinforcementArmies(Player player, ArrayList<Country> countries) {

        int player_countries_count = 0;
        ArrayList<String> player_countries = new ArrayList<>();
        for (Country country : countries) {
            if (country.getBelongsTo().equals(player.player_name)) {
                player_countries_count++;
                player_countries.add(country.getCountryName());
            }
        }
        player.countries = player_countries;
        player.armies = calculateReinforcementArmies(player_countries_count);
        player.no_of_countries = player_countries_count;
        System.out.println();
        System.out.println("Countries: " + player.no_of_countries);
        System.out.println("Reinforcement Armies: " + player.armies);

    }

    private void setArmiesForContinentsControlled(Player player, ArrayList<String> playerCountries, int playerArmies) {
        ArrayList<String> playerContinents = map.continentsControlledByPlayer(playerCountries);
        if (!playerContinents.isEmpty()) {
            System.out.println("Player controls the following continents: " + playerContinents);
            LinkedHashMap<String, Integer> continents = map.getContinents();
            System.out.println("All Continents and their control values: " + continents);
            System.out.println("** Assigning Armies based on Controlled Continents Control Values**\n");
            for (String playerContinent : playerContinents) {
                playerArmies += continents.get(playerContinent);
            }
            player.setArmies(playerArmies);
            System.out.println();
            System.out.println("\nAfter assigning armies for continents controlled**\nPlayer Armies: " + playerArmies);
        } else {
            System.out.println("Player does not control any continents yet!");
        }
    }


    /**
     * This method sets the initial number of armies as per number of players playing in a game.
     *
     * @param players   list of players
     * @param countries list of countries
     * @return ArrayList of players
     */
    public ArrayList<Player> setInitialArmies(ArrayList<Player> players, ArrayList<Country> countries) {

        for (Player player : players) {
            int player_countries_count = 0;
            ArrayList<String> player_countries = new ArrayList<>();
            for (Country country : countries) {
                if (country.getBelongsTo().equals(player.player_name)) {
                    player_countries_count++;
                    player_countries.add(country.getCountryName());
                }
            }
            player.countries = player_countries;
            player.armies = calculateInitialArmies(players.size());
            player.no_of_countries = player_countries_count;
            System.out.println();
            System.out.println("**** Player- " + player.player_name + " ****");
            System.out.println("Countries: " + player.no_of_countries);
            System.out.println("Armies: " + player.armies);
        }
        return players;

    }

    /**
     * Calculate the number of initial armies for each player based on the total number of players in the game
     *
     * @param no_of_players Number of players
     * @return no of armies for each player
     */
    public int calculateInitialArmies(int no_of_players) {
        int initial_armies = 0;
        if (no_of_players == 2) {
            initial_armies = 40;
        } else if (no_of_players == 3) {
            initial_armies = 35;
        } else if (no_of_players == 4) {
            initial_armies = 30;
        } else if (no_of_players == 5) {
            initial_armies = 25;
        } else if (no_of_players == 6) {
            initial_armies = 20;
        }
        return initial_armies;
    }

    /**
     * Calculate the reinforcement armies of each player based on the total number of countries that a player owns
     *
     * @param player_countries_count total number of countries that a player owns
     * @return number of reinforcement armies
     */
    public static int calculateReinforcementArmies(int player_countries_count) {
        return (int) Math.floor(player_countries_count / 3.0);
    }


    public void deployArmies(Player player, RiskMap mapInstance) {

        int player_armies = player.getArmies();
        ArrayList<String> player_countries = player.getCountries();
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
                            if (no_of_armies_to_place <= player_armies && no_of_armies_to_place > 0) {
                                no_of_armies_flag = true;
                                System.out.println("Placing " + no_of_armies_to_place + " armies in country " + country_name);
                                ArrayList<Country> countries = mapInstance.getCountries();
                                for (Country country : countries) {
                                    if (country.getCountryName().equals(country_name)) {
                                        int country_no_armies = country.getArmies();
                                        country.setArmies(country_no_armies + no_of_armies_to_place);
                                        System.out.println("Placed Successfully! Number of Armies in Country " + country_name + " is " + country.getArmies() + "\n");
                                    }
                                }
                                player_armies = player_armies - no_of_armies_to_place;
                                player.setArmies(player_armies);
                                System.out.println("Remaining armies to place: " + player_armies);
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
     * Implementation of reinforcement phase of the game
     */
    public void reinforcement(Player player, RiskMap mapInstance) {
        //calculate reinforcement armies
        setReinforcementArmies(player, mapInstance.getCountries());
        setArmiesForContinentsControlled(player, player.getCountries(), player.getArmies());
        currentPlayer = player;
        setChanged();
        notifyObservers(this);
        System.out.println("\nAfter Card Exchange View");
        displayPlayerCards(player);
        System.out.println("Player armies: " + player.getArmies());
        deployArmies(player, mapInstance);
    }

    /**
     * Implementation of attack phase of game
     *
     * @param mapInstance
     */
    public void attack(Player player, RiskMap mapInstance) {

        attackcontinue:
        while (true) {
            ArrayList<Country> countriescopy = mapInstance.getCountries();
            Scanner scan = new Scanner(System.in);
            System.out.println("Select Player to Attack From the List");
            int count = 0;
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getPlayerName().equals(player.getPlayerName())) {
                    System.out.println(players.get(i).getPlayerName());
                }
            }
            String attacker = scan.next();

            System.out.println("The Countries You Can Attack of the Player " + attacker + " are :");
            ArrayList<String> playerCountries = player.getCountries();
            ArrayList<String> store = new ArrayList<String>();
            for (int i = 0; i < playerCountries.size(); i++) {
                ArrayList<String> adjcont = map.adjCountries.get(playerCountries.get(i));


                for (int j = 0; j < adjcont.size(); j++) {
                    for (int k = 0; k < countriescopy.size(); k++) {
                        if (adjcont.get(j).equals(countriescopy.get(k).getCountryName())) {

                            if (countriescopy.get(k).getBelongsTo().equals(attacker)) {
                                store.add(countriescopy.get(k).getCountryName());

                            }
                        }
                    }
                }

                for (int p = 0; p < countriescopy.size(); p++) {
                    if (countriescopy.get(p).getCountryName().equals(playerCountries.get(i))) {
                        System.out.print(playerCountries.get(i) + "-" + countriescopy.get(p).getArmies());
                    }
                }
                System.out.print("------> [");
                for (int m = 0; m < store.size(); m++) {
                    for (int p = 0; p < countriescopy.size(); p++) {
                        if (countriescopy.get(p).getCountryName().equals(store.get(m))) {
                            System.out.print(store.get(m) + "-" + countriescopy.get(p).getArmies());
                            if (m != store.size() - 1) {
                                System.out.print(",");
                            }
                        }

                    }
                }
                System.out.print("]");
                System.out.println();
                store.clear();
            }
            int attackerArmies = 0;
            int defenderArmies = 0;
            String defendingcountry;
            String attackingcountry;
            int attackerDice = 0;
            int defenderDice = 0;
            ZeroArmy:
            while (true) {

                System.out.println("Select the Country you want to Attack");
                defendingcountry = scan.next();
                System.out.println("Select Your country To attack");
                attackingcountry = scan.next();

                boolean check = false;
                for (int i = 0; i < map.adjCountries.get(attackingcountry).size(); i++) {
                    if (map.adjCountries.get(attackingcountry).get(i).equals(defendingcountry)) {

                        check = true;
                    }

                }

                if (check == false) {
                    System.out.println("It is not a Neighbouring country Please check and enter again");
                    continue ZeroArmy;
                }
                for (int i = 0; i < countriescopy.size(); i++) {
                    if (countriescopy.get(i).getCountryName().equals(attackingcountry)) {
                        attackerArmies = countriescopy.get(i).getArmies();
                        if (countriescopy.get(i).getArmies() == 0) {

                            System.out.println("You have no armies in the selected country");
                            continue ZeroArmy;
                        } else
                            break ZeroArmy;
                    }
                }

            }

            for (int i = 0; i < countriescopy.size(); i++) {
                if (countriescopy.get(i).getCountryName().equals(defendingcountry)) {
                    defenderArmies = countriescopy.get(i).getArmies();


                }
            }

            while (true) {

                if (defenderArmies == 0) {
                    defenderDice = 1;
                } else {
                    defenderDice = 2;
                }
                if (attackerArmies >= 3){
                    System.out.println("You Can Roll Atmost 3 dice");
                    attackerDice = scan.nextInt();
                } else if (attackerArmies == 2){
                    System.out.println("You Can Roll Atmost 2 dice");
                    attackerDice = scan.nextInt();
                }else{
                    System.out.println("You Can only Roll the Dice once");
                    attackerDice =1;
                }


                Integer attackerRandomNumbers[] = new Integer[attackerDice];
                Integer defenderRandomNumbers[] = new Integer[defenderDice];

                for (int i = 0; i < attackerDice; i++) {
                    attackerRandomNumbers[i] = Util.randInt(1, 6);
                }
                for (int i = 0; i < defenderDice; i++) {
                    defenderRandomNumbers[i] = Util.randInt(1, 6);
                }
                Arrays.sort(attackerRandomNumbers, Collections.reverseOrder());
                Arrays.sort(defenderRandomNumbers, Collections.reverseOrder());

                System.out.println("Dice Rolled!!!");
                System.out.println("Attacker's Dice");
                for (int i = 0; i < attackerDice; i++) {
                    System.out.print(attackerRandomNumbers[i] + " ");
                }
                System.out.println();
                System.out.println("Defenders Dice");
                for (int i = 0; i < defenderDice; i++) {
                    System.out.print(defenderRandomNumbers[i] + " ");
                }
                System.out.println();
                if (attackerDice < defenderDice) {
                    for (int i = 0; i < attackerDice; i++) {
                        if (attackerRandomNumbers[i] <= defenderRandomNumbers[i]) {
                            attackerArmies--;
                        } else {
                            defenderArmies--;
                        }
                    }
                } else {
                    for (int i = 0; i < defenderDice; i++) {
                        if (attackerRandomNumbers[i] <= defenderRandomNumbers[i]) {
                            attackerArmies--;
                        } else {
                            defenderArmies--;
                        }
                    }
                }

                if (attackerArmies == 0) {
                    System.out.println("Defender has Won");

                    assignCardsToWinner(getPlayerInstanceByName(attacker));
                    for (int i = 0; i < countriescopy.size(); i++) {
                        if (countriescopy.get(i).getCountryName().equals(defendingcountry)) {
                            mapInstance.countries.get(i).setArmies(defenderArmies);


                        }
                    }

                    for (int i = 0; i < countriescopy.size(); i++) {
                        if (countriescopy.get(i).getCountryName().equals(attackingcountry)) {
                            mapInstance.countries.get(i).setArmies(0);

                        }
                    }

                    break;
                } else if (defenderArmies == -1) {
                    System.out.println("You Won");
                    attackerArmies--;

                    if (attackerArmies > 1) {
                        System.out.println("You Have " + attackerArmies + " Left");
                        System.out.println("Enter the Number of armies you want to leave behind from the territory you came");
                        int temp = scan.nextInt();
                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(attackingcountry)) {
                                mapInstance.countries.get(i).setArmies(temp);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                                break;
                            }
                        }

                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(defendingcountry)) {
                                mapInstance.countries.get(i).setArmies(attackerArmies - temp);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                                playerCountries.add(defendingcountry);
                                break;

                            }
                        }

                    } else if (attackerArmies == 1) {
                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(defendingcountry)) {
                                mapInstance.countries.get(i).setArmies(1);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                            }
                        }

                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(attackingcountry)) {
                                mapInstance.countries.get(i).setArmies(0);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());

                            }
                        }
                    } else {
                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(defendingcountry)) {
                                mapInstance.countries.get(i).setArmies(0);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                            }
                        }
                        for (int i = 0; i < countriescopy.size(); i++) {
                            if (countriescopy.get(i).getCountryName().equals(attackingcountry)) {
                                mapInstance.countries.get(i).setArmies(0);
                                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                            }
                        }
                    }

                    assignCardsToWinner(player);
                    break;
                }


            }

            int counter = 0;
            for (int i = 0; i < countriescopy.size(); i++) {
                if (countriescopy.get(i).getBelongsTo().equals(player.getPlayerName())) {
                    counter++;
                }
            }
            if (countriescopy.size() == counter) {
                System.out.println("You Won Please the collect the reward from the TA :)");
                System.exit(0);
            }

            counter = 0;
            for (int i = 0; i < countriescopy.size(); i++) {
                if (countriescopy.get(i).getBelongsTo().equals(player.getPlayerName())) {
                    if (countriescopy.get(i).getArmies() > 0)
                        counter++;
                }
            }
            if (counter < 1) {
                System.out.println("Attack Not Possible No Armies Left");

                break attackcontinue;
            }

            System.out.println("Do You Want to Continue the Attack \n Yes or No ?");

            String response = scan.next();

            if (response.equals("Yes")) {
                continue attackcontinue;
            } else if (response.equals("No")) {
                break attackcontinue;
            }

        }


    }


    public void displayPlayerCards(Player player) {
        int cardCount=player.getInfantryCount()+player.getCannonCount()+player.getCannonCount();
        if(cardCount>0){
            System.out.print("\n**Cards**\nPlayer has " + cardCount + " card(s)\nThey are: \n");
            for (int i=1;i<=player.infantryCount;i++) {
                System.out.println(i+"- Infantry Card");
            }
            for (int i=1;i<=player.cavalryCount;i++) {
                System.out.println(i+"- Cavalry Card");
            }
            for (int i=1;i<=player.cannonCount;i++) {
                System.out.println(i+"- Cannon Card");
            }
            System.out.println("\n");
        }else{
            System.out.print("\n**Player has zero cards!**\n");
        }


    }


    /**
     * Implementation of fortification phase of game
     */
    public void fortification(Player player) {
        ArrayList<String> player_countries = player.getCountries();
        ArrayList<Country> countries = map.getCountries();
        System.out.println("\nCountries you own: " + player_countries);
        for (Country country : countries) {
            if (player_countries.contains(country.getCountryName())) {
                System.out.println("Country " + country.getCountryName() + " has " + country.getArmies() + " armies");
            }
        }
        System.out.println("Enter the country from which you want to move armies from or Enter 'exit' to not move armies: ");
        boolean player_from_country_flag = false;
        while (!player_from_country_flag) {
            String move_armies_from = scanner.next();
            if (!move_armies_from.toLowerCase().equals("exit")) {
                if (noOfArmiesInCountry(move_armies_from) > 0) {
                    if (player_countries.contains(move_armies_from)) {
                        if (containsCountriesAsNeighbours(move_armies_from, player)) {
                            player_from_country_flag = true;
                            LinkedHashMap<String, ArrayList<String>> all_countries_with_neighbours = map.getAdjCountries();
                            ArrayList<String> selected_country_neighbours = all_countries_with_neighbours.get(move_armies_from);
                            System.out.println("Neighbours to " + move_armies_from + " : " + selected_country_neighbours);
                            System.out.println("Enter the country to which you want to move armies to: ");
                            boolean player_to_country_flag = false;
                            while (!player_to_country_flag) {
                                String move_armies_to = scanner.next();
                                if (selected_country_neighbours.contains(move_armies_to)) {
                                    if (player_countries.contains(move_armies_to)) {
                                        player_to_country_flag = true;
                                        int no_of_armies_from = noOfArmiesInCountry(move_armies_from);
                                        System.out.println("No of armies in your country " + move_armies_from + " : " + no_of_armies_from);
                                        System.out.println("Enter the number of armies to move: ");
                                        boolean no_of_armies_flag = false;
                                        while (!no_of_armies_flag) {
                                            if (scanner.hasNextInt()) {
                                                int no_of_armies_to_move = scanner.nextInt();
                                                if (no_of_armies_to_move <= no_of_armies_from && no_of_armies_to_move > 0) {
                                                    no_of_armies_flag = true;
                                                    System.out.println("** Before moving armies **\n");
                                                    System.out.println("No of armies in country " + move_armies_from + " (from): " + noOfArmiesInCountry(move_armies_from));
                                                    System.out.println("No of armies in country " + move_armies_to + " (to): " + noOfArmiesInCountry(move_armies_to));
                                                    for (Country country : countries) {
                                                        if (country.getCountryName().equals(move_armies_from)) {
                                                            country.setArmies(country.getArmies() - no_of_armies_to_move);
                                                        }
                                                        if (country.getCountryName().equals(move_armies_to)) {
                                                            country.setArmies(country.getArmies() + no_of_armies_to_move);
                                                        }
                                                    }
                                                    System.out.println("** After moving armies **\n");
                                                    System.out.println("No of armies in country " + move_armies_from + " (from): " + noOfArmiesInCountry(move_armies_from));
                                                    System.out.println("No of armies in country " + move_armies_to + " (to): " + noOfArmiesInCountry(move_armies_to));

                                                } else {
                                                    System.out.println("Invalid! Enter again: ");
                                                }
                                            } else {
                                                System.out.println("Invalid characters! Enter again: ");
                                                scanner.next();
                                            }
                                        }
                                    } else {
                                        System.out.println("Invalid! You can only move armies to your own country! Enter again: ");
                                    }
                                } else {
                                    System.out.println("Invalid! Not a neighbouring country! Enter again: ");
                                }
                            }
                        } else {
                            System.out.println("Invalid! You don't own any neighbouring countries! Enter a different country:");
                        }

                    } else {
                        System.out.println("Invalid! You don't own this country! Enter again: ");
                    }
                } else {
                    System.out.println("Invalid! No armies in this country! Enter again: ");
                }
            } else {
                System.out.println("Exiting Fortification Phase!");
                player_from_country_flag = true;
            }
        }
        if(!doesPlayerOwnAtLeastOneCountry(player)){
            players.remove(player);
        }

    }

    /**
     * Calculate the number of armies that a country has at a particular time in the game
     *
     * @param country_name name of the country
     * @return number of armies in the country
     */
    public int noOfArmiesInCountry(String country_name) {

        ArrayList<Country> countries = map.getCountries();
        for (Country country : countries) {
            if (country.getCountryName().equals(country_name)) {
                return country.getArmies();
            }
        }
        return -1;
    }

    /**
     * Checks if player contains neighbor countries that are owned by a different player
     *
     * @param country_name name of country
     * @param player       player instance
     * @return true if at least one neighbour country is owned by a different player, else false
     */
    public boolean containsCountriesAsNeighbours(String country_name, Player player) {
        LinkedHashMap<String, ArrayList<String>> allCountriesWithNeighbours = map.getAdjCountries();
        ArrayList<String> neighbours = allCountriesWithNeighbours.get(country_name);
        for (String neighbour : neighbours) {
            if (player.getCountries().contains(neighbour)) {
                return true;
            }
        }
        return false;
    }

    private void assignCardsToWinner(Player winner) {
        ArrayList<Card> cards = winner.cards;
        int cardTypeValue = Util.randInt(1, 3);
        String cardName = Card.getNameByTypeNumber(cardTypeValue);
        switch (cardTypeValue){
            case 1:
                winner.setInfantryCount(winner.getInfantryCount()+1);
                break;
            case 2:
                winner.setCavalryCount(winner.getCavalryCount()+1);
                break;
            case 3:
                winner.setCannonCount(winner.getCannonCount()+1);
                break;
        }
        System.out.println("**Assigning "+ cardName+" Card to the Winner- "+winner.getPlayerName()+" **");
        cards.add(new Card(cardName, cardTypeValue));
        winner.cards = cards;
        displayPlayerCards(winner);

    }

    private Player getPlayerInstanceByName(String name) {
        for (Player player : players) {
            if (player.getPlayerName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    private boolean doesPlayerOwnAtLeastOneCountry(Player player){
        return player.getCountries().size() != 0;
    }


}
