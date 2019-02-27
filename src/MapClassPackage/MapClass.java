package MapClassPackage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import country.Country;
import player.Player;

/**
 * @author Maqsood
 * Map Class Loads the maps form the Console and form the file.
 *
 */
public class MapClass {
	/**
	 * Holds the Number of continents in the Map.
	 */
	private int noofcontinents;
	/**
	 * Holds the Number of Adjacent countries mentioned for each country.
	 */
	private int noof_adjacentcountries;
	/**
	 * HashMap Which Stores the Continents along with their control Values.
	 */
	private HashMap<String,Integer> continents;
	/**
	 * HashMap which stores the country and the neighboring countries to it.
	 */
	private HashMap<String,ArrayList<String>> adj_countries;
	/**
	 * HashMap Which stores the Continent along with the number of contrives in it.
	 */
	private HashMap<String,Integer> noofcountries;
	
	private ArrayList<Country> countries;
	
	
	public MapClass()
	{
		continents=new HashMap<String,Integer>();
		adj_countries=new HashMap<String,ArrayList<String>>();
		noofcountries=new HashMap<String,Integer>();
	}
	/**
	 * populateMap loads the map form the console.
	 * @throws IOException 
	 */
	public void populateMap() throws IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of Continents");
		noofcontinents=Integer.parseInt(br.readLine());
		System.out.println("Enter each Continent along with its control Value seperated with =");
	    for(int i=0;i<noofcontinents;i++)
	    {
	    	String temp_str=br.readLine();
	    	System.out.println(temp_str);
	    	String[] temp=temp_str.split("=");
	    	
	    	if(!continents.containsKey(temp[0]))
	    	{
	    	if(temp.length!=2)
	    	{
	    		System.out.println("Incorrect Input : Provide continent along with its control value");
	    		i--;
	    	}
	    	else if(!temp[1].matches(".*\\d+.*"))
	    	{
	    		System.out.println(" Incorrect Input :The Control Value should be a Numeric");
	    		i--;
	    	}
	    	else
	    	{
	    		continents.put(temp[0], Integer.parseInt(temp[1]));
	    	}
	    	}
	    	else
	    	{
	    		System.out.println("Continent Already Exists: please Renenter with correct Continent name");
	    	    i--;
	    	
	    	}
	    }
		for(String continent:continents.keySet())
		{
			System.out.println("Enter the Number of countries in "+ continent);
			noofcountries.put(continent, Integer.parseInt(br.readLine()));
		    System.out.println("Enter the all the Countries Along with with their neighbours seperated by comma starting a new line for each country");
			for(int j=0;j<noofcountries.get(continent);j++)
			{
				String[] temp=br.readLine().split(",");
				ArrayList<String> temp_list=new ArrayList<String>();
				for(int i=1;i<temp.length;i++)
				{
					if(!temp[i].equals(temp[0]))
					temp_list.add(temp[i]);
					else
					System.out.println("A country Cannot be neighbour to itself");
				}
				adj_countries.put(temp[0],temp_list);
			}
		    
		}
	    
}
	/**
	 * Writes the Map to a text file.
	 * @throws IOException 
	 */
	public void writeTheMapToTheTextFile() throws IOException
	{
		FileWriter fw=new FileWriter("OutputMap.txt");
		PrintWriter pw=new PrintWriter(fw);
		
		pw.println("[Continents]");
		for(String continent:continents.keySet())
		{
			pw.write(continent+"="+continents.get(continent));
			pw.println();
		}
		pw.println("[Territories]");
		for(String country:adj_countries.keySet())
		{
			System.out.println(adj_countries);
			pw.write(country+","+"0,0,");
			for(int i=0;i<adj_countries.get(country).size();i++)
			{
				pw.write(adj_countries.get(country).get(i)+",");
			}
			pw.println();
		}
		fw.close();
		pw.close();
	}
	
	public void assignPlayersToCountries(ArrayList<Player> players,int no_of_players)
	{
		countries=new ArrayList<Country>();
		int temp=0;
		for(String var:adj_countries.keySet())
		{
			int rand_temp=randInt(0,no_of_players-1);
			System.out.println(rand_temp+" "+no_of_players);
			countries.add(new Country(var,players.get(rand_temp).getPlayername(),0));	
		}	
		
	}
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public int getNoofcontinents() {
		return noofcontinents;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}
	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}
	public int getNoof_adjacentcountries() {
		return noof_adjacentcountries;
	}

	public HashMap<String, Integer> getContinents() {
		return continents;
	}
	
	public HashMap<String, ArrayList<String>> getAdj_countries() {
		return adj_countries;
	}

	public HashMap<String, Integer> getNoofcountries() {
		return noofcountries;
	}
	
	
}
