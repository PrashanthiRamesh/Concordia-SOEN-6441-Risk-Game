package model;


import util.Util;

import java.io.*;
import java.util.*;


/**
 * RiskMap Class Loads the maps form the Console and from the file.
 *
 * @author Maqsood
 * @version 1.1
 * @since  1.0
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
    public ArrayList<Country> countries;

    /**
     * LinkedHashMap Which Stores the Continents along with their control Values.
     */
    public LinkedHashMap<String, Integer> continents;

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
    public LinkedHashMap<String, Integer> continentWithNoOfCountries;

    /**
     * LinkedHashMap that holds Continents and the countries in it
     */
    public LinkedHashMap<String, ArrayList<String>> continentsWithCountries;

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

    /**
     * Getter for field continentsWithCountriesAndNeighbours- returns all the continents along with their countries and its neighbours
     * @return continentsWithCountriesAndNeighbours LinkedHashMap of String and LinkedHashMap to set continents and its countries and its neighbours
     */
    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> getContinentsWithCountriesAndNeighbours() {
        return continentsWithCountriesAndNeighbours;
    }

    /**
     * Setter for field continentsWithCountriesAndNeighbours- returns all the continents along with their countries and its neighbours
     * @param continentsWithCountriesAndNeighbours LinkedHashMap of String and LinkedHashMap to set continents and its countries and its neighbours
     */
    public void setContinentsWithCountriesAndNeighbours(LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> continentsWithCountriesAndNeighbours) {
        this.continentsWithCountriesAndNeighbours = continentsWithCountriesAndNeighbours;
    }

    /**
     * Creates a riskMap
     */
    public RiskMap() {
        continents = new LinkedHashMap<String, Integer>();
        countries=new ArrayList<Country>();
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
        boolean continentFlag = false;
        while (!continentFlag) {
            if (scanner.hasNextInt()) {
                continentFlag = true;
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
     * @param numberOfContinents number of continents to iterate over to get its values
     * @return name of the continent
     * @throws IOException on user input
     */
    private String parseContinents(int numberOfContinents) throws IOException {
        String returnContinent = "";
        for (int i = 0; i < numberOfContinents; i++) {
            String continentWithControlValue = bufferedReader.readLine();
            String[] continentAndControlValue = continentWithControlValue.split("=");
            if (!continents.containsKey(continentAndControlValue[0])) {
                if (continentAndControlValue.length != 2) {
                    System.out.println("Incorrect Input : Provide continent along with its control value");
                    i--;
                } else if (!continentAndControlValue[1].matches(".*\\d+.*")) {
                    System.out.println(" Incorrect Input :The Control Value should be a Numeric");
                    i--;
                } else {
                    continents.put(continentAndControlValue[0], Integer.parseInt(continentAndControlValue[1]));
                    if (i == 0) {
                        returnContinent = continentAndControlValue[0];
                    }
                }
            } else {
                System.out.println("Continent Already Exists: please Re enter with correct Continent name");
                i--;
            }
        }
        return returnContinent;
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
        boolean noOfCountriesFlag=false;
        LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours = new LinkedHashMap<>();
        while (!noOfCountriesFlag){
            if(scanner.hasNextInt()){
                noOfCountriesFlag=true;
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
                    ArrayList<String> tempList = new ArrayList<String>();
                    for (int i = 1; i < temp.length; i++) {
                        if (!temp[i].equals(temp[0]))
                            tempList.add(temp[i]);
                        else {
                            System.out.println("A country Cannot be neighbour to itself");
                            j--;
                        }
                    }
                    adjCountries.put(temp[0], tempList);
                    countries.add(temp[0]);
                    countriesWithNeighbours.put(temp[0],tempList);

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
     * @return string continents for testing
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
     * @param noOfPlayers Number of players of the game
     */
    public void assignPlayersToCountries(ArrayList<Player> players, int noOfPlayers) {
        countries = new ArrayList<>();
        for (String countryName : adjCountries.keySet()) {
            int randInt = Util.randInt(0, noOfPlayers - 1);
            String playerName =players.get(randInt).getPlayerName();
            countries.add(new Country(countryName, playerName, 0));
        }
    }

    /**
     * Set the start node and end node of adjacent countries data structure to initiate the Breadth First Search
     * @return if map is connected it returns true, else it returns false
     */
    public boolean isMapConnected() {
        Map.Entry<String, ArrayList<String>> firstEntry = adjCountries.entrySet().iterator().next();
        String start = firstEntry.getKey();
        Map.Entry<String, ArrayList<String>> lastEntry = (Map.Entry<String, ArrayList<String>>) adjCountries.entrySet().toArray()[adjCountries.size() - 1];
        String end = lastEntry.getKey();
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
        boolean continentsConnectedFlag=true;
        for(String continent:continents){
            LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours=continentsWithCountriesAndNeighbours.get(continent);
            Map.Entry<String, ArrayList<String>> firstEntry = countriesWithNeighbours.entrySet().iterator().next();
            String start = firstEntry.getKey();
            Map.Entry<String, ArrayList<String>> lastEntry = (Map.Entry<String, ArrayList<String>>) countriesWithNeighbours.entrySet().toArray()[countriesWithNeighbours.size() - 1];
            String end = lastEntry.getKey();
            List<String> countries = new ArrayList<>(countriesWithNeighbours.keySet());
            for(String countryWithNeighbours: countriesWithNeighbours.keySet()){
                ArrayList<String> tempCountries=new ArrayList<>();
                for(String neighbour:countriesWithNeighbours.get(countryWithNeighbours)){
                    if(countries.contains(neighbour)){
                        tempCountries.add(neighbour);
                    }
                }
                countriesWithNeighbours.put(countryWithNeighbours,tempCountries);
            }
            if(!breadthFirstSearch(countriesWithNeighbours, start, end)){
                System.out.println("\nContinent- "+continent+" is not a connected sub-graph");
                continentsConnectedFlag=false;
                break;
            }else{
                System.out.println("\nContinent- "+continent+" is a connected sub-graph");
            }
        }
        return continentsConnectedFlag;
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
     * @param filename name of the map file
     * @return first neighbour of country for testing
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
                String[] tempArr = temp.split("=");
                continents.put(tempArr[0], Integer.parseInt(tempArr[1]));
            }
        }
        while (!bir.readLine().trim().equals("[Territories]")) {

        }
        String line = bir.readLine();
        while (line != null) {
            LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours=new LinkedHashMap<>();
            String[] temp = line.split(",");
            if (temp.length > 1) {
                ArrayList<String> tempAl = new ArrayList<String>();
                for (int i = 4; i < temp.length; i++) {
                    tempAl.add(temp[i]);
                }
                //save continents with its countries logic here
                String continent = temp[3];
                String country = temp[0];
                saveContinentWithCountries(continent, country);
                adjCountries.put(country, tempAl);
                countriesWithNeighbours.put(country,tempAl);
                saveContinentWithCountriesAndNeighbours(continent,country,tempAl);
            }
            line = bir.readLine();
        }
        
        return adjCountries.values().iterator().next().get(0);
        
    }

    /**
     * Store country in a continent
     * @param continent name of the continent
     * @param country name of the country
     */
    private void saveContinentWithCountries(String continent, String country) {
        ArrayList<String> countries = new ArrayList<>();
        if (continentsWithCountriesAndNeighbours.containsKey(continent)) {
            countries = continentsWithCountries.get(continent);
            countries.add(country);
            continentsWithCountries.put(continent, countries);
        } else {
            countries.add(country);
            continentsWithCountries.put(continent, countries);
        }
    }

    private void saveContinentWithCountriesAndNeighbours(String continent, String country, ArrayList<String> countryNeighbours) {
        LinkedHashMap<String, ArrayList<String>> countriesAndNeighbours = new LinkedHashMap<>();
        if (continentsWithCountriesAndNeighbours.containsKey(continent)) {
            countriesAndNeighbours = continentsWithCountriesAndNeighbours.get(continent);
            countriesAndNeighbours.put(country,countryNeighbours);
            continentsWithCountriesAndNeighbours.put(continent, countriesAndNeighbours);
        } else {
            countriesAndNeighbours.put(country,countryNeighbours);
            continentsWithCountriesAndNeighbours.put(continent, countriesAndNeighbours);
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
        boolean editMapChoiceFlag = false;
        while (!editMapChoiceFlag) {
            if (scanner.hasNextInt()) {
                editMapChoiceFlag = true;
                int editMapChoice = scanner.nextInt();
                switch (editMapChoice) {
                    case 1:
                        System.out.println("Enter one Continent along with its control value separated with =");
                        String continentName = parseContinents(1);
                        inputCountriesAndNeighbours(continentName);
                        break;
                    case 2:
                        System.out.println("** Continents and their countries **\n" + continentsWithCountries);
                        System.out.println("Enter the name of the continent to which you want to add the country: ");
                        boolean oldContinentFlag = false;
                        while (!oldContinentFlag) {
                            String oldContinent = bufferedReader.readLine();
                            if (continentsWithCountries.containsKey(oldContinent)) {
                                oldContinentFlag = true;
                                System.out.println("Countries of the continent- " + oldContinent + " : " + continentsWithCountries.get(oldContinent));
                                inputCountryWhileEditingMap(oldContinent);
                            } else {
                                System.out.println("Continent is not present! Please enter again: ");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Continents with their control values: " + continents);
                        System.out.println("Enter continent to be removed: ");
                        boolean continentFlag = false;
                        while (!continentFlag) {
                            String continentToRemove = bufferedReader.readLine();
                            if (continents.containsKey(continentToRemove)) {
                                continentFlag = true;
                                ArrayList<String> countries = continentsWithCountries.get(continentToRemove);
                                for (String country : countries) {
                                    adjCountries.remove(country);
                                }
                                continentWithNoOfCountries.remove(continentToRemove);
                                continentsWithCountries.remove(continentToRemove);
                                System.out.println("**Continent removed**");
                            } else {
                                System.out.println("Continent does not exist in the map! Please enter again: ");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("Countries and their neighbours: "+ adjCountries);
                        System.out.println("Enter the country to be removed: ");
                        boolean countryFlag =false;
                        while (!countryFlag){
                            String countryToRemove= bufferedReader.readLine();
                            if(adjCountries.containsKey(countryToRemove)){
                                countryFlag =true;
                                adjCountries.remove(countryToRemove);
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
                        editMapChoiceFlag = false;
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
        boolean newCountryFlag = false;
        while (!newCountryFlag) {
            String[] countryWithNeighbours = bufferedReader.readLine().split(",");
            String newCountry = countryWithNeighbours[0];
            if (!this.containsCountry(newCountry)) {

                ArrayList<String> tempList = new ArrayList<String>();
                for (int i = 1; i < countryWithNeighbours.length; i++) {
                    if (!countryWithNeighbours[i].equals(newCountry)) {
                        tempList.add(countryWithNeighbours[i]);
                        newCountryFlag = true;
                    } else {
                        System.out.println("A country Cannot be neighbour to itself");
                    }
                }
                adjCountries.put(newCountry, tempList);
                saveContinentWithCountries(continent, newCountry);
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


    /**
     * This method is used to get the number of countries a player owns by player name
     * @param playerName name of the player
     * @return integer value of the country count of player
     */
    public int noOfCountriesPlayerOwns(String playerName) {
    	int count=0;
    	for(int i=0;i<countries.size();i++) {
    		if(countries.get(i).getBelongsTo().equals(playerName))
    			count++;
    	}
		return count;
    }

    /**
     * This method is used to return a list of continents a player controls in the map
     * @param playerCountries list of countries of player
     * @return list of continents the player controls
     */
    public ArrayList<String> continentsControlledByPlayer(ArrayList<String> playerCountries){
        ArrayList<String> controlledContinents=new ArrayList<>();
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
