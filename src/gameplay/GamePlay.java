package gameplay;

import java.util.ArrayList;
import java.util.Scanner;

import country.Country;
import map.RiskMap;
import player.Player;


/**
 * GamePlay Class represents a game
 *
 * @author Suthakhar
 * @version 1.0
 * @since 2019-02-27
 */
public class GamePlay {

    private static Scanner scanner = new Scanner(System.in);

    private ArrayList<Player> players;

    private RiskMap map;


    public GamePlay(ArrayList<Player> players, RiskMap map) {
        this.players = players;
        this.map = map;
    }

    public void start() {
        System.out.println("\n**** Game has started ****\n");
        boolean game_over = false;
        while (!game_over) {
            System.out.println("** New Round Begins **");
            for (Player player : players) {
                System.out.println("\n** Player- " + player.getPlayer_name() + " **");
                reinforcement(player);
                fortification();
            }
            System.out.println("\nContinue the game?\nYes\nNo");
            boolean continue_game_flag = false;
            while (!continue_game_flag) {
                String choice = scanner.next();
                if (choice.equals("Yes")) {
                    continue_game_flag = true;
                } else if (choice.equals("No")) {
                    continue_game_flag = true;
                    game_over = true;
                } else {
                    System.out.println("Invalid choice! Enter either Yes or No: ");
                    scanner.next();
                }
            }
        }
        System.out.println("\n***Game Over***\n");
    }

    /**
     *
     */
    private void reinforcement(Player player) {
        int player_armies = player.getArmies();
        ArrayList<String> player_countries = player.getCountries();
        System.out.println("\n**Reinforcement**\n");
        //display armies player owns
        System.out.println("No of armies to place: " + player_armies);
        //display countries he owns
        System.out.println("Countries you own: " + player_countries);
        //place armies on countries-> Country class->
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
                            if (no_of_armies_to_place <= player_armies && no_of_armies_to_place>0) {
                                no_of_armies_flag = true;
                                System.out.println("Placing " + no_of_armies_to_place + " armies in country " + country_name);
                                ArrayList<Country> countries = map.getCountries();
                                for (Country country : countries) {
                                    if(country.getCountryName().equals(country_name)){
                                        int country_no_armies=country.getArmies();
                                        country.setArmies(country_no_armies+no_of_armies_to_place);
                                        System.out.println("Placed Successfully! Number of Armies in Country "+country_name+" is "+country.getArmies()+"\n");
                                    }
                                }
                                player_armies = player_armies - no_of_armies_to_place;
                                player.setArmies(player_armies);
                                System.out.println("Remaining armies to place: "+player_armies);
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
     *
     */
    private void attack() {

    }

    /**
     *
     */
    private void fortification() {

        System.out.println("Fortification");
    }

}
