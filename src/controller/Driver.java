package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import CountryPackage.CountryClass;
import MapClassPackage.LoadMapFromFile;
import MapClassPackage.MapClass;
import PlayerPackgae.PlayerClass;

public class Driver {

	public static void main(String args[]) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		ArrayList<PlayerClass> players;
		MapClass map = null;
		CountryClass country_class;
		System.out.println("Get Ready to play Conquest!\n");
		System.out.println("1.Create Map From Console \n2.Load from a File");
		OUTER:
		while(true)
		{
		try {
        int choice=Integer.parseInt(br.readLine());
        switch(choice){
		case 1:	
		{
			map=new MapClass();
			map.populateMap();
			map.writeTheMapToTheTextFile();
			break;
		}
			
		case 2:
		{
			System.out.println("Enter the File Name");
			String temp=br.readLine();
			LoadMapFromFile lm=new LoadMapFromFile(temp.trim());
		    lm.loapMap();
		    lm.writeTheMapToTheTextFile();
		    System.out.println("Map Loaded!");
			break;
		}
		default:
		{
			System.out.println("Invalid choice");
			continue OUTER;
		}
        }
        break;
		}
		catch(NumberFormatException e)
		{
			System.out.println("Enter either 1 or 2");
			continue OUTER;
		}
		}	
		System.out.println("Enter the Number of players want to play the Game");
		
		Inner:while(true)
		{
			try
			{
				int temp_num=Integer.parseInt(br.readLine());
				players=new ArrayList<PlayerClass>(temp_num);
				for(int i=0;i<temp_num;i++)
				{
					System.out.println("Enter player "+i+" name");
					String temp_name=br.readLine();
					players.add(new PlayerClass(temp_name,0));
				}
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Enter a positive integer");
				continue Inner;
			}
		}
		System.out.println("Randomly Assigning Countries to Playqers");
		map.assignPlayersToCountries(players,players.size());
		System.out.println(map.getCountries());
		
	}
	
	   
	
	
	
	
}
