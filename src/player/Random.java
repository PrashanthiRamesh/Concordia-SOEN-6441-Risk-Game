package player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;

import model.Country;
import model.Player;
import model.RiskMap;
import util.Util;
import view.ComputerCardExchange;

/**
 * Class Random defines the Random strategy of the player
 * @author Mutesham
 * @author Manasa
 * @version 1.2
 * @since 1.2
 */
public class Random extends Observable implements Strategy {

	RiskMap map;

	Player player;

	ArrayList<Player> players;

	/**
	 * Reinforcement method for Random strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
	 * @return null
	 * @throws Exception if an exception occurred
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
	 * Attack phase method for Random strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param mapPlayer We pass the mapPlayer object
	 * @return null
	 */
	@Override
	public Player attack(Player player, Player mapPlayer) {
		System.out.println("Player will always attack with a random country until it cannot attack anymore");
		this.map = mapPlayer.map;
		this.player = player;
		this.players = mapPlayer.players;
		int playerArmies = player.getArmies();
		ArrayList<Country> countries = map.getCountries();

		// get random country
		int attackerWinCount=0;
		boolean randomAttackFlag=false;
		while (!randomAttackFlag) {
			ArrayList<String> playerCountries = player.getCountries();
			int playerCountriesSize = playerCountries.size();
			String randomCountryName = playerCountries.get(Util.randInt(0, playerCountriesSize - 1));
			Country randomCountry = getPlayerCountry(randomCountryName, mapPlayer.map.getCountries());
			
			ArrayList<String> neighbours = this.map.adjCountries.get(randomCountryName);
			ArrayList<String> randomCountryNeighbours = new ArrayList<String>();
			for (String neighbour : neighbours) {
				if (!isNeighbourCountryOwnedByPlayer(neighbour)) {
					randomCountryNeighbours.add(neighbour);
				}
			}
			for (String strongCountryNeighbour : randomCountryNeighbours) {
				int defenderArmies = (getPlayerCountry(strongCountryNeighbour, this.map.countries)).getArmies();
				int attackerArmies = (randomCountry).getArmies();

				ArrayList<Country> countriescopy = this.map.getCountries();
				int defenderDice = player.getDefenderDice(defenderArmies);
				int attackerDice = player.getAttackerDice(attackerArmies, true);

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
						if (player.isCountryNameEquals(strongCountryNeighbour, countriescopy.get(i))) {
							mapPlayer.map.getCountries().get(i).setArmies(defenderArmies);

						}
					}

					for (int i = 0; i < countriescopy.size(); i++) {
						if (player.isCountryNameEquals(randomCountry.getCountryName(), countriescopy.get(i))) {
							mapPlayer.map.countries.get(i).setArmies(0);

						}
					}
					randomAttackFlag=true;
					break;
				} else if (defenderArmies == -1) {
					System.out.println("You Won");
					attackerArmies--;
					attackerWinCount++;
					randomAttackFlag=true;
					break;
				}
			}
			
			if(randomAttackFlag) {
				break;
			}
		}
		if (attackerWinCount > 0) {
			player.assignCardsToWinner(player);
		}

		
		return null;
	}

	/**
	 * Fortification phase method for Random strategy
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass th map object where the map is loaded
	 * @return null
	 */
	@Override
	public Player fortification(Player player, RiskMap map) {
		// iterate tru get player countries
		// if there are neighbours and if they are the players's and >0
		// move from neighbour to desc
		this.map = map;
		this.player = player;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		String sourceCountryName = "";
		boolean forfeit = false;
		System.out.println("\nCountries you own: " + playerCountries);
		boolean canForfeit = false;
		int c = 0;
		//TODO
		//iterate tru player countries and find neighbours 
		// check if neighbouts are his and is armies are >0 
		//increment counter
		
		for(String playerCountryName:playerCountries) {
			Country playerCountry=getPlayerCountry(playerCountryName, this.map.getCountries());
			ArrayList<String> neighbours = this.map.adjCountries.get(playerCountryName);
			for(String neighbour:neighbours) {
				if(getPlayerCountry(neighbour, this.map.getCountries()).getArmies()>0 && isNeighbourCountryOwnedByPlayer(neighbour)) {
					c++;
				}
			}
			if(c>2) {
				break;
			}
			
		}
		if (c >= 2) {
			canForfeit = true;
		}
		if (canForfeit) {
			while (!forfeit) {
				int randomDescCountryIndex = Util.randInt(0, playerCountries.size() - 1);
				String destinationCountryName = playerCountries.get(randomDescCountryIndex);
				for (int i = 0; i < playerCountries.size(); i++) {
					sourceCountryName = getSourceCountry(destinationCountryName);
					Country desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
					if (sourceCountryName != "" && desCountry.getArmies() > -1) {
						Country sourceCountry = getPlayerCountry(sourceCountryName, this.map.countries);
						int toSetForSourceCountry = 0;
						int toSetForDesCountry = sourceCountry.getArmies() + desCountry.getArmies();
						System.out.println("** Before moving armies **\n");
						System.out.println("No of armies in country " + sourceCountryName + " (from): "
								+ sourceCountry.getArmies());
						System.out.println("No of armies in country " + destinationCountryName + " (to): "
								+ desCountry.getArmies());
						forfeit(destinationCountryName, sourceCountryName, toSetForDesCountry, toSetForSourceCountry);
						sourceCountry = getPlayerCountry(sourceCountryName, this.map.countries);
						desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
						System.out.println("** After moving armies **\n");
						System.out.println("No of armies in country " + sourceCountryName + " (from): "
								+ sourceCountry.getArmies());
						System.out.println("No of armies in country " + destinationCountryName + " (to): "
								+ desCountry.getArmies());
						forfeit = true;
						break;
					}
				}
				if (forfeit) {
					break;
				}
			}
		} else {
			System.out.println("\n## No Valid move for the random player ##\n");
		}

		if (!forfeit) {
			System.out.println("\n## No Valid move for the random player ##\n");
		}

		return null;
	}

	/**
	 * Method is foe deploying the armies in the players countries
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
	 * @return null
	 */
	@Override
	public Player deployArmies(Player player, RiskMap map) {
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();

		if (playerCountries.size() >= 1) {
			int randomCountryIndex = Util.randInt(0, playerCountries.size() - 1);
			Country countryToPlaceArmies = getPlayerCountry(playerCountries.get(randomCountryIndex),
					this.map.countries);
			int countryNoArmies = countryToPlaceArmies.getArmies();
			countryToPlaceArmies.setArmies(countryNoArmies + playerArmies);
			System.out.println("You have successfully placed armies and reinforced a random country= "
					+ countryToPlaceArmies.getCountryName());
			System.out.println(map.getCountries());
			player.setArmies(0);
		} else {
			System.out.println("No valid reinforcement");
		}

		return null;
	}


	/**
	 * Method to get the country of the player
	 * @param playerCountry We pass playerCountry as a string
	 * @param mapCountries List of countries in map
	 * @return returnCountry returning the country name
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
	 * Method to check the neighbouring country owned by the player
	 * @param neighbourCountryName name of the neighbour country
	 * @return false boolean value false is returned if the player's country list does not contain the neighbouring country
	 */
	private boolean isNeighbourCountryOwnedByPlayer(String neighbourCountryName) {
		if (this.player.getCountries().contains(neighbourCountryName)) {
			return true;
		}
		return false;
	}


	/**
	 * Method to get the source country
	 * @param destinationCountry name of the destination country
	 * @return sourceCountry name of the source country is returned
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
	 * 	Method for forfeit technique
	 * @param sourceCountry name of the source country
	 * @param destinationCountry name of the destination country
	 * @param sourceArmies number of source country armies
	 * @param destinationArmy number of destination country armies
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

	private void updateIfPlayerWon() {
		if (this.player.getCountries().size() == this.map.countries.size()) {
			this.player.setWinner(true);
		}
	}

	private ArrayList<String> getSortedCountryListBasedOnArmy(ArrayList<String> playerCountries) {

		ArrayList<String> sortedCountryList = playerCountries;

		for (int i = 0; i < sortedCountryList.size(); i++) {
			int iarmies = getPlayerCountry(sortedCountryList.get(i), this.map.getCountries()).getArmies();
			for (int j = i + 1; j < sortedCountryList.size(); j++) {
				int jarmies = getPlayerCountry(sortedCountryList.get(j), this.map.getCountries()).getArmies();
				if (jarmies > iarmies) {
					Collections.swap(sortedCountryList, i, j);
				}
			}
		}

		return sortedCountryList;
	}
	
	

}
