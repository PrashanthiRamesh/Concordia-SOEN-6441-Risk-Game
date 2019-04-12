package player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import model.Country;
import model.Player;
import model.RiskMap;
import util.Util;
import view.CardExchange;
import view.ComputerCardExchange;


/**
 * Class Aggressive defines the strategy for aggressive player
 *
 * @author Maqsood
 * @version 1.2
 * @since 1.2
 */

public class Aggressive extends Observable implements Strategy {

	/**
	 * map is an object ofm RiskMap class
	 */
	RiskMap map;


	/**
	 * player is an object of Player class
	 */
	Player player;

	/**
	 * Reinforcement method for aggressive strategy
	 *
	 * @param player is an object of Player class
	 * @param map loads the map editor
	 * @return an object of Player class
	 * @throws  Exception if an exception occurs
	 */
	@Override
	public Player reinforcement(Player player, RiskMap map) throws Exception {
		ComputerCardExchange cardExchange = new ComputerCardExchange(player);
		this.addObserver(cardExchange);
		this.map = map;
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
	 * Attack method for aggressive strategy
	 * @param player is an object of Player class
	 * @param mapPlayer is an object of Player
	 * @return an object of Player
	 */
	@Override
	public Player attack(Player player, Player mapPlayer) {
//		this.map=mapPlayer.map;
//        int attackerWinCount=0;
//        attackcontinue:
//        while (true) {
//        	ArrayList<Country> countriescopy = mapPlayer.map.getCountries();
//        	 ArrayList<String> playerCountries = player.getCountries();
//            int attackerArmies = 0;
//            int defenderArmies = 0;
//            String defendingcountry;
//            String attackingcountry;
//            int attackerDice = 0;
//            int defenderDice = 0;
//            ZeroArmy:
//            while (true) {
//        	boolean tempBool=true;
//           
//        	while (true) {
//
//                defenderDice = player.getDefenderDice(defenderArmies);
//                attackerDice = player.getAttackerDice(attackerArmies,tempBool);
//        
//                Integer[] attackerRandomNumbers = new Integer[attackerDice];
//                Integer[] defenderRandomNumbers = new Integer[defenderDice];
//
//                for (int i = 0; i < attackerDice; i++) {
//                    attackerRandomNumbers[i] = Util.randInt(1, 6);
//                }
//                for (int i = 0; i < defenderDice; i++) {
//                    defenderRandomNumbers[i] = Util.randInt(1, 6);
//                }
//                Arrays.sort(attackerRandomNumbers, Collections.reverseOrder());
//                Arrays.sort(defenderRandomNumbers, Collections.reverseOrder());
//
//                System.out.println("Dice Rolled!!!");
//                System.out.println("Attacker's Dice");
//                for (int i = 0; i < attackerDice; i++) {
//                    System.out.print(attackerRandomNumbers[i] + " ");
//                }
//                System.out.println();
//                System.out.println("Defenders Dice");
//                for (int i = 0; i < defenderDice; i++) {
//                    System.out.print(defenderRandomNumbers[i] + " ");
//                }
//                System.out.println();
//                if (attackerDice < defenderDice) {
//                    for (int i = 0; i < attackerDice; i++) {
//                        if (attackerRandomNumbers[i] <= defenderRandomNumbers[i]) {
//                            attackerArmies--;
//                        } else {
//                            defenderArmies--;
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < defenderDice; i++) {
//                        if (attackerRandomNumbers[i] <= defenderRandomNumbers[i]) {
//                            attackerArmies--;
//                        } else {
//                            defenderArmies--;
//                        }
//                    }
//                }
//
//                if (attackerArmies == 0) {
//                    System.out.println("Defender has Won");
//                    for (int i = 0; i < countriescopy.size(); i++) {
//                        if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
//                            mapPlayer.map.getCountries().get(i).setArmies(defenderArmies);
//
//
//                        }
//                    }
//
//                    for (int i = 0; i < countriescopy.size(); i++) {
//                        if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
//                            mapPlayer.map.countries.get(i).setArmies(0);
//
//                        }
//                    }
//
//                    break;
//                } else if (defenderArmies == -1) {
//                    System.out.println("You Won");
//                    attackerArmies--;
//
//                    if (attackerArmies > 1) {
//                        System.out.println("You Have " + attackerArmies + " Left");
//                        System.out.println("Enter the Number of armies you want to leave behind from the territory you came");
//                        player.moveAfterConquering(player, mapPlayer.map, countriescopy, playerCountries, attackerArmies, defendingcountry, attackingcountry, attackerArmies);
//
//                    } else if (attackerArmies == 1) {
//                        for (int i = 0; i < countriescopy.size(); i++) {
//                            if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
//                                mapPlayer.map.countries.get(i).setArmies(1);
//                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
//                            }
//                        }
//
//                        for (int i = 0; i < countriescopy.size(); i++) {
//                            if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
//                                mapPlayer.map.countries.get(i).setArmies(0);
//                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
//
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < countriescopy.size(); i++) {
//                            if (player.isCountryNameEquals(defendingcountry, countriescopy.get(i))) {
//                                mapPlayer.map.countries.get(i).setArmies(0);
//                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
//                            }
//                        }
//                        for (int i = 0; i < countriescopy.size(); i++) {
//                            if (player.isCountryNameEquals(attackingcountry, countriescopy.get(i))) {
//                                mapPlayer.map.countries.get(i).setArmies(0);
//                                mapPlayer.map.countries.get(i).setBelongsTo(player.getPlayerName());
//                            }
//                        }
//                    }
//
//                    attackerWinCount++;
//                    break;
//                }
//              
//
//
//            }
//
//            int armiesLeft = 0;
//            for (int i = 0; i < countriescopy.size(); i++) {
//                if (countriescopy.get(i).getBelongsTo().equals(player.getPlayerName())) {
//                    armiesLeft++;
//                }
//            }
//            if (countriescopy.size() == armiesLeft) {
//                System.out.println("You Won Please the collect the reward from the TA :)");
//                System.exit(0);
//            }
//
//            armiesLeft = 0;
//            armiesLeft = player.getArmiesLeftForPlayer(player, countriescopy, armiesLeft);
//            if (armiesLeft < 1) {
//                System.out.println("Attack Not Possible No Armies Left");
//
//                break attackcontinue;
//            }
//
//            System.out.println("Do You Want to Continue the Attack \n Yes or No ?");
//
//            //if(there are neighbours that are not his then continue)
//            if (response.equals("Yes")) {
//                continue attackcontinue;
//            } else if (response.equals("No")) {
//                break attackcontinue;
//            }
//
//        }
//        if(attackerWinCount>0) {
//        	player.assignCardsToWinner(player);
//        }
//        }
	return null;
	}

	/***
	 * Fortification method for aggressive strategy
	 *
	 * @param player is an object of Player class
	 * @param map is an object of RiskMap
	 * @return an object of Player
	 */
	@Override
	public Player fortification(Player player, RiskMap map) {
		// find country with the most armies and
		this.map = map;
		this.player = player;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();

		// sort player countries in ascending order
		ArrayList<String> sortedPlayerCountries = getSortedCountryListBasedOnArmy(playerCountries);
		String destinationCountryName = "";
		String sourceCountryName = "";
		boolean forfeit = false;
		System.out.println("\nCountries you own: " + playerCountries);
		for (Country country : countries) {
			if (playerCountries.contains(country.getCountryName())) {
				System.out.println("Country " + country.getCountryName() + " has " + country.getArmies() + " armies");
			}
		}
		for (int i = 0; i < sortedPlayerCountries.size() - 1; i++) {
			destinationCountryName = sortedPlayerCountries.get(i);
			Country desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
			sourceCountryName = getSourceCountry(destinationCountryName);
			if (sourceCountryName != "" && desCountry.getArmies() > 0) {
				Country sourceCountry = getPlayerCountry(sourceCountryName, this.map.countries);
				int toSetForSourceCountry = 0;
				int toSetForDesCountry = sourceCountry.getArmies() + desCountry.getArmies();
				System.out.println("** Before moving armies **\n");
				System.out.println(
						"No of armies in country " + sourceCountryName + " (from): " + sourceCountry.getArmies());
				System.out.println(
						"No of armies in country " + destinationCountryName + " (to): " + desCountry.getArmies());
				forfeit(sourceCountryName, destinationCountryName, toSetForSourceCountry, toSetForDesCountry);
				sourceCountry = getPlayerCountry(sourceCountryName, this.map.countries);
				desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
				System.out.println("** After moving armies **\n");
				System.out.println(
						"No of armies in country " + sourceCountryName + " (from): " + sourceCountry.getArmies());
				System.out.println(
						"No of armies in country " + destinationCountryName + " (to): " + desCountry.getArmies());
				forfeit = true;
				break;
			}
		}
		if (!forfeit) {
			System.out.println("\n## No Valid move for the aggresive player ##\n");
		}

		// do the opposite for benevolent
		return null;
	}


	/**
	 * DeployArmies method for aggressive strategy
	 *
	 * @param player is an object of Player
	 * @param map loads the map editor
	 * @return an object of Player
	 */
	@Override
	public Player deployArmies(Player player, RiskMap map) {
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		// get maximum armies country
		String maxArmiesCountry = "";
		int maxArmyCount = Integer.MIN_VALUE;
		Country countryToPlaceArmies = null;
		for (String playerCountry : playerCountries) {
			Country armyCountry = getPlayerCountry(playerCountry, countries);
			if (armyCountry.getArmies() > maxArmyCount) {
				maxArmiesCountry = playerCountry;
				maxArmyCount = armyCountry.getArmies();
				countryToPlaceArmies = armyCountry;
			}
		}
		if(countryToPlaceArmies!=null) {
			int countryNoArmies = countryToPlaceArmies.getArmies();
			countryToPlaceArmies.setArmies(countryNoArmies + playerArmies);
			System.out.println("You have successfully placed armies and reinforced your strongest country= "+countryToPlaceArmies.getCountryName());
			System.out.println(map.getCountries());
			player.setArmies(0);
			return null;
		}else {
			System.out.println("No Valid Reinforcement move!");
		}
		return null;
		
	}

	/**
	 * Method to retrieve the country the player is associated with.
	 *
	 * @param playerCountry is an object of Country
	 * @param mapCountries is an arraylist for Country
	 * @return an object of Country
	 */
	private Country getPlayerCountry(String playerCountry, ArrayList<Country> mapCountries) {
		Country returnCountry = null;
		for (Country country : mapCountries) {
			if (country.getCountryName().equals(playerCountry)) {
				returnCountry = country;
				break;
			}
		}
		return returnCountry;
	}

	/**
	 * Method to retrieve the country based on army
	 *
	 * @param playerCountries is an arraylist of countries
	 * @return an arraylist of countries owned by player
	 */
	private ArrayList<String> getSortedCountryListBasedOnArmy(ArrayList<String> playerCountries) {

		ArrayList<String> sortedCountryList = playerCountries;

		for (int i = 0; i < sortedCountryList.size() ; i++) {
			int iarmies = getPlayerCountry(sortedCountryList.get(i), this.map.getCountries()).getArmies();
			for (int j = i+1; j < sortedCountryList.size() ; j++) {
				int jarmies = getPlayerCountry(sortedCountryList.get(j), this.map.getCountries()).getArmies();
				if (jarmies > iarmies) {
					Collections.swap(sortedCountryList, i, j );
				}
			}
		}

		return sortedCountryList;
	}

	/**
	 * Method to get source country
	 *
	 * @param destinationCountry is a String varible
	 * @return the source country of the player
	 */
	private String getSourceCountry(String destinationCountry) {
		String sourceCountry = "";
		ArrayList<String> neighbours = this.map.adjCountries.get(destinationCountry);
		boolean hasNeighbourArmies = false;
		for (int i = 0; i < neighbours.size() - 1; i++) {
			int sourceArmies = getPlayerCountry(neighbours.get(i), this.map.countries).getArmies();
			if (sourceArmies > 0 && isNeighbourCountryOwnedByPlayer(neighbours.get(i))) {
				sourceCountry = neighbours.get(i);
				for (int j = 0; j < neighbours.size() - i - 1; j++) {
					if (getPlayerCountry(neighbours.get(j + 1), this.map.countries).getArmies() > sourceArmies) {
						sourceCountry = neighbours.get(j + 1);
					}
				}
				break;
			}
		}
		return sourceCountry;
	}

	/**
	 * Method used to implement forfeit technique
	 * @param sourceCountry is a string variable
	 * @param destinationCountry is a string variable
	 * @param sourceArmies is an inetger value denoting the number of armies a player owns
	 * @param destinationArmy is an integer value denoting the number of armies a player moves
	 */
	private void forfeit(String sourceCountry, String destinationCountry, int sourceArmies, int destinationArmy) {
		int count = 0;
		for (Country country : this.map.countries) {
			if (country.getCountryName().equals(sourceCountry)) {
				country.setArmies(sourceArmies);
				count++;
			}
			if (country.getCountryName().equals(destinationCountry)) {
				country.setArmies(destinationArmy);
				count++;
			}
			if (count == 2) {
				break;
			}
		}
	}

	/**
	 * Method to check whether a neighbouring country is owned by a player
	 *
	 * @param neighbourCountryName is a String variable
	 * @return a boolean value denoting the ownership of neighbouring country
	 */
	private boolean isNeighbourCountryOwnedByPlayer(String neighbourCountryName) {
		if (this.player.getCountries().contains(neighbourCountryName)) {
			return true;
		}
		return false;
	}

}
