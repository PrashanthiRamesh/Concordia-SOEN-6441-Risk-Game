
package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Scanner;


import player.Strategy;
import util.Util;

/**
 * Player Class represents a player
 *
 * @author Manasa
 * @version 1.1
 * @since 1.0
 */
public class Player extends Observable {

    /**
     * Stores the player's name
     */
    private String playerName;

    /**
     * Stores the number of armies of the player
     */
    private int armies;

    /**
     * Store the number of countries of the player
     */
    private int noOfCountries;

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

    /**
     * Instance of the current player
     */
    private Player currentPlayer;

    /**
     * Number of infantry cards of player
     */
    private int infantryCount;

    /**
     * Number of cavalry cards of player
     */
    private int cavalryCount;

    /**
     * Number of cannon cards of player
     */
    private int cannonCount;
    
    /*
     * Instance of player's strategy depending on their behaviour/personality
     */
    public Strategy playerStrategy;
    
    
    private boolean isHuman;

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

    /**
     * Creates a player with the players list and map of the game
     *
     * @param players list of players
     * @param map     instance of map
     */
    public Player(ArrayList<Player> players, RiskMap map) {
        this.players = players;
        this.map = map;
    }

    /**
     * Creates a player with the specified player's name and armies
     *
     * @param playerName A String representing the name of the player
     * @param armies     An integer representing the number of armies of player
     * @param countries  An ArrayList representing the list of countries that the player owns
     * @param cards A list of cards a player owns
     * @param infantryCount Number of infantry cards of player
     * @param cavalryCount Number of cavalry cards of player
     * @param cannonCount Number of cannon cards of player
     */
    public Player(String playerName, int armies, ArrayList<String> countries,
                  ArrayList<Card> cards, int infantryCount, int cavalryCount, int cannonCount) {
        this.playerName = playerName;
        this.armies = armies;
        this.countries = countries;
        this.cards = cards;
        this.infantryCount = infantryCount;
        this.cavalryCount = cavalryCount;
        this.cannonCount = cannonCount;
    }

    /**
     * Getter method to get the name of the player
     *
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter method to assign a string value to the name of the player
     *
     * @param playerName name of the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
    public int getNoOfCountries() {
        return noOfCountries;
    }

    /**
     * Setter method to assign the number of countries that a player owns
     *
     * @param noOfCountries number of countries that a player owns
     */
    public void setNoOfCountries(int noOfCountries) {
        this.noOfCountries = noOfCountries;
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

    /**
     * Getter method to get the list of cards of player
     * @return list of cards that player owns
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Setter method to assign a player a list of cards
     * @param cards list of cards
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Getter method to set the instance of current player
     * @return instance of current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter method to set the instance of current player
     * @param currentPlayer instance of current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter method to return the number of infantry cards of player
     * @return number of infantry cards the player owns
     */
    public int getInfantryCount() {
        return infantryCount;
    }

    /**
     * Setter method to assign the number of infantry cards of a player
     * @param infantryCount number of infantry cards
     */
    public void setInfantryCount(int infantryCount) {
        this.infantryCount = infantryCount;
    }

    /**
     * Getter method to return the number of cavalry cards of player
     * @return number of cavalry cards the player owns
     */
    public int getCavalryCount() {
        return cavalryCount;
    }

    /**
     * Setter method to assign the number of cavalry cards of a player
     * @param cavalryCount number of cavalry cards
     */
    public void setCavalryCount(int cavalryCount) {
        this.cavalryCount = cavalryCount;
    }

    /**
     * Getter method to return the number of cannon cards of player
     * @return number of cannon cards the player owns
     */
    public int getCannonCount() {
        return cannonCount;
    }

    /**
     * Setter method to assign the number of cannon cards of a player
     * @param cannonCount number of cannon cards
     */
    public void setCannonCount(int cannonCount) {
        this.cannonCount = cannonCount;
    }

    
    
    public Strategy getPlayerStrategy() {
		return playerStrategy;
	}

	public void setPlayerStrategy(Strategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	
	public boolean isHuman() {
		return isHuman;
	}

	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}

	/**
     * Calculation of correct number of reinforcement armies according to the Risk rules.
     *
     * @param player    A Player in the game
     * @param countries List of countries in the game
     */
    public void setReinforcementArmies(Player player, ArrayList<Country> countries) {

        int playerCountriesCount = 0;
        ArrayList<String> playerCountries = new ArrayList<>();
        for (Country country : countries) {
            if (country.getBelongsTo().equals(player.playerName)) {
                playerCountriesCount++;
                playerCountries.add(country.getCountryName());
            }
        }
        player.countries = playerCountries;
        player.armies = calculateReinforcementArmies(playerCountriesCount);
        player.noOfCountries = playerCountriesCount;
        System.out.println();
        System.out.println("Countries: " + player.noOfCountries);
        System.out.println("Reinforcement Armies: " + player.armies);

    }

    /**
     * Method to set the reinforcement armies by assigning control values for players depending on the continents they control
     * @param player instance of player
     * @param playerCountries list of countries a player controls
     * @param playerArmies number of armies of player
     */
    public void setArmiesForContinentsControlled(Player player, ArrayList<String> playerCountries, int playerArmies) {
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
            int playerCountriesCount = 0;
            ArrayList<String> playerCountries = new ArrayList<>();
            for (Country country : countries) {
                if (country.getBelongsTo().equals(player.playerName)) {
                    playerCountriesCount++;
                    playerCountries.add(country.getCountryName());
                }
            }
            player.countries = playerCountries;
            player.armies = calculateInitialArmies(players.size());
            player.noOfCountries = playerCountriesCount;
            System.out.println();
            System.out.println("**** Player- " + player.playerName + " ****");
            System.out.println("Countries: " + player.noOfCountries);
            System.out.println("Armies: " + player.armies);
        }
        return players;

    }

    /**
     * Calculate the number of initial armies for each player based on the total number of players in the game
     *
     * @param noOfPlayers Number of players
     * @return no of armies for each player
     */
    public int calculateInitialArmies(int noOfPlayers) {
        int initialArmies = 0;
        if (noOfPlayers == 2) {
            initialArmies = 40;
        } else if (noOfPlayers == 3) {
            initialArmies = 35;
        } else if (noOfPlayers == 4) {
            initialArmies = 30;
        } else if (noOfPlayers == 5) {
            initialArmies = 25;
        } else if (noOfPlayers == 6) {
            initialArmies = 20;
        }
        return initialArmies;
    }

    /**
     * Calculate the reinforcement armies of each player based on the total number of countries that a player owns
     *
     * @param playerCountriesCount total number of countries that a player owns
     * @return number of reinforcement armies
     */
    static int calculateReinforcementArmies(int playerCountriesCount) {
        return (int) Math.floor(playerCountriesCount / 3.0);
    }


    /**
     * Method to calculate the number of armies left for a player
     * @param player instance of player
     * @param countriescopy list of country instances
     * @param armiesLeft number of armies left for player
     * @return number of armies left for the player
     */
    public int getArmiesLeftForPlayer(Player player, ArrayList<Country> countriescopy, int armiesLeft) {
        for (int i = 0; i < countriescopy.size(); i++) {
            if (countriescopy.get(i).getBelongsTo().equals(player.getPlayerName())) {
                if (countriescopy.get(i).getArmies() > 0)
                    armiesLeft++;
            }
        }
        return armiesLeft;
    }

    /**
     * A valid move of armies of attacker between countries after conquering
     * @param player instance of player
     * @param mapInstance instance of map
     * @param countriescopy list of  country instances
     * @param playerCountries list of player countries
     * @param attackerArmies number of attacker armies
     * @param defendingcountry name of defending country
     * @param attackingcountry name of attacking country
     * @param armiesToMove number of armies to move
     * @return true for testing
     */
    public boolean moveAfterConquering(Player player, RiskMap mapInstance, ArrayList<Country> countriescopy, ArrayList<String> playerCountries, int attackerArmies, String defendingcountry, String attackingcountry, int armiesToMove) {
        for (int i = 0; i < countriescopy.size(); i++) {
            if (isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
                mapInstance.countries.get(i).setArmies(armiesToMove);
                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                break;
            }
        }

        for (int i = 0; i < countriescopy.size(); i++) {
            if (isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
                mapInstance.countries.get(i).setArmies(attackerArmies - armiesToMove);
                mapInstance.countries.get(i).setBelongsTo(player.getPlayerName());
                playerCountries.add(defendingcountry);
                break;

            }
        }

        return true;
    }

    /**
     * Method to get the defender number of roll of rice depending on the number of armies he owns in the country
     * @param defenderArmies number of defending armies
     * @return number of roll of dice for defender
     */
    public int getDefenderDice(int defenderArmies) {
        int defenderDice;
        if (defenderArmies == 0) {
            defenderDice = 1;
        } else {
            defenderDice = 2;
        }
        return defenderDice;
    }

    /**
     * Method to get the attacker number of roll of rice depending on the number of armies he owns in the country
     * @param attackerArmies number of attacking armies
     * @return number of roll of dice for attacker
     */
    public int getAttackerDice(int attackerArmies,boolean attackmode) {
        
    	if(!attackmode)
    	{
    	int attackerDice;
        Dice dice = new Dice();
        if (attackerArmies >= 3) {
            System.out.println("You Can Roll Atmost 3 dice");
            dice.roll(1,3);
            attackerDice = scanner.nextInt();
        } else if (attackerArmies == 2) {
            System.out.println("You Can Roll Atmost 2 dice");
            dice.roll(1,2);
            attackerDice = scanner.nextInt();
        } else {
            System.out.println("You Can only Roll the Dice once");
            attackerDice = 1;
        }
        return attackerDice;
    	}
    	else
    	{
    		if(attackerArmies>=3)
    			return 3;
    		else if(attackerArmies==2)
    			return 2;
    		else
    			return 1;
    	}
    }


    /**
     * Method to display all the cards the player owns
     * @param player instance of player
     */
    public void displayPlayerCards(Player player) {
        int cardCount = player.getInfantryCount() + player.getCavalryCount() + player.getCannonCount();
        if (cardCount > 0) {
            System.out.print("\n**Cards**\nPlayer has " + cardCount + " card(s)\nThey are: \n");
            for (int i = 1; i <= player.infantryCount; i++) {
                System.out.println(i + "- Infantry Card");
            }
            for (int i = 1; i <= player.cavalryCount; i++) {
                System.out.println(i + "- Cavalry Card");
            }
            for (int i = 1; i <= player.cannonCount; i++) {
                System.out.println(i + "- Cannon Card");
            }
            System.out.println("\n");
        } else {
            System.out.print("\n**Player has zero cards!**\n");
        }


    }


    /**
     * This method implements the fortification phase of game
     * @param player instance of player
     * @param mapInstance instance of map
     */
    public void fortification(Player player, RiskMap mapInstance) {
        map = mapInstance;
        ArrayList<String> playerCountries = player.getCountries();
        ArrayList<Country> countries = mapInstance.getCountries();
        System.out.println("\nCountries you own: " + playerCountries);
        for (Country country : countries) {
            if (playerCountries.contains(country.getCountryName())) {
                System.out.println("Country " + country.getCountryName() + " has " + country.getArmies() + " armies");
            }
        }
        System.out.println("Enter the country from which you want to move armies from or Enter 'exit' to not move armies: ");
        boolean playerFromCountryFlag = false;
        while (!playerFromCountryFlag) {
            String moveArmiesFrom = scanner.next();
            if (!moveArmiesFrom.toLowerCase().equals("exit")) {
                if (noOfArmiesInCountry(moveArmiesFrom, null) > 0) {
                    if (playerCountries.contains(moveArmiesFrom)) {
                        if (containsCountriesAsNeighbours(moveArmiesFrom, player, null)) {
                            playerFromCountryFlag = true;
                            LinkedHashMap<String, ArrayList<String>> allCountriesWithNeighbours = mapInstance.getAdjCountries();
                            ArrayList<String> selectedCountryNeighbours = allCountriesWithNeighbours.get(moveArmiesFrom);
                            System.out.println("Neighbours to " + moveArmiesFrom + " : " + selectedCountryNeighbours);
                            System.out.println("Enter the country to which you want to move armies to: ");
                            boolean playerToCountryFlag = false;
                            while (!playerToCountryFlag) {
                                String moveArmiesTo = scanner.next();
                                if (selectedCountryNeighbours.contains(moveArmiesTo)) {
                                    if (playerCountries.contains(moveArmiesTo)) {
                                        playerToCountryFlag = true;
                                        int noOfArmiesFrom = noOfArmiesInCountry(moveArmiesFrom, null);
                                        System.out.println("No of armies in your country " + moveArmiesFrom + " : " + noOfArmiesFrom);
                                        System.out.println("Enter the number of armies to move: ");
                                        boolean noOfArmiesFlag = false;
                                        while (!noOfArmiesFlag) {
                                            if (scanner.hasNextInt()) {
                                                int noOfArmiesToMove = scanner.nextInt();
                                                if (noOfArmiesToMove <= noOfArmiesFrom && noOfArmiesToMove > 0) {
                                                    noOfArmiesFlag = true;
                                                    System.out.println("** Before moving armies **\n");
                                                    System.out.println("No of armies in country " + moveArmiesFrom + " (from): " + noOfArmiesInCountry(moveArmiesFrom, null));
                                                    System.out.println("No of armies in country " + moveArmiesTo + " (to): " + noOfArmiesInCountry(moveArmiesTo, null));
                                                    for (Country country : countries) {
                                                        if (isCountryNameEquals(moveArmiesFrom, country)) {
                                                            country.setArmies(country.getArmies() - noOfArmiesToMove);
                                                        }
                                                        if (isCountryNameEquals(moveArmiesTo, country)) {
                                                            country.setArmies(country.getArmies() + noOfArmiesToMove);
                                                        }
                                                    }
                                                    System.out.println("** After moving armies **\n");
                                                    System.out.println("No of armies in country " + moveArmiesFrom + " (from): " + noOfArmiesInCountry(moveArmiesFrom, null));
                                                    System.out.println("No of armies in country " + moveArmiesTo + " (to): " + noOfArmiesInCountry(moveArmiesTo, null));

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
                playerFromCountryFlag = true;
            }
        }
        removePlayerIfZeroCountriesOwned(player);

    }

    /**
     * Method to remove player if they don't own at least one country in the map
     * @param player instance of player
     * @return true if the player is removed
     */
    public boolean removePlayerIfZeroCountriesOwned(Player player) {
        if (!doesPlayerOwnAtLeastOneCountry(player)) {
            players.remove(player);
        }
        return true;
    }

    /**
     * Calculate the number of armies that a country has at a particular time in the game
     *
     * @param countryName name of the country
     * @param map TODO
     * @return number of armies in the country
     */
    public int noOfArmiesInCountry(String countryName, RiskMap map) {

        ArrayList<Country> countries = map.getCountries();
        for (Country country : countries) {
            if (isCountryNameEquals(countryName, country)) {
                return country.getArmies();
            }
        }
        return -1;
    }

    /**
     * Refactored method- To find if country name is equal to the country instance's country name
     * @param countryName name of the country
     * @param country country instance
     * @return true if the string is equal to the name of the country, else false
     */
    public boolean isCountryNameEquals(String countryName, Country country) {
        return country.getCountryName().equals(countryName);
    }

    /**
     * Checks if player contains neighbor countries that are owned by a different player
     *
     * @param countryName name of country
     * @param player      player instance
     * @param map TODO
     * @return true if at least one neighbour country is owned by a different player, else false
     */
    public boolean containsCountriesAsNeighbours(String countryName, Player player, RiskMap map) {
        LinkedHashMap<String, ArrayList<String>> allCountriesWithNeighbours = map.getAdjCountries();
        ArrayList<String> neighbours = allCountriesWithNeighbours.get(countryName);
        for (String neighbour : neighbours) {
            if (player.getCountries().contains(neighbour)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns a random card to winner of a round in attack phase
     * @param winner instance of player who won a round in attack phase
     */
    public void assignCardsToWinner(Player winner) {
        ArrayList<Card> cards = winner.cards;
        int cardTypeValue = Util.randInt(1, 3);
        String cardName = Card.getNameByTypeNumber(cardTypeValue);
        switch (cardTypeValue) {
            case 1:
                winner.setInfantryCount(winner.getInfantryCount() + 1);
                break;
            case 2:
                winner.setCavalryCount(winner.getCavalryCount() + 1);
                break;
            case 3:
                winner.setCannonCount(winner.getCannonCount() + 1);
                break;
        }
        System.out.println("**Assigning " + cardName + " Card to the Winner- " + winner.getPlayerName() + " **");
        cards.add(new Card(cardName, cardTypeValue));
        winner.cards = cards;
        displayPlayerCards(winner);

    }

    /**
     * Method to return the instance of player by searching by passing a name
     * @param name name
     * @return instance of player
     */
    private Player getPlayerInstanceByName(String name) {
        for (Player player : players) {
            if (player.getPlayerName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Method to check if player contains at least one country in the whole map
     * @param player instance of player
     * @return true if player contains at least one country in the map, else false
     */
    private boolean doesPlayerOwnAtLeastOneCountry(Player player) {
        return player.getCountries().size() != 0;
    }
    
    /**
	 * This method will execute the attack method from the PlayerStrategy interface
	 * 
	 * @param player player object
	 */
	public void executeAttack(Player player, Player mapPlayer) {
		this.playerStrategy.attack(player, mapPlayer);
	}

	/**
	 * This method will execute the reinforce method from the PlayerStrategy
	 * interface
	 * 
	 * @param player object of Player class
	 * @throws Exception if there is an error
	 */
	public void executeReinforcement(Player player, RiskMap map) throws Exception {
		this.playerStrategy.reinforcement(player, map);
	}

	/**
	 * This method will execute the fortification method from the PlayerStrategy
	 * interface
	 * 
	 * @param player object of Player class
	 */
	public void executeFortification(Player player) {
		this.playerStrategy.fortification(player, map);
	}


}
