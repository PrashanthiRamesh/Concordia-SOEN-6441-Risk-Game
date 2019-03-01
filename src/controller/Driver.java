package controller;


import map.LoadMapFromFile;
import map.RiskMap;
import player.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

/**
 * Driver Class
 * @author Maqsood
 *
 */
public class Driver {
	public static void main(String args[]) throws IOException {

		RiskMap map = null;
		System.out.println("Get Ready to play Conquest!\n");
		System.out.println("1.Create RiskMap From Console \n2.Load from a File");

		Scanner scan=new Scanner(System.in);
		int choice;
		/*
		Prash: Changed from try catch to this logic because, number format exception
		is handled for all the methods called within switch which causes
		other unhandled exceptions
		 */
		boolean choice_flag=false;
		while(!choice_flag){
			if(scan.hasNextInt()){
				choice=scan.nextInt();
				choice_flag=true;
				switch (choice){
					case 1: {
						map = new RiskMap();
						map.populateMap();
						map.writeTheMapToTheTextFile();
						break;
					}
					case 2: {
						boolean file_flag=false;
						System.out.println("Enter the RiskMap File Name [eg: input.txt]: ");
						while(!file_flag) {
								String input_map_file = scan.next();
								File map_file = new File(input_map_file.trim());
                            if(map_file.exists()) {
                                file_flag=true;
                                LoadMapFromFile lm = new LoadMapFromFile(input_map_file.trim());
                                lm.loapMap();
                                map=new RiskMap();
                                map.setContinents(lm.getContinents());
                                map.setAdj_countries(lm.getAdj_countries());
                                map.writeTheMapToTheTextFile();
                                System.out.println("RiskMap Loaded!");
                            }else{
                                System.out.println("File does not exist! Enter again [eg: input.txt]: ");
                            }
						}
						break;
					}
					default: {
						System.out.println("Invalid choice! Enter either 1 or 2:");
						choice_flag=false;
					}
				}
			}else{
				System.out.println("Invalid characters! Enter either 1 or 2: ");
				scan.next();
			}
		}
        ArrayList<Player> players=null;
		boolean players_flag=false;
		System.out.println("Enter the number of players [2 to 6]: ");
		while (!players_flag) {
            if(scan.hasNextInt()) {
                players_flag = true;
                int no_of_players = scan.nextInt();
                if(no_of_players>=2 && no_of_players<=6){
                    players = new ArrayList<>(no_of_players);
                    for (int i = 0; i < no_of_players; i++) {
                        System.out.println("Enter player " + (i+1) + " name: ");
                        String player_name = scan.next();
                        players.add(new Player(player_name, 0));
                    }
                }else{
                    System.out.println("Invalid no of players! Enter again [2 to 6]:");
                    players_flag=false;
                }

            }else{
                System.out.println("Invalid characters! Enter again [2 to 6]:");
                scan.next();
            }
		}
		//validate map
        map.validateMap();
		System.out.println("Randomly Assigning Countries to Players");
		map.assignPlayersToCountries(players, players.size());
		System.out.println(map.getCountries());

	}

}
