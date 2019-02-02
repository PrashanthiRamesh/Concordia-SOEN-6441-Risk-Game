package MapClassPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
	
	/**
	 * populateMap loads the map form the console.
	 */
	public void populateMap()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number of Continents");
		noofcontinents=sc.nextInt();
		System.out.println("Enter each Continent along with its control Value seperated with a space");
	    for(int i=0;i<noofcontinents;i++)
	    {
	    	String[] temp=sc.nextLine().split(" ");
	    	continents.put(temp[0], Integer.parseInt(temp[1]));
	    }
		for(String continent:continents.keySet())
		{
			System.out.println("Enter the Number of countries in "+ continent);
			noofcountries.put(continent, sc.nextInt());
		    System.out.println("Enter the all the Countries Along with with their neighbours seperated by comma starting a new line for each country");
			while(sc.hasNextLine())
			{
				String[] temp=sc.nextLine().split(",");
				ArrayList<String> temp_list=new ArrayList<String>();
				for(int i=1;i<temp.length;i++)
				{
					temp_list.add(temp[i]);
				}
				adj_countries.put(temp[1],temp_list);
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
			pw.write(country+","+"0,0,");
			for(int i=1;i<adj_countries.get(country).size();i++)
			{
				pw.write(adj_countries.get(country).get(i)+",");
			}
			pw.println();
		}
	}

	public int getNoofcontinents() {
		return noofcontinents;
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
