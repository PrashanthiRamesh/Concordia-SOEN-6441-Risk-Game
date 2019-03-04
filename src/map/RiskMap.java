package map;

import java.io.*;
import java.util.*;

import country.Country;
import player.Player;

/**
 * RiskMap Class Loads the maps form the Console and form the file.
 *
 * @author Maqsood
 */
public class RiskMap {

    /**
     * Holds the Number of continents in the RiskMap.
     */
    private int no_of_continents;

    /**
     * Holds the Number of Adjacent countries mentioned for each country.
     */
    private int no_of_adjacent_countries;

    /**
     * LinkedHashMap that holds all the countries in the map
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

    private LinkedHashMap<String, ArrayList<String>> continents_with_countries;

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Scanner scan = new Scanner(System.in);

    public int getNo_of_continents() {
        return no_of_continents;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public int getNo_of_adjacent_countries() {
        return no_of_adjacent_countries;
    }

    public LinkedHashMap<String, Integer> getContinents() {
        return continents;
    }

    public LinkedHashMap<String, ArrayList<String>> getAdj_countries() {
        return adj_countries;
    }

    public LinkedHashMap<String, Integer> getContinent_with_no_of_countries() {
        return continent_with_no_of_countries;
    }

    public void setNo_of_continents(int no_of_continents) {
        this.no_of_continents = no_of_continents;
    }

    public void setNo_of_adjacent_countries(int no_of_adjacent_countries) {
        this.no_of_adjacent_countries = no_of_adjacent_countries;
    }

    public void setContinents(LinkedHashMap<String, Integer> continents) {
        this.continents = continents;
    }

    public void setAdj_countries(LinkedHashMap<String, ArrayList<String>> adj_countries) {
        this.adj_countries = adj_countries;
    }

    public void setContinent_with_no_of_countries(LinkedHashMap<String, Integer> continent_with_no_of_countries) {
        this.continent_with_no_of_countries = continent_with_no_of_countries;
    }

    public LinkedHashMap<String, ArrayList<String>> getContinents_with_countries() {
        return continents_with_countries;
    }

    public void setContinents_with_countries(LinkedHashMap<String, ArrayList<String>> continents_with_countries) {
        this.continents_with_countries = continents_with_countries;
    }

    public RiskMap() {
        continents = new LinkedHashMap<String, Integer>();
        adj_countries = new LinkedHashMap<String, ArrayList<String>>();
        continent_with_no_of_countries = new LinkedHashMap<String, Integer>();
    }

    /**
     * populateMap loads the map form the console.
     *
     * @throws IOException
     */
    public void populateMap() throws IOException {
        addContinents();
        addCountriesWithNeighbours();
    }

    private void addContinents() throws IOException {
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

    private String parseContinents(int continents_count) throws IOException {
        String return_continent = "";
        for (int i = 0; i < continents_count; i++) {
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


    private void addCountriesWithNeighbours() throws IOException {
        continents_with_countries=new LinkedHashMap<>();
        for (String continent : continents.keySet()) {
            parseCountries(continent);
        }
    }

    private void parseCountries(String new_continent) throws IOException {
        //TODO Validations
        System.out.println("Enter the Number of countries in " + new_continent);
        boolean no_of_countries_flag=false;
        while (!no_of_countries_flag){
            if(scan.hasNextInt()){
                no_of_countries_flag=true;
                continent_with_no_of_countries.put(new_continent, scan.nextInt());
                System.out.println(
                        "Enter all the Countries Along with with their neighbours separated by comma starting a new line for each country");
                ArrayList<String> countries = new ArrayList<>();

                for (int j = 0; j < continent_with_no_of_countries.get(new_continent); j++) {
                    String[] temp = br.readLine().split(",");
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
                continents_with_countries.put(new_continent, countries);
            }else{
                System.out.println("Invalid characters! Please enter only numbers. Try Again: ");
                scan.next();
            }
        }

    }

    private void parseCountriesWhileEditingMap(String old_continent) {

    }

    /**
     * Writes the RiskMap to a text file.
     *
     * @throws IOException
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

    public void assignPlayersToCountries(ArrayList<Player> players, int no_of_players) {
        countries = new ArrayList<>();
        for (String var : adj_countries.keySet()) {
            int rand_temp = randInt(0, no_of_players - 1);
            countries.add(new Country(var, players.get(rand_temp).getPlayer_name(), 0));
        }

    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public boolean isMapConnected() {
        //map= adj_countries
        //check if connected- run a bfs
        //TODO : what other validations?
        Map.Entry<String, ArrayList<String>> first_entry = adj_countries.entrySet().iterator().next();
        String start = first_entry.getKey();
        Map.Entry<String, ArrayList<String>> last_entry = (Map.Entry<String, ArrayList<String>>) adj_countries.entrySet().toArray()[adj_countries.size() - 1];
        String end = last_entry.getKey();
        return bfsPath(adj_countries, start, end);
    }

    private static boolean bfsPath(Map<String, ArrayList<String>> adjList, String start, String end) {
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
     * Loads the RiskMap form the File.
     *
     * @throws IOException
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
            String temp[] = line.split(",");

            if (temp.length > 1) {
                ArrayList<String> temp_al = new ArrayList<String>();
                for (int i = 4; i < temp.length; i++) {
                    temp_al.add(temp[i]);
                }
                //save continents with its countries logic here
                String continent = temp[3];
                String country = temp[0];
                countryWithContinents(continent, country);
                adj_countries.put(country, temp_al);
            }
            line = bir.readLine();
        }

    }

    private void countryWithContinents(String continent, String country) {
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


    public void editMap() throws IOException {
        //display all continents and countries
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
                        parseCountries(continent_name);
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
                                addCountryWhileEditingMap(old_continent);
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

    private void addCountryWhileEditingMap(String continent) throws IOException {
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
                countryWithContinents(continent, new_country);
                System.out.println("**New Country Added**");
            } else {
                System.out.println("Country already exists! Please enter again: ");
            }
        }


    }

    private boolean containsCountry(String country) {
        if (adj_countries.containsKey(country)) {
            return true;
        }
        return false;
    }


}
