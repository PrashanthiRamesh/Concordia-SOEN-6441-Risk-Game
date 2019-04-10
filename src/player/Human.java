package player;

import java.util.ArrayList;
import java.util.Scanner;

import model.Country;
import model.Player;
import model.RiskMap;

public class Human implements Strategy {
	
	RiskMap map;
	
	/**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

	@Override
	public Player reinforcement(Player player, RiskMap map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player attack(Player player, RiskMap map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player fortification(Player player, RiskMap map) {
		// TODO Auto-generated method stub
		return null;
	}

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
