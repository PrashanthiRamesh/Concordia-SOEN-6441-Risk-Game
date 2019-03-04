package controller;


import map.RiskMap;
import player.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

/**
 * Driver Class
 *
 * @author Prashanthi
 */
public class Driver {

    /**
     *
     */
    private static RiskMap map = null;

    /**
     *
     */
    private static Scanner scan = new Scanner(System.in);

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {

        boolean map_flag=false;
        while (!map_flag){
            map = new RiskMap();
            getMapInput();
            boolean edit_map_flag=false;
            while(!edit_map_flag){
                if (map.isMapConnected()) {
                    System.out.println("\nContinents and its countries: "+map.getContinents_with_countries());
                    System.out.println("\nCountries and its neighbours: "+map.getAdj_countries());
                    System.out.println("****Map that is loaded is connected and valid!****");
                    System.out.println("Do you want to edit the map?\nYes\nNo\n");
                    boolean edit_map_choice_flag=false;
                    while(!edit_map_choice_flag){
                        String edit_map_choice = scan.next();
                        if (edit_map_choice.equals("Yes")) {
                            edit_map_choice_flag=true;
                            map.editMap();
                            map.writeTheMapToTheTextFile();
                        } else if (edit_map_choice.equals("No")) {
                            edit_map_choice_flag=true;
                            edit_map_flag=true;
                            getPlayersInput();
                        } else {
                            System.out.println("Invalid! Enter either Yes or No: ");
                        }
                    }
                    map_flag=true;
                } else {
                    edit_map_flag=true;
                    map_flag=false;
                    System.out.println("\n**Map after editing**\nContinents and its countries: "+map.getContinents_with_countries());
                    System.out.println("\nCountries and its neighbours: "+map.getAdj_countries());
                    System.out.println("\n**** Map is not connected and is invalid! Please start again! ****\n");
                }
            }
        }
    }

    /**
     * @throws IOException
     */
    private static void getMapInput() throws IOException {
        System.out.println("Get Ready to play Conquest!\n");
        System.out.println("1.Create RiskMap From Console \n2.Load from a File");

        boolean choice_flag = false;
        while (!choice_flag) {
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                choice_flag = true;
                switch (choice) {
                    case 1: {
                        map.populateMap();
                        map.writeTheMapToTheTextFile();
                        break;
                    }
                    case 2: {
                        boolean file_flag = false;
                        System.out.println("Enter the RiskMap File Name [eg: input.txt]: ");
                        while (!file_flag) {
                            String input_map_file = scan.next();
                            File map_file = new File(input_map_file.trim());
                            if (map_file.exists()) {
                                file_flag = true;
                                map.loadMap(input_map_file.trim());
                                map.writeTheMapToTheTextFile();
                                System.out.println("Risk Map Loaded!");
                            } else {
                                System.out.println("File does not exist! Enter again [eg: input.txt]: ");
                            }
                        }
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice! Enter either 1 or 2:");
                        choice_flag = false;
                    }
                }
            } else {
                System.out.println("Invalid characters! Enter either 1 or 2: ");
                scan.next();
            }
        }
    }

    /**
     *
     */
    private static void getPlayersInput() {
        ArrayList<Player> players = null;
        boolean players_flag = false;
        System.out.println("Enter the number of players [2 to 6]: ");
        while (!players_flag) {
            if (scan.hasNextInt()) {
                players_flag = true;
                int no_of_players = scan.nextInt();
                if (no_of_players >= 2 && no_of_players <= 6) {
                    players = new ArrayList<>(no_of_players);
                    for (int i = 0; i < no_of_players; i++) {
                        System.out.println("Enter player " + (i + 1) + " name: ");
                        String player_name = scan.next();
                        players.add(new Player(player_name, 0));
                    }
                } else {
                    System.out.println("Invalid no of players! Enter again [2 to 6]:");
                    players_flag = false;
                }

            } else {
                System.out.println("Invalid characters! Enter again [2 to 6]:");
                scan.next();
            }
        }

        System.out.println("Randomly Assigning Countries to Players");
        map.assignPlayersToCountries(players, players.size());
        System.out.println(map.getCountries());

        System.out.println("Assigning Initial Armies to Players based on the number of countries they own");
        Player player_armies = new Player();
        player_armies.calculateArmies(players, map.getCountries());
    }


}
