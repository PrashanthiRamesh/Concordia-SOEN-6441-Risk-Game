package player;

import java.util.ArrayList;
import java.util.Observable;

import model.Country;
import model.Player;
import model.RiskMap;
import util.Util;
import view.ComputerCardExchange;

public class Random extends Observable implements Strategy {

	RiskMap map;

	Player player;

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

	@Override
	public Player attack(Player player, Player mapPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

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
		String sourceCountryName="";
		boolean forfeit = false;
		System.out.println("\nCountries you own: " + playerCountries);
		for (Country country : countries) {
			if (playerCountries.contains(country.getCountryName())) {
				System.out.println("Country " + country.getCountryName() + " has " + country.getArmies() + " armies");
			}
		}
		while(!forfeit){
			int randomDescCountryIndex=Util.randInt(0, playerCountries.size()-1);
			String destinationCountryName=playerCountries.get(randomDescCountryIndex);
			for(int i=0;i<playerCountries.size();i++) {
				sourceCountryName = getSourceCountry(destinationCountryName);
				Country desCountry = getPlayerCountry(destinationCountryName, this.map.countries);
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
			if(forfeit) {
				break;
			}
		}
		
		
		if (!forfeit) {
			System.out.println("\n## No Valid move for the random player ##\n");
		}

		return null;
	}

	@Override
	public Player deployArmies(Player player, RiskMap map) {
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();

		int randomCountryIndex = Util.randInt(0, playerCountries.size() - 1);
		Country countryToPlaceArmies = getPlayerCountry(playerCountries.get(randomCountryIndex), this.map.countries);
		int countryNoArmies = countryToPlaceArmies.getArmies();
		countryToPlaceArmies.setArmies(countryNoArmies + playerArmies);
		System.out.println("You have successfully placed armies and reinforced a random country= "
				+ countryToPlaceArmies.getCountryName());
		System.out.println(map.getCountries());
		player.setArmies(0);
		return null;
	}

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
	
	private boolean isNeighbourCountryOwnedByPlayer(String neighbourCountryName) {
		if (this.player.getCountries().contains(neighbourCountryName)) {
			return true;
		}
		return false;
	}
	
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

}
