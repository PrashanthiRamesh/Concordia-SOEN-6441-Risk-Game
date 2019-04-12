package player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Scanner;

import model.Country;
import model.Player;
import model.RiskMap;
import util.Util;
import view.CardExchange;
/**
 * Class Human defines the strategy for Human player
 *
 * @author Manasa
 * @version 1.2
 * @since 1.2
 */

public class Human extends Observable implements Strategy {
	
	RiskMap map;
	
	/**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

	/**
	 * Reinforcement phase for human strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
	 * @return null
	 * @throws Exception when an exception occurs
	 */
	@Override
	public Player reinforcement(Player player, RiskMap map) throws Exception {
	    CardExchange cardExchange=new CardExchange(player);
		this.addObserver(cardExchange);
		this.map=map;
        player.setReinforcementArmies(player, this.map.getCountries());
        player.setArmiesForContinentsControlled(player, player.getCountries(), player.getArmies());
        setChanged();
        notifyObservers(this);
        System.out.println("\nAfter Card Exchange View");
        player.displayPlayerCards(player);
        System.out.println("Player armies: " + player.getArmies());
        deployArmies(player, this.map);
        this.deleteObserver(cardExchange);
		return null;
	}

	/**
	 * Attack phase for human strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param mapPlayer We pass the mapPlayer object
	 * @return null
	 */
	@Override
	public Player attack(Player player, Player mapPlayer) {
		 	this.map=mapPlayer.map;
	        int attackerWinCount=0;
	        attackcontinue:
	        while (true) {
	            ArrayList<Country> countriescopy = mapPlayer.map.getCountries();
	            Scanner scan = new Scanner(System.in);
	            System.out.println("Select Player to Attack From the List");
	            int count = 0;
	            for (int i = 0; i < mapPlayer.players.size(); i++) {
	                if (!mapPlayer.players.get(i).getPlayerName().equals(player.getPlayerName())) {
	                    System.out.println(mapPlayer.players.get(i).getPlayerName());
	                }
	            }
	            String attacker = scan.next();

	            System.out.println("The Countries You Can Attack of the Player " + attacker + " are :");
	            ArrayList<String> playerCountries = player.getCountries();
	            ArrayList<String> store = new ArrayList<String>();
	            for (int i = 0; i < playerCountries.size(); i++) {
	                ArrayList<String> adjcont = mapPlayer.map.adjCountries.get(playerCountries.get(i));


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
	                    if (player.isCountryNameEquals(playerCountries.get(i), countriescopy.get(p))) {
	                        System.out.print(playerCountries.get(i) + "-" + countriescopy.get(p).getArmies());
	                    }
	                }
	                System.out.print("------> [");
	                for (int m = 0; m < store.size(); m++) {
	                    for (int p = 0; p < countriescopy.size(); p++) {
	                        if (player.isCountryNameEquals(store.get(m), countriescopy.get(p))) {
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
	                for (int i = 0; i < mapPlayer.map.adjCountries.get(attackingcountry).size(); i++) {
	                    if (mapPlayer.map.adjCountries.get(attackingcountry).get(i).equals(defendingcountry)) {

	                        check = true;
	                    }

	                }

	                if (check == false) {
	                    System.out.println("It is not a Neighbouring country Please check and enter again");
	                    continue ZeroArmy;
	                }
	                for (int i = 0; i < countriescopy.size(); i++) {
	                    if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
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
	                if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
	                    defenderArmies = countriescopy.get(i).getArmies();
	                }
	            }

	        	System.out.println("Select 1 for allout mode, 2 for manual mode");
            	
            	int alloutch=scan.nextInt();
            	
            	boolean tempBool=false;
            	
            	if(alloutch==1)
            		tempBool=true;
	            
	            while (true) {

	            	
	            
	            	
	                defenderDice = player.getDefenderDice(defenderArmies);
	                attackerDice = player.getAttackerDice(attackerArmies,tempBool);
            
	                Integer[] attackerRandomNumbers = new Integer[attackerDice];
	                Integer[] defenderRandomNumbers = new Integer[defenderDice];

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
	                    for (int i = 0; i < countriescopy.size(); i++) {
	                        if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
	                            mapPlayer.map.getCountries().get(i).setArmies(defenderArmies);


	                        }
	                    }

	                    for (int i = 0; i < countriescopy.size(); i++) {
	                        if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
	                            mapPlayer.map.countries.get(i).setArmies(0);

	                        }
	                    }

	                    break;
	                } else if (defenderArmies == -1) {
	                    System.out.println("You Won");
	                    attackerArmies--;

	                    if (attackerArmies > 1) {
	                        System.out.println("You Have " + attackerArmies + " Left");
	                        System.out.println("Enter the Number of armies you want to leave behind from the territory you came");
	                        int armiesToLeaveBehind = scan.nextInt();
	                        player.moveAfterConquering(player, mapPlayer.map, countriescopy, playerCountries, attackerArmies, defendingcountry, attackingcountry, armiesToLeaveBehind);

	                    } else if (attackerArmies == 1) {
	                        for (int i = 0; i < countriescopy.size(); i++) {
	                            if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
	                                mapPlayer.map.countries.get(i).setArmies(1);
	                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
	                            }
	                        }

	                        for (int i = 0; i < countriescopy.size(); i++) {
	                            if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
	                                mapPlayer.map.countries.get(i).setArmies(0);
	                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());

	                            }
	                        }
	                    } else {
	                        for (int i = 0; i < countriescopy.size(); i++) {
	                            if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
	                                mapPlayer.map.countries.get(i).setArmies(0);
	                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
	                            }
	                        }
	                        for (int i = 0; i < countriescopy.size(); i++) {
	                            if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
	                                mapPlayer.map.countries.get(i).setArmies(0);
	                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
	                            }
	                        }
	                    }

	                    attackerWinCount++;
	                    break;
	                }
	              


	            }

	            int armiesLeft = 0;
	            for (int i = 0; i < countriescopy.size(); i++) {
	                if (countriescopy.get(i).getBelongsTo().equals(player.getPlayerName())) {
	                    armiesLeft++;
	                }
	            }
	            if (countriescopy.size() == armiesLeft) {
	                System.out.println("You Won Please the collect the reward from the TA :)");
	                System.exit(0);
	            }

	            armiesLeft = 0;
	            armiesLeft = player.getArmiesLeftForPlayer(player, countriescopy, armiesLeft);
	            if (armiesLeft < 1) {
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
	        if(attackerWinCount>0) {
	        	player.assignCardsToWinner(player);
	        }

		return null;
	}

	/**
	 * Fortification phase for the human strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object in which map is loaded
	 * @return null
	 */
	@Override
	public Player fortification(Player player, RiskMap map) {
		    this.map = map;
	        ArrayList<String> playerCountries = player.getCountries();
	        ArrayList<Country> countries = this.map.getCountries();
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
	                if (player.noOfArmiesInCountry(moveArmiesFrom, this.map) > 0) {
	                    if (playerCountries.contains(moveArmiesFrom)) {
	                        if (player.containsCountriesAsNeighbours(moveArmiesFrom, player, this.map)) {
	                            playerFromCountryFlag = true;
	                            LinkedHashMap<String, ArrayList<String>> allCountriesWithNeighbours = this.map.getAdjCountries();
	                            ArrayList<String> selectedCountryNeighbours = allCountriesWithNeighbours.get(moveArmiesFrom);
	                            System.out.println("Neighbours to " + moveArmiesFrom + " : " + selectedCountryNeighbours);
	                            System.out.println("Enter the country to which you want to move armies to: ");
	                            boolean playerToCountryFlag = false;
	                            while (!playerToCountryFlag) {
	                                String moveArmiesTo = scanner.next();
	                                if (selectedCountryNeighbours.contains(moveArmiesTo)) {
	                                    if (playerCountries.contains(moveArmiesTo)) {
	                                        playerToCountryFlag = true;
	                                        int noOfArmiesFrom = player.noOfArmiesInCountry(moveArmiesFrom, this.map);
	                                        System.out.println("No of armies in your country " + moveArmiesFrom + " : " + noOfArmiesFrom);
	                                        System.out.println("Enter the number of armies to move: ");
	                                        boolean noOfArmiesFlag = false;
	                                        while (!noOfArmiesFlag) {
	                                            if (scanner.hasNextInt()) {
	                                                int noOfArmiesToMove = scanner.nextInt();
	                                                if (noOfArmiesToMove <= noOfArmiesFrom && noOfArmiesToMove > 0) {
	                                                    noOfArmiesFlag = true;
	                                                    System.out.println("** Before moving armies **\n");
	                                                    System.out.println("No of armies in country " + moveArmiesFrom + " (from): " + player.noOfArmiesInCountry(moveArmiesFrom, this.map));
	                                                    System.out.println("No of armies in country " + moveArmiesTo + " (to): " + player.noOfArmiesInCountry(moveArmiesTo, this.map));
	                                                    for (Country country : countries) {
	                                                        if (player.isCountryNameEquals(moveArmiesFrom, country)) {
	                                                            country.setArmies(country.getArmies() - noOfArmiesToMove);
	                                                        }
	                                                        if (player.isCountryNameEquals(moveArmiesTo, country)) {
	                                                            country.setArmies(country.getArmies() + noOfArmiesToMove);
	                                                        }
	                                                    }
	                                                    System.out.println("** After moving armies **\n");
	                                                    System.out.println("No of armies in country " + moveArmiesFrom + " (from): " + player.noOfArmiesInCountry(moveArmiesFrom, this.map));
	                                                    System.out.println("No of armies in country " + moveArmiesTo + " (to): " + player.noOfArmiesInCountry(moveArmiesTo, this.map));

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
	        player.removePlayerIfZeroCountriesOwned(player);
		return null;
	}

	/**
	 * Method where the armies are deployed for the player's countries
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object in which map is loaded
	 * @return null
	 */
	@Override
	public Player deployArmies(Player player, RiskMap map) {
		    this.map=map;
	        int playerArmies = player.getArmies();
	        ArrayList<String> playerCountries = player.getCountries();
	        System.out.println("\nNo of armies to place: " + playerArmies);
	        System.out.println("Countries you own: " + playerCountries);
	        while (playerArmies > 0) {
	            System.out.println("Enter the name of the country to place armies in: ");
	            boolean countryNameFlag = false;
	            while (!countryNameFlag) {
	                String countryName = scanner.next();
	                if (playerCountries.contains(countryName)) {
	                    countryNameFlag = true;
	                    System.out.println("Enter the number of armies you want to place in this country: ");
	                    boolean noOfArmiesFlag = false;
	                    while (!noOfArmiesFlag) {
	                        if (scanner.hasNextInt()) {
	                            int noOfArmiesToPlace = scanner.nextInt();
	                            if (noOfArmiesToPlace <= playerArmies && noOfArmiesToPlace > 0) {
	                                noOfArmiesFlag = true;
	                                System.out.println("Placing " + noOfArmiesToPlace + " armies in country " + countryName);
	                                ArrayList<Country> countries = map.getCountries();
	                                for (Country country : countries) {
	                                    if ((new Player()).isCountryNameEquals(countryName, country)) {
	                                        int countryNoArmies = country.getArmies();
	                                        country.setArmies(countryNoArmies + noOfArmiesToPlace);
	                                        System.out.println("Placed Successfully! Number of Armies in Country " + countryName + " is " + country.getArmies() + "\n");
	                                    }
	                                }
	                                playerArmies = playerArmies - noOfArmiesToPlace;
	                                player.setArmies(playerArmies);
	                                System.out.println("Remaining armies to place: " + playerArmies);
	                            } else {
	                                System.out.println("Ops Invalid! You don't have enough armies: Armies you own: " + playerArmies + "\nEnter again: ");
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
		return null;
	}



}
