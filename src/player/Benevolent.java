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
 * Class benevolent defies the benevolent strategy of a player
 * @author Prashanthi
 * @version 1.2
 * @since 1.2
 */

public class Benevolent extends Observable implements Strategy {

	/**
	 * map is an object of RiskMap
	 */
	RiskMap map;

	/**
	 * player is an object of Player class
	 */
	Player player;


	ArrayList<Player> players;

	/**
	 * Reinforcement phase for benevolent startegy
	 *
	 * @param player is an object of Player
	 * @param map is an object of RiskMap
	 * @return Player object
	 * @throws Exception when an exception occurs
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
	 * Attack module for benevolent strategy
	 *
	 * @param player is an object of Player
	 * @param mapPlayer is an object of Player
	 * @return object of Player
	 */
	@Override
	public Player attack(Player player, Player mapPlayer) {
		System.out.println("Player armies will be doubled in all the countries they own");
		this.map = mapPlayer.map;
		this.player = player;
		this.players = mapPlayer.players;
		int playerArmies = player.getArmies();
		ArrayList<Country> countries = map.getCountries();
		System.out.println("\n***** Benevolent Player- I don't attack *****\n");
		updateIfPlayerWon();
		return null;
	}


	/**
	 * Fortification phase for benevolent strategy
	 *
	 * @param player is an obkject of Player
	 * @param map is an object of RiskMap
	 * @return object of Player
	 */
	@Override
	public Player fortification(Player player, RiskMap map) {
		// find country with the most armies and
		this.map = map;
		this.player = player;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		int c = 0;
		
		System.out.println("\nCountries you own: " + playerCountries);
		boolean canForfeit = false;
		// TODO
		// iterate tru player countries and find neighbours
		// check if neighbouts are his and is armies are >0
		// increment counter

		for (String playerCountryName : playerCountries) {
			Country playerCountry = getPlayerCountry(playerCountryName, this.map.getCountries());
			ArrayList<String> neighbours = this.map.adjCountries.get(playerCountryName);
			for (String neighbour : neighbours) {
				if (getPlayerCountry(neighbour, this.map.getCountries()).getArmies() > 0
						&& isNeighbourCountryOwnedByPlayer(neighbour)) {
					c++;
				}
			}
			if (c > 2) {
				break;
			}

		}
		if (c >= 2) {
			canForfeit = true;
		}
		if (canForfeit) {
			// sort player countries in ascending order
			ArrayList<String> sortedPlayerCountries = getSortedCountryListBasedOnArmy(playerCountries);
			String destinationCountryName = "";
			String sourceCountryName = "";
			boolean forfeit = false;
			System.out.println("\nCountries you own: " + playerCountries);
			for (Country country : countries) {
				if (playerCountries.contains(country.getCountryName())) {
					System.out
							.println("Country " + country.getCountryName() + " has " + country.getArmies() + " armies");
				}
			}
			for (int i = 0; i < sortedPlayerCountries.size() - 1; i++) {
				destinationCountryName = sortedPlayerCountries.get(i);
				Country desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
				sourceCountryName = getSourceCountry(destinationCountryName);
				if (sourceCountryName != "" && desCountry.getArmies() > -1) {
					Country sourceCountry = getPlayerCountry(sourceCountryName, this.map.countries);
					int toSetForSourceCountry = 0;
					int toSetForDesCountry = sourceCountry.getArmies() + desCountry.getArmies();
					System.out.println("** Before moving armies **\n");
					System.out.println(
							"No of armies in country " + sourceCountryName + " (from): " + sourceCountry.getArmies());
					System.out.println(
							"No of armies in country " + destinationCountryName + " (to): " + desCountry.getArmies());
					forfeit(destinationCountryName, sourceCountryName, toSetForDesCountry, toSetForSourceCountry);
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
				System.out.println("\n## No Valid move for the benevolent player ##\n");
			}
		}else {
			System.out.println("\n## No Valid move for the benevolent player ##\n");
			
		}
		// do the opposite for benevolent
		return null;
	}

	/**
	 * Method to deploy armies in benevolent strategy
	 *
	 * @param player is an object of Player
	 * @param map is an object of RiskMap
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
		int minArmyCount = Integer.MAX_VALUE;
		Country countryToPlaceArmies = null;
		for (String playerCountry : playerCountries) {
			Country armyCountry = getPlayerCountry(playerCountry, countries);
			if (armyCountry.getArmies() < minArmyCount) {
				maxArmiesCountry = playerCountry;
				minArmyCount = armyCountry.getArmies();
				countryToPlaceArmies = armyCountry;
			}
		}
		if (countryToPlaceArmies != null) {
			int countryNoArmies = countryToPlaceArmies.getArmies();
			countryToPlaceArmies.setArmies(countryNoArmies + playerArmies);
			System.out.println("You have successfully placed armies and reinforced your weakest country= "
					+ countryToPlaceArmies.getCountryName());
			System.out.println(map.getCountries());
			player.setArmies(0);
		} else {
			System.out.println("No Valid Reinforcement move!");
		}

		return null;
	}


	/**
	 * Method to return player country
	 *
	 * @param playerCountry is a String variable
	 * @param mapCountries is an arraylist of Country
	 * @return return an object of Country
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
	 * Method to return country list based on armies
	 *
	 * @param playerCountries is an arraylist of countries
	 * @return sortedCountryList list of countries
	 */
	private ArrayList<String> getSortedCountryListBasedOnArmy(ArrayList<String> playerCountries) {

		ArrayList<String> sortedCountryList = playerCountries;

		for (int i = 0; i < sortedCountryList.size(); i++) {
			int iarmies = getPlayerCountry(sortedCountryList.get(i), this.map.getCountries()).getArmies();
			for (int j = i + 1; j < sortedCountryList.size(); j++) {
				int jarmies = getPlayerCountry(sortedCountryList.get(j), this.map.getCountries()).getArmies();
				if (jarmies < iarmies) {
					Collections.swap(sortedCountryList, i, j);
				}
			}
		}

		return sortedCountryList;
	}

	/**
	 * Method to return source country
	 *
	 * @param destinationCountry is a string
	 * @return returns the source country of a player
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
	 * Method to forfeit the game
	 *
	 * @param sourceCountry is a string variable
	 * @param destinationCountry is a string variable
	 * @param sourceArmies is an integer value denoting the number of armies
	 * @param destinationArmy is an integer value denoting the number of armies
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
	 * Method to check whether the neighbouring country is owned by a player
	 *
	 * @param neighbourCountryName is a string variable
	 * @return returns a boolean value
	 */
	private boolean isNeighbourCountryOwnedByPlayer(String neighbourCountryName) {
		if (this.player.getCountries().contains(neighbourCountryName)) {
			return true;
		}
		return false;
	}

	private void updateIfPlayerWon() {
		if (this.player.getCountries().size() == this.map.countries.size()) {
			this.player.setWinner(true);
		}
	}
}
