package view;


import controller.GamePlay;
import model.Card;
import model.RiskMap;
import model.Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Driver Class to initiate the game
 *
 * @author Prashanthi
 * @version 1.0
 * @since 2019-02-26
 */
public class Driver {

    /**
     * Holds all the players in the game
     */
    private static ArrayList<Player> players = null;

    /**
     * A RiskMap instance
     */
    public static RiskMap map = null;

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);


    /**
     * Initiates the game
     * @param args command line arguments
     */
    public static void main(String args[]) {
        startGame();
    }

    /**
     * Implements the game phases according to the Risk rules
     */
    private static void startGame(){
        try{
            boolean map_flag=false;
            while (!map_flag){
                map = new RiskMap();
                getMapInput();
                boolean edit_map_flag=false;
                while(!edit_map_flag){
                    if (map.areContinentsConnected() && map.isMapConnected()) {
                        System.out.println("\nContinents and its countries: "+map.getContinentsWithCountries());
                        System.out.println("\nCountries and its neighbours: "+map.getAdjCountries());
                        System.out.println("\n****Map that is loaded is connected and valid!****\n");
                        System.out.println("Do you want to edit the map?\nYes\nNo");
                        boolean edit_map_choice_flag=false;
                        while(!edit_map_choice_flag){
                            String edit_map_choice = scanner.next();
                            if (edit_map_choice.equals("Yes")) {
                                edit_map_choice_flag=true;
                                map.editMap();
                                map.writeTheMapToTheTextFile();
                            } else if (edit_map_choice.equals("No")) {
                                edit_map_choice_flag=true;
                                edit_map_flag=true;
                                getPlayersInput();

                                /*
                                 * Observer Pattern
                                 * 1. Phase View to display name of the game phase, current player's name,
                                 *    information about actions that are taking place in the phase which should
                                 *    declared at the beginning of every phase
                                 * 2. Player World Domination View to display the % of map controlled by every player,
                                 *    continents controlled by every player, total number of armies owned by every player
                                 */
                                GamePlay gamePlay  =new GamePlay(players,map);
                                gamePlay.addObserver(new PlayerWorldDomination());
                                gamePlay.start(gamePlay);
                            } else {
                                System.out.println("Invalid! Enter either Yes or No: ");
                            }
                        }
                        map_flag=true;
                    } else {
                        edit_map_flag=true;
                        map_flag=false;
                        System.out.println("\n**Map Overview**\n\nContinents and its countries and its neighbours: "+map.getContinentsWithCountriesAndNeighbours());
                        System.out.println("\nContinents and its countries: "+map.getContinentsWithCountries());
                        System.out.println("\nCountries and its neighbours: "+map.getAdjCountries());
                        System.out.println("\n**** Map is not connected or one of the Continents is not connected and is invalid! Please start again! ****\n");
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("Unhandled Exception: "+ex);
            System.out.println("Start Again");
            startGame();
        }

    }

    /**
     * Game starts by user creation of a map file.
     * @throws IOException on user input
     */
    private static void getMapInput() throws IOException {
        System.out.println("Get Ready to play Conquest!\n");
        System.out.println("1.Create RiskMap From Console \n2.Load from a File");

        boolean choice_flag = false;
        while (!choice_flag) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
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
                            String input_map_file = scanner.next();
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
                scanner.next();
            }
        }
    }

    /**
     * User chooses the number of players, then all countries are randomly assigned to players.
     */
    private static void getPlayersInput() {

        boolean players_flag = false;
        System.out.println("Enter the number of players [2 to 6]: ");
        while (!players_flag) {
            if (scanner.hasNextInt()) {
                players_flag = true;
                int no_of_players = scanner.nextInt();
                if (no_of_players >= 2 && no_of_players <= 6) {
                    players = new ArrayList<>(no_of_players);
                    ArrayList<String> playerCountries=new ArrayList<>();

                    for (int i = 0; i < no_of_players; i++) {
                        ArrayList<Card> cards=new ArrayList<>();
                        System.out.println("Enter player " + (i + 1) + " name: ");
                        String player_name = scanner.next();
                        players.add(new Player(player_name, 0,playerCountries, cards ));
                    }
                } else {
                    System.out.println("Invalid no of players! Enter again [2 to 6]:");
                    players_flag = false;
                }

            } else {
                System.out.println("Invalid characters! Enter again [2 to 6]:");
                scanner.next();
            }
        }
        System.out.println("\n** Randomly Assigning Countries to Players **\n");
        map.assignPlayersToCountries(players, players.size());
        System.out.println(map.getCountries());

        System.out.println("\n** Assigning Initial Armies to Players based on the number of players in the game **\n");
        Player player = new Player();
        players=player.setInitialArmies(players, map.getCountries());
    }


}
