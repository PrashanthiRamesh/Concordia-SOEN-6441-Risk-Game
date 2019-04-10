package player;

import java.util.ArrayList;
import java.util.Observable;

import model.Country;
import model.Player;
import model.RiskMap;
import view.CardExchange;
import view.ComputerCardExchange;

public class Aggressive extends Observable implements Strategy {

	RiskMap map;

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
		//find country with the most armies and 
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		//get maximum armies country
		String maxArmiesCountry="";
		int maxArmyCount=Integer.MIN_VALUE;
		Country countryToPlaceArmies=null;
		for(String playerCountry: playerCountries) {
			Country armyCountry=getPlayerCountry(playerCountry, countries);
			if(armyCountry.getArmies()>maxArmyCount) {
				maxArmiesCountry=playerCountry;
				maxArmyCount=armyCountry.getArmies();
				countryToPlaceArmies=armyCountry;
			}
		}
		// find most among the neighours 
		// move armies from neighbour to army 
		return null;
	}

	@Override
	public Player deployArmies(Player player, RiskMap map) {
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		//get maximum armies country
		String maxArmiesCountry="";
		int maxArmyCount=Integer.MIN_VALUE;
		Country countryToPlaceArmies=null;
		for(String playerCountry: playerCountries) {
			Country armyCountry=getPlayerCountry(playerCountry, countries);
			if(armyCountry.getArmies()>maxArmyCount) {
				maxArmiesCountry=playerCountry;
				maxArmyCount=armyCountry.getArmies();
				countryToPlaceArmies=armyCountry;
			}
		}
		int countryNoArmies = countryToPlaceArmies.getArmies();
        countryToPlaceArmies.setArmies(countryNoArmies + playerArmies);
		System.out.println("You have successfully placed armies and reinforced your strongest country!");
		System.out.println(map.getCountries());
		player.setArmies(0);
		return null;
	}
	
	private Country getPlayerCountry(String playerCountry, ArrayList<Country> mapCountries) {
		int armyCount=0;
		Country returnCountry=null;
		for(Country country:mapCountries) {
				if(country.getCountryName().equals(playerCountry)) {
					armyCount=country.getArmies();
					returnCountry=country;
					break;
				}
		}
		return returnCountry; 
	}

}
