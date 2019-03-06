package map;

import java.io.*;
import java.util.*;

import country.Country;
import player.Player;

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
    private int no_of_continents;

    /**
     * Holds the Number of Adjacent countries (neighbours) for each country in the map.
     */
    private int no_of_adjacent_countries;

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
    private LinkedHashMap<String, ArrayList<String>> adj_countries;

    /**
     * LinkedHashMap Which stores the Continent along with the number of countries in it.
     */
    private LinkedHashMap<String, Integer> continent_with_no_of_countries;

    /**
     * LinkedHashMap that holds Continents and the countries in it
     */
    private LinkedHashMap<String, ArrayList<String>> continents_with_countries;

    /**
     * Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines
     */
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * To read and parse various primitive values.
     */
    private Scanner scan = new Scanner(System.in);

    /**
     * Getter for field no_of_continents- returns the value of number of continents
     * @return An integer value of number of continents in the map
     */
    public int getNo_of_continents() {
        return no_of_continents;
    }

    /**
     * Setter for field no_of_continents- assigns the number of countries
     * @param no_of_continents Integer value to set for number of countries
     */
    public void setNo_of_continents(int no_of_continents) {
        this.no_of_continents = no_of_continents;
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
     * Getter for field no_of_adjacent_countries- returns the value of countries
     * @return Array List of countries in the map
     */
    public int getNo_of_adjacent_countries() {
        return no_of_adjacent_countries;
    }

    /**
     * Setter for field no_of_adjacent_countries- assigns the number of adjacent countries
     * @param no_of_adjacent_countries Integer value to set for number of adjacent countries
     */
    public void setNo_of_adjacent_countries(int no_of_adjacent_countries) {
        this.no_of_adjacent_countries = no_of_adjacent_countries;
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
     * Getter for field adj_countries- returns the all countries and its neighbouring/adjacent countries
     * @return adj_countries LinkedHashMap of String and ArrayList for country and its adjacent countries
     */
    public LinkedHashMap<String, ArrayList<String>> getAdj_countries() {
        return adj_countries;
    }

    /**
     * Setter for field adj_countries- assigns the value of countries and its adjacent countries
     * @param adj_countries LinkedHashMap of String and ArrayList to set the country and its neighbours
     */
    public void setAdj_countries(LinkedHashMap<String, ArrayList<String>> adj_countries) {
        this.adj_countries = adj_countries;
    }

    /**
     * Getter for field continent_with_no_of_countries- returns all continents and number of countries in it
     * @return continent_with_no_of_countries LinkedHashMap of String and Integer for continents and its number of countries in it
     */
    public LinkedHashMap<String, Integer> getContinent_with_no_of_countries() {
        return continent_with_no_of_countries;
    }

    /**
     * Setter for field continent_with_no_of_countries- assigns the value of continents and its number of countries in it
     * @param continent_with_no_of_countries LinkedHashMap of String and Integer to set continents and its countries
     */
    public void setContinent_with_no_of_countries(LinkedHashMap<String, Integer> continent_with_no_of_countries) {
        this.continent_with_no_of_countries = continent_with_no_of_countries;
    }

    /**
     * Getter for field continents_with_countries- returns all the continents along with their countries
     * @return continents_with_countries LinkedHashMap of String and ArrayList for continents and its countries
     */
    public LinkedHashMap<String, ArrayList<String>> getContinents_with_countries() {
        return continents_with_countries;
    }

    /**
     * Setter for field continents_with_countries- assigns the value of continents and its countries
     * @param continents_with_countries LinkdedHashMap of String and ArrayList to set continents and its countries
     */
    public void setContinents_with_countries(LinkedHashMap<String, ArrayList<String>> continents_with_countries) {
        this.continents_with_countries = continents_with_countries;
    }

    /**
     * Creates a riskMap
     */
    public RiskMap() {
        continents = new LinkedHashMap<String, Integer>();
        adj_countries = new LinkedHashMap<String, ArrayList<String>>();
        continent_with_no_of_countries = new LinkedHashMap<String, Integer>();
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
            if (scan.hasNextInt()) {
                continent_flag = true;
                no_of_continents = scan.nextInt();
                System.out.println("Enter each Continent along with its control value separated with =");
                parseContinents(no_of_continents);
            } else {
                System.out.println("Invalid characters! Enter again :");
                scan.next();
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
            String continent_with_control_value = br.readLine();
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
        continents_with_countries=new LinkedHashMap<>();
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

        //TODO Validations
        System.out.println("Enter the Number of countries in " + continent);
        boolean no_of_countries_flag=false;
        while (!no_of_countries_flag){
            if(scan.hasNextInt()){
                no_of_countries_flag=true;
                continent_with_no_of_countries.put(continent, scan.nextInt());
                System.out.println(
                        "Enter all the Countries Along with with their neighbours separated by comma starting a new line for each country");
                ArrayList<String> countries = new ArrayList<>();

                outer:
                for (int j = 0; j < continent_with_no_of_countries.get(continent); j++) {

                    String[] temp = br.readLine().split(",");
                    
                    for(int i=0;i<temp.length;i++)
                    {
                    	if(temp[i].trim().length()<1)
                    	{
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
                    adj_countries.put(temp[0], temp_list);
                    countries.add(temp[0]);
                }
                continents_with_countries.put(continent, countries);
            }else{
                System.out.println("Invalid characters! Please enter only numbers. Try Again: ");
                scan.next();
            }
        }
    }


    /**
     * Writes and parses the Risk Map to a text file.
     * @throws IOException on user input
     */
    public void writeTheMapToTheTextFile() throws IOException {
        FileWriter fw = new FileWriter("OutputMap.txt");
        PrintWriter pw = new PrintWriter(fw);

        pw.println("[Continents]");
        for (String continent : continents.keySet()) {
            pw.write(continent + "=" + continents.get(continent));
            pw.println();
        }
        pw.println("[Territories]");
        for (String country : adj_countries.keySet()) {
            pw.write(country + "," + "0,0,");
            for (int i = 0; i < adj_countries.get(country).size(); i++) {
                pw.write(adj_countries.get(country).get(i) + ",");
            }
            pw.println();
        }
        fw.close();
        pw.close();
    }

    /**
     * Randomly assign countries in the map to all the players
     * @param players ArrayList of players of the game
     * @param no_of_players Number of players of the game
     */
    public void assignPlayersToCountries(ArrayList<Player> players, int no_of_players) {
        countries = new ArrayList<>();
        for (String country_name : adj_countries.keySet()) {
            int rand_temp = randInt(0, no_of_players - 1);
            String player_name=players.get(rand_temp).getPlayer_name();
            countries.add(new Country(country_name, player_name, 0));
        }
    }

    /**
     * Generate a random integer between two integers (range)
     * @param min minimum value of the range
     * @param max maximum value of the range
     * @return a random integer
     */
    private static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Set the start node and end node of adjacent countries data structure to initiate the Breadth First Search
     * @return if map is connected it returns true, else it returns false
     */
    public boolean isMapConnected() {
        //TODO : what other validations?
        Map.Entry<String, ArrayList<String>> first_entry = adj_countries.entrySet().iterator().next();
        String start = first_entry.getKey();
        Map.Entry<String, ArrayList<String>> last_entry = (Map.Entry<String, ArrayList<String>>) adj_countries.entrySet().toArray()[adj_countries.size() - 1];
        String end = last_entry.getKey();
        return breadthFirstSearch(adj_countries, start, end);

    }

    /**
     * Traverses the countries with adjacent countries to check if all the nodes are connected
     * @param adjList LinkedHashMap of countries and their adjacent countries
     * @param start First country in the list
     * @param end Last country in the list
     * @return true if the algorithm is able to traverse from first to last node, else returns false
     */
    public boolean breadthFirstSearch(Map<String, ArrayList<String>> adjList, String start, String end) {
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
    public void loadMap(String filename) throws IOException {
        //TODO: validations here
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
        continents_with_countries = new LinkedHashMap<>();
        String line = bir.readLine();
        while (line != null) {
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
                adj_countries.put(country, temp_al);
            }
            line = bir.readLine();
        }
    }

    /**
     * Store country in a continent
     * @param continent name of the continent
     * @param country name of the country
     */
    private void saveContinentWithCountries(String continent, String country) {
        ArrayList<String> countries = new ArrayList<>();
        if (continents_with_countries.containsKey(continent)) {
            countries = continents_with_countries.get(continent);
            countries.add(country);
            continents_with_countries.put(continent, countries);
        } else {
            countries.add(country);
            continents_with_countries.put(continent, countries);
        }
    }

    /**
     * Driver method to edit the map
     * @throws IOException on user input
     */
    public void editMap() throws IOException {
        System.out.println("Continents and its countries: " + this.continents_with_countries);
        System.out.println("Countries and its neighbours:" + this.adj_countries);
        System.out.println();
        System.out.println("1.Add Continent\n2.Add Country\n3.Remove Continent\n4.Remove Country\n5.Quit Edit");
        boolean edit_map_choice_flag = false;
        while (!edit_map_choice_flag) {
            if (scan.hasNextInt()) {
                edit_map_choice_flag = true;
                int edit_map_choice = scan.nextInt();
                switch (edit_map_choice) {
                    case 1:
                        System.out.println("Enter one Continent along with its control value separated with =");
                        String continent_name = parseContinents(1);
                        inputCountriesAndNeighbours(continent_name);
                        break;
                    case 2:
                        System.out.println("** Continents and their countries **\n" + continents_with_countries);
                        System.out.println("Enter the name of the continent to which you want to add the country: ");
                        boolean old_continent_flag = false;
                        while (!old_continent_flag) {
                            String old_continent = br.readLine();
                            if (continents_with_countries.containsKey(old_continent)) {
                                old_continent_flag = true;
                                System.out.println("Countries of the continent- " + old_continent + " : " + continents_with_countries.get(old_continent));
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
                            String continent_to_remove = br.readLine();
                            if (continents.containsKey(continent_to_remove)) {
                                continent_flag = true;
                                ArrayList<String> countries = continents_with_countries.get(continent_to_remove);
                                for (String country : countries) {
                                    adj_countries.remove(country);
                                }
                                continent_with_no_of_countries.remove(continent_to_remove);
                                continents_with_countries.remove(continent_to_remove);
                                System.out.println("**Continent removed**");
                            } else {
                                System.out.println("Continent does not exist in the map! Please enter again: ");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Countries and their neighbours: "+adj_countries);
                        System.out.println("Enter the country to be removed: ");
                        boolean country_flag=false;
                        while (!country_flag){
                            String country_to_remove=br.readLine();
                            if(adj_countries.containsKey(country_to_remove)){
                                country_flag=true;
                                adj_countries.remove(country_to_remove);
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
                scan.next();
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
            String[] country_with_neighbours = br.readLine().split(",");
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
                adj_countries.put(new_country, temp_list);
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
        return adj_countries.containsKey(country);
    }



}
