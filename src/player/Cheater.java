package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import model.Country;
import model.Player;
import model.RiskMap;
import view.ComputerCardExchange;

public class Cheater extends Observable implements Strategy {

	RiskMap map;

	Player player;
	
	ArrayList<Player> players;
	
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
		System.out.println("Player armies will be doubled in all the countries they own");
		deployArmies(player, this.map);
		this.deleteObserver(cardExchange);
		return null;
	}

	@Override
	public Player attack(Player player, Player mapPlayer) {
		System.out.println("Player armies will be doubled in all the countries they own");
		this.map = mapPlayer.map;
		this.player=player;
		this.players=mapPlayer.players;
		int playerArmies = player.getArmies();
		ArrayList<Country> countries = map.getCountries();
		//iterate tru all his countries
		System.out.println("$$$ Cheater conquers all neighbour countries $$$");
		System.out.println("No of Countries before conquering neighbours: "+this.player.getCountries().size());
		Iterator<String> playerCountries = this.player.getCountries().iterator();
		for(String playerCountryName:this.player.getCountries()) {
			autoConquerOtherPlayersCountryThatAreNeighbours(playerCountryName);
		}
		updatePlayerCountries();
		System.out.println("No of Countries after conquering neighbours: "+this.player.getCountries().size());
		updateIfPlayerWon();
		
		return null;
	}

	@Override
	public Player fortification(Player player, RiskMap map) {
		System.out.println("Player armies will be doubled in all the countries they own in which there are neighbours that is owned by different player");
		this.map = map;
		this.player=player;
		int c = 0;
		ArrayList<String> playerCountries=player.getCountries();
		
		System.out.println("\nCountries you own: " + playerCountries);
		boolean canForfeit = false;
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
		if(canForfeit) {
			for(String playerCountryName:this.player.getCountries()) {
				ArrayList<String> neighbours = this.map.adjCountries.get(playerCountryName);
				for (int i = 0; i < neighbours.size() - 1; i++) {
					//int neighbourArmies = getPlayerCountry(neighbours.get(i), this.map.countries).getArmies();
					if (!isNeighbourCountryOwnedByPlayer(neighbours.get(i))) {
						Country country=getPlayerCountry(playerCountryName, this.map.countries);
						System.out.print(playerCountryName+" armies increased from "+country.getArmies()+" to ");
						country.setArmies(country.getArmies()*2);
						System.out.println(country.getArmies());
					}
				}
			}
		}else {
			System.out.println("Cheater cannot forfeit anymore!");
		}
		
		return null;
	}

	@Override
	public Player deployArmies(Player player, RiskMap map) {
		System.out.println("Player armies will be doubled in all the countries they own");
		this.map = map;
		int playerArmies = player.getArmies();
		ArrayList<String> playerCountries = player.getCountries();
		ArrayList<Country> countries = map.getCountries();
		for (String playerCountry : playerCountries) {
			Country playCountry = getPlayerCountry(playerCountry, countries);
			int countryArmies=playCountry.getArmies();
			System.out.print(playCountry.getCountryName()+" armies increased from "+countryArmies+" to ");
			playCountry.setArmies(countryArmies*2);
			System.out.println(playCountry.getArmies());
		}
		
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
	
	private void autoConquerOtherPlayersCountryThatAreNeighbours(String playerCountryName) {
		ArrayList<String> neighbours = this.map.adjCountries.get(playerCountryName);
		for (int i = 0; i < neighbours.size() - 1; i++) {
			//int neighbourArmies = getPlayerCountry(neighbours.get(i), this.map.countries).getArmies();
			if (!isNeighbourCountryOwnedByPlayer(neighbours.get(i))) {
				String toConqueurCountryName = neighbours.get(i);
				Country toConquerCountry=getPlayerCountry(toConqueurCountryName, this.map.countries);
				String attackerName=this.player.getPlayerName();
				String lostPlayerName=toConquerCountry.getBelongsTo();
				toConquerCountry.setBelongsTo(attackerName);
			
				Player lostPlayer=getPlayerByPlayerName(lostPlayerName);
			}
			
		}
		
	}
	
	private boolean isNeighbourCountryOwnedByPlayer(String neighbourCountryName) {
		if (this.player.getCountries().contains(neighbourCountryName)) {
			return true;
		}
		return false;
	}
	
	private Player getPlayerByPlayerName(String playerName) {
		for(Player player:this.players) {
			if(player.getPlayerName().equals(playerName)) {
				return player;
			}
		}
		return null;
	}
	
	private void updatePlayerCountries() {
		for(Player player:this.players) {
			String playerName=player.getPlayerName();
			ArrayList<String> playerCountries=new ArrayList<String>();
			for(Country country:this.map.countries) {
				if(country.getBelongsTo().equals(playerName)) {
					playerCountries.add(country.getCountryName());
				}
			}
			player.setCountries(playerCountries);
		}
	}
	
	private void updateIfPlayerWon() {
		if(this.player.getCountries().size()==this.map.countries.size()) {
			this.player.setWinner(true);
		}
	}


}
