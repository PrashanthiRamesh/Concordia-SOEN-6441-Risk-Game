package model;

import java.io.*;
import java.util.*;


/**
 * RiskMap Class Loads the maps form the Console and from the file.
 *
 * @author Maqsood
 * @version 1.0
 * @since   2019-02-01
 */
public class RiskMap {

    /**
     * Holds the Number of continents in the RiskMap.
     */
    private int noOfContinents;

    /**
     * Holds the Number of Adjacent countries (neighbours) for each country in the map.
     */
    private int noOfAdjacentCountries;

    /**
     * ArrayList that holds all the countries in the map
     */
    private ArrayList<Country> countries;

    /**
     * LinkedHashMap Which Stores the Continents along with their control Values.
     */
    private LinkedHashMap<String, Integer> continents;

    /**
     * LinkedHashMap which stores the country and the neighboring countries to it.
     */
    public LinkedHashMap<String, ArrayList<String>> adjCountries;

    /**
     * LinkedHashMap which stores the continent and its countries and their neighboring countries
     */
    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> continentsWithCountriesAndNeighbours;

    /**
     * LinkedHashMap Which stores the Continent along with the number of countries in it.
     */
    private LinkedHashMap<String, Integer> continentWithNoOfCountries;

    /**
     * LinkedHashMap that holds Continents and the countries in it
     */
    private LinkedHashMap<String, ArrayList<String>> continentsWithCountries;

    /**
     * Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines
     */
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * To read and parse various primitive values.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Getter for field noOfContinents- returns the value of number of continents
     * @return An integer value of number of continents in the map
     */
    public int getNoOfContinents() {
        return noOfContinents;
    }

    /**
     * Setter for field noOfContinents- assigns the number of countries
     * @param noOfContinents Integer value to set for number of countries
     */
    public void setNoOfContinents(int noOfContinents) {
        this.noOfContinents = noOfContinents;
    }

    /**
     * Getter for field countries- returns the value of countries
     * @return Array List of countries in the map
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * Setter for field countries- assigns the value of countries
     * @param countries ArrayList of countries
     */
    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    /**
     * Getter for field noOfAdjacentCountries- returns the value of countries
     * @return Array List of countries in the map
     */
    public int getNoOfAdjacentCountries() {
        return noOfAdjacentCountries;
    }

    /**
     * Setter for field noOfAdjacentCountries- assigns the number of adjacent countries
     * @param noOfAdjacentCountries Integer value to set for number of adjacent countries
     */
    public void setNoOfAdjacentCountries(int noOfAdjacentCountries) {
        this.noOfAdjacentCountries = noOfAdjacentCountries;
    }

    /**
     * Getter for field continents- returns the continents and their corresponding control values
     * @return continents LinkedHashMap of String and Integer for continents and control values in the map
     */
    public LinkedHashMap<String, Integer> getContinents() {
        return continents;
    }

    /**
     * Setter for the field continents- assigns the value of continents and its corresponding control value
     * @param continents LinkedHashMap of String and Integer to set the continent and its control value
     */
    public void setContinents(LinkedHashMap<String, Integer> continents) {
        this.continents = continents;
    }

    /**
     * Getter for field adjCountries- returns the all countries and its neighbouring/adjacent countries
     * @return adjCountries LinkedHashMap of String and ArrayList for country and its adjacent countries
     */
    public LinkedHashMap<String, ArrayList<String>> getAdjCountries() {
        return adjCountries;
    }

    /**
     * Setter for field adjCountries- assigns the value of countries and its adjacent countries
     * @param adjCountries LinkedHashMap of String and ArrayList to set the country and its neighbours
     */
    public void setAdjCountries(LinkedHashMap<String, ArrayList<String>> adjCountries) {
        this.adjCountries = adjCountries;
    }

    /**
     * Getter for field continentWithNoOfCountries- returns all continents and number of countries in it
     * @return continentWithNoOfCountries LinkedHashMap of String and Integer for continents and its number of countries in it
     */
    public LinkedHashMap<String, Integer> getContinentWithNoOfCountries() {
        return continentWithNoOfCountries;
    }

    /**
     * Setter for field continentWithNoOfCountries- assigns the value of continents and its number of countries in it
     * @param continentWithNoOfCountries LinkedHashMap of String and Integer to set continents and its countries
     */
    public void setContinentWithNoOfCountries(LinkedHashMap<String, Integer> continentWithNoOfCountries) {
        this.continentWithNoOfCountries = continentWithNoOfCountries;
    }

    /**
     * Getter for field continentsWithCountries- returns all the continents along with their countries
     * @return continentsWithCountries LinkedHashMap of String and ArrayList for continents and its countries
     */
    public LinkedHashMap<String, ArrayList<String>> getContinentsWithCountries() {
        return continentsWithCountries;
    }

    /**
     * Setter for field continentsWithCountries- assigns the value of continents and its countries
     * @param continentsWithCountries LinkdedHashMap of String and ArrayList to set continents and its countries
     */
    public void setContinentsWithCountries(LinkedHashMap<String, ArrayList<String>> continentsWithCountries) {
        this.continentsWithCountries = continentsWithCountries;
    }

    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getContinentsWithCountriesAndNeighbours() {
        return continentsWithCountriesAndNeighbours;
    }

    public void setContinentsWithCountriesAndNeighbours(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> continentsWithCountriesAndNeighbours) {
        this.continentsWithCountriesAndNeighbours = continentsWithCountriesAndNeighbours;
    }

    /**
     * Creates a riskMap
     */
    public RiskMap() {
        continents = new LinkedHashMap<String, Integer>();
        adjCountries = new LinkedHashMap<String, ArrayList<String>>();
        continentWithNoOfCountries = new LinkedHashMap<String, Integer>();
        continentsWithCountries =new LinkedHashMap<>();
        continentsWithCountriesAndNeighbours=new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
    }

    /**
     * Loads the map form the console.
     * @throws IOException On input error
     */
    public void populateMap() throws IOException {
        inputNumberOfContinents();
        inputCountries();
    }

    /**
     * Prompt user to enter number of continents
     * @throws IOException On input error
     */
    private void inputNumberOfContinents() throws IOException {
        System.out.println("Enter the number of Continents");
        boolean continent_flag = false;
        while (!continent_flag) {
            if (scanner.hasNextInt()) {
                continent_flag = true;
                noOfContinents = scanner.nextInt();
                System.out.println("Enter each Continent along with its control value separated with =");
                parseContinents(noOfContinents);
            } else {
                System.out.println("Invalid characters! Enter again :");
                scanner.next();
            }
        }
    }

    /**
     * Prompt to get name of continent along with its control value
     * @param number_of_continents number of continents to iterate over to get its values
     * @return name of the continent
     * @throws IOException on user input
     */
    private String parseContinents(int number_of_continents) throws IOException {
        String return_continent = "";
        for (int i = 0; i < number_of_continents; i++) {
            String continent_with_control_value = bufferedReader.readLine();
            String[] continent_and_control_value = continent_with_control_value.split("=");
            if (!continents.containsKey(continent_and_control_value[0])) {
                if (continent_and_control_value.length != 2) {
                    System.out.println("Incorrect Input : Provide continent along with its control value");
                    i--;
                } else if (!continent_and_control_value[1].matches(".*\\d+.*")) {
                    System.out.println(" Incorrect Input :The Control Value should be a Numeric");
                    i--;
                } else {
                    continents.put(continent_and_control_value[0], Integer.parseInt(continent_and_control_value[1]));
                    if (i == 0) {
                        return_continent = continent_and_control_value[0];
                    }
                }
            } else {
                System.out.println("Continent Already Exists: please Re enter with correct Continent name");
                i--;
            }
        }
        return return_continent;
    }

    /**
     * Iterate through number of continents and get country details by calling the corresponding method
     * @throws IOException on user input
     */
    private void inputCountries() throws IOException {

        for (String continent : continents.keySet()) {
            inputCountriesAndNeighbours(continent);
        }
    }

    /**
     * Prompt to get number of countries, country name, adjacent countries to a particular continent
     * @param continent name of the continent
     * @throws IOException on user input
     */
    private void inputCountriesAndNeighbours(String continent) throws IOException {
        System.out.println("Enter the Number of countries in " + continent);
        boolean no_of_countries_flag=false;
        LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours = new LinkedHashMap<>();
        while (!no_of_countries_flag){
            if(scanner.hasNextInt()){
                no_of_countries_flag=true;
                continentWithNoOfCountries.put(continent, scanner.nextInt());
                System.out.println(
                        "Enter all the Countries Along with with their neighbours separated by comma starting a new line for each country");
                ArrayList<String> countries = new ArrayList<>();
                outer:
                for (int j = 0; j < continentWithNoOfCountries.get(continent); j++) {
                    String[] temp = bufferedReader.readLine().split(",");
                    for(int i=0;i<temp.length;i++) {
                    	if(temp[i].trim().length()<1) {
                    		System.out.println("please enter the input in correct format");
                    		j--;
                    		continue outer;
                    	}
                    }
                    ArrayList<String> temp_list = new ArrayList<String>();
                    for (int i = 1; i < temp.length; i++) {
                        if (!temp[i].equals(temp[0]))
                            temp_list.add(temp[i]);
                        else {
                            System.out.println("A country Cannot be neighbour to itself");
                            j--;
                        }
                    }
                    adjCountries.put(temp[0], temp_list);
                    countries.add(temp[0]);
                    countriesWithNeighbours.put(temp[0],temp_list);

                }
                continentsWithCountries.put(continent, countries);
                continentsWithCountriesAndNeighbours.put(continent,countriesWithNeighbours);
            }else{
                System.out.println("Invalid characters! Please enter only numbers. Try Again: ");
                scanner.next();
            }
        }
    }


    /**
     * Writes and parses the Risk Map to a text file.
     * @throws IOException on user input
     */
    public String writeTheMapToTheTextFile() throws IOException {
        FileWriter fw = new FileWriter("OutputMap.txt");
        PrintWriter pw = new PrintWriter(fw);

        pw.println("[Continents]");
        for (String continent : continents.keySet()) {
            pw.write(continent + "=" + continents.get(continent));
            pw.println();
        }
        pw.println("[Territories]");
        for (String country : adjCountries.keySet()) {
            pw.write(country + "," + "0,0,");
            for (int i = 0; i < adjCountries.get(country).size(); i++) {
                pw.write(adjCountries.get(country).get(i) + ",");
            }
            pw.println();
        }
       
        fw.close();
        pw.close();
        FileReader fir = new FileReader("OutputMap.txt");
        BufferedReader bir = new BufferedReader(fir);
        String toReturn=bir.readLine();
        fir.close();
        bir.close();
        return toReturn;
    }

    /**
     * Randomly assign countries in the map to all the players
     * @param players ArrayList of players of the game
     * @param no_of_players Number of players of the game
     */
    public void assignPlayersToCountries(ArrayList<Player> players, int no_of_players) {
        countries = new ArrayList<>();
        for (String country_name : adjCountries.keySet()) {
            int rand_temp = randInt(0, no_of_players - 1);
            String player_name=players.get(rand_temp).getPlayerName();
            countries.add(new Country(country_name, player_name, 0));
        }
    }

    /**
     * Generate a random integer between two integers (range)
     * @param min minimum value of the range
     * @param max maximum value of the range
     * @return a random integer
     */
    public int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Set the start node and end node of adjacent countries data structure to initiate the Breadth First Search
     * @return if map is connected it returns true, else it returns false
     */
    public boolean isMapConnected() {
        Map.Entry<String, ArrayList<String>> first_entry = adjCountries.entrySet().iterator().next();
        String start = first_entry.getKey();
        Map.Entry<String, ArrayList<String>> last_entry = (Map.Entry<String, ArrayList<String>>) adjCountries.entrySet().toArray()[adjCountries.size() - 1];
        String end = last_entry.getKey();
        return breadthFirstSearch(adjCountries, start, end);

    }

    /**
     * Set the start node and end node of adjacent countries data structure to initiate the Breadth First Search
     * @return if map is connected it returns true, else it returns false
     */
    public boolean areContinentsConnected() {
        /*
         * Iterate through all Continents and for each continent run bfs on each
         */
        Set<String> continents = continentsWithCountries.keySet();
        boolean continents_connected_flag=true;
        for(String continent:continents){
            LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours=continentsWithCountriesAndNeighbours.get(continent);
            Map.Entry<String, ArrayList<String>> first_entry = countriesWithNeighbours.entrySet().iterator().next();
            String start = first_entry.getKey();
            Map.Entry<String, ArrayList<String>> last_entry = (Map.Entry<String, ArrayList<String>>) countriesWithNeighbours.entrySet().toArray()[countriesWithNeighbours.size() - 1];
            String end = last_entry.getKey();
            if(!breadthFirstSearch(countriesWithNeighbours, start, end)){
                System.out.println("\nContinent- "+continent+" is not a connected sub-graph");
                continents_connected_flag=false;
                break;
            }else{
                System.out.println("\nContinent- "+continent+" is a connected sub-graph");
            }
        }
        return continents_connected_flag;
    }

    /**
     * Traverses the countries with adjacent countries to check if all the nodes are connected
     * @param adjList LinkedHashMap of countries and their adjacent countries
     * @param start First country in the list
     * @param end Last country in the list
     * @return true if the algorithm is able to traverse from first to last node, else returns false
     */

    public  boolean breadthFirstSearch(Map<String, ArrayList<String>> adjList, String start, String end) {
        Map<String, String> parents = new LinkedHashMap<String, String>();
        Queue<String> q = new LinkedList<String>();
        q.add(start);
        parents.put(start, "dummy");
        boolean connected = false;
        while (!q.isEmpty()) {
            String nextVertex = q.poll(); 
            if (nextVertex.equals(end)) {
                connected = true;
                break;
            }
            if(adjList.get(nextVertex)!=null){
                for (String edge : adjList.get(nextVertex)) {
                    if (!parents.containsKey(edge)) {
                        q.add(edge);
                        parents.put(edge, nextVertex);
                    }
                }
            }else{
                return false;
            }
        }
        return connected;
    }

    /**
     * Loads the RiskMap form the File
     * @throws IOException on user input
     */
    public String loadMap(String filename) throws IOException {
        FileReader fir = new FileReader(filename);
        BufferedReader bir = new BufferedReader(fir);
        while (!bir.readLine().trim().equals("[Continents]")) {

        }
        while (true) {
            String temp = bir.readLine();
            if (temp.trim().length() == 0) {
                break;
            } else {
                String[] temp_arr = temp.split("=");
                continents.put(temp_arr[0], Integer.parseInt(temp_arr[1]));
            }
        }
        while (!bir.readLine().trim().equals("[Territories]")) {

        }
        String line = bir.readLine();
        while (line != null) {
            LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours=new LinkedHashMap<>();
            String[] temp = line.split(",");
            if (temp.length > 1) {
                ArrayList<String> temp_al = new ArrayList<String>();
                for (int i = 4; i < temp.length; i++) {
                    temp_al.add(temp[i]);
                }
                //save continents with its countries logic here
                String continent = temp[3];
                String country = temp[0];
                saveContinentWithCountries(continent, country);
                adjCountries.put(country, temp_al);
                countriesWithNeighbours.put(country,temp_al);
                continentsWithCountriesAndNeighbours.put(continent,countriesWithNeighbours);
            }
            line = bir.readLine();
        }
        
        return adjCountries.get("AA").get(0);
        
    }

    /**
     * Store country in a continent
     * @param continent name of the continent
     * @param country name of the country
     */
    private void saveContinentWithCountries(String continent, String country) {
        ArrayList<String> countries = new ArrayList<>();
        if (continentsWithCountries.containsKey(continent)) {
            countries = continentsWithCountries.get(continent);
            countries.add(country);
            continentsWithCountries.put(continent, countries);
        } else {
            countries.add(country);
            continentsWithCountries.put(continent, countries);
        }
    }

    /**
     * Driver method to edit the map
     * @throws IOException on user input
     */
    public void editMap() throws IOException {
        System.out.println("Continents and its countries: " + this.continentsWithCountries);
        System.out.println("Countries and its neighbours:" + this.adjCountries);
        System.out.println();
        System.out.println("1.Add Continent\n2.Add Country\n3.Remove Continent\n4.Remove Country\n5.Quit Edit");
        boolean edit_map_choice_flag = false;
        while (!edit_map_choice_flag) {
            if (scanner.hasNextInt()) {
                edit_map_choice_flag = true;
                int edit_map_choice = scanner.nextInt();
                switch (edit_map_choice) {
                    case 1:
                        System.out.println("Enter one Continent along with its control value separated with =");
                        String continent_name = parseContinents(1);
                        inputCountriesAndNeighbours(continent_name);
                        break;
                    case 2:
                        System.out.println("** Continents and their countries **\n" + continentsWithCountries);
                        System.out.println("Enter the name of the continent to which you want to add the country: ");
                        boolean old_continent_flag = false;
                        while (!old_continent_flag) {
                            String old_continent = bufferedReader.readLine();
                            if (continentsWithCountries.containsKey(old_continent)) {
                                old_continent_flag = true;
                                System.out.println("Countries of the continent- " + old_continent + " : " + continentsWithCountries.get(old_continent));
                                inputCountryWhileEditingMap(old_continent);
                            } else {
                                System.out.println("Continent is not present! Please enter again: ");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Continents with their control values: " + continents);
                        System.out.println("Enter continent to be removed: ");
                        boolean continent_flag = false;
                        while (!continent_flag) {
                            String continent_to_remove = bufferedReader.readLine();
                            if (continents.containsKey(continent_to_remove)) {
                                continent_flag = true;
                                ArrayList<String> countries = continentsWithCountries.get(continent_to_remove);
                                for (String country : countries) {
                                    adjCountries.remove(country);
                                }
                                continentWithNoOfCountries.remove(continent_to_remove);
                                continentsWithCountries.remove(continent_to_remove);
                                System.out.println("**Continent removed**");
                            } else {
                                System.out.println("Continent does not exist in the map! Please enter again: ");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Countries and their neighbours: "+ adjCountries);
                        System.out.println("Enter the country to be removed: ");
                        boolean country_flag=false;
                        while (!country_flag){
                            String country_to_remove= bufferedReader.readLine();
                            if(adjCountries.containsKey(country_to_remove)){
                                country_flag=true;
                                adjCountries.remove(country_to_remove);
                            }else{
                                System.out.println("Country does not exist in the map! Please enter again: ");
                            }
                        }
                        break;
                    case 5:
                        System.out.println("ok! editing over!");
                        break;
                    default:
                        System.out.println("Invalid choice! Enter 1,2,3,4 or 5:");
                        edit_map_choice_flag = false;
                }
            } else {
                System.out.println("Invalid characters! Enter either 1,2,3,4 or 5: ");
                scanner.next();
            }
        }
    }

    /**
     * prompt user to get country details during map editing process
     * @param continent name of the continent to add the country to
     * @throws IOException on user input
     */
    private void inputCountryWhileEditingMap(String continent) throws IOException {
        System.out.println("Enter the new country Along with with their neighbours separated by comma starting a new line for each country");
        boolean new_country_flag = false;
        while (!new_country_flag) {
            String[] country_with_neighbours = bufferedReader.readLine().split(",");
            String new_country = country_with_neighbours[0];
            if (!this.containsCountry(new_country)) {

                ArrayList<String> temp_list = new ArrayList<String>();
                for (int i = 1; i < country_with_neighbours.length; i++) {
                    if (!country_with_neighbours[i].equals(new_country)) {
                        temp_list.add(country_with_neighbours[i]);
                        new_country_flag = true;
                    } else {
                        System.out.println("A country Cannot be neighbour to itself");
                    }
                }
                adjCountries.put(new_country, temp_list);
                saveContinentWithCountries(continent, new_country);
                System.out.println("**New Country Added**");
            } else {
                System.out.println("Country already exists! Please enter again: ");
            }
        }


    }

    /**
     * Check if the a country is already present in the map
     * @param country name of the country
     * @return true if country is present in the map, else return false
     */
    private boolean containsCountry(String country) {
        return adjCountries.containsKey(country);
    }


    public int noOfCountriesPlayerOwns(String playerName) {
    	int count=0;
    	for(int i=0;i<countries.size();i++) {
    		if(countries.get(i).getBelongsTo().equals(playerName))
    			count++;
    	}
		return count;
    }

    public ArrayList<String> continentsOfGivenCountries(ArrayList<String> playerCountries){
        ArrayList<String> controlledContinents=new ArrayList<>();
        //get continentsWithCountries HashMap
        //if any country of a continent is in playerCountries, then add continent in the arralist
        Set<String> continents = continentsWithCountries.keySet();
        for(String continent:continents){
            int countCountries=0;
            for(String country:continentsWithCountries.get(continent)){
                if(playerCountries.contains(country)){
                    countCountries++;
                }
            }
            if(countCountries==continentsWithCountries.get(continent).size()){
                controlledContinents.add(continent);
            }
        }
        return controlledContinents;
    }

}
