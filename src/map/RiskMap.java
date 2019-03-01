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
                for (int i = 0; i < no_of_continents; i++) {
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
                        }
                    } else {
                        System.out.println("Continent Already Exists: please Re enter with correct Continent name");
                        i--;
                    }
                }
            } else {
                System.out.println("Invalid characters! Enter again :");
                scan.next();
            }
        }

    }

    private void addCountriesWithNeighbours() throws IOException {
        //TODO: validations here
        for (String continent : continents.keySet()) {
            System.out.println("Enter the Number of countries in " + continent);
            continent_with_no_of_countries.put(continent, scan.nextInt());
            System.out.println(
                    "Enter the all the Countries Along with with their neighbours seperated by comma starting a new line for each country");
            for (int j = 0; j < continent_with_no_of_countries.get(continent); j++) {
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
            }

        }
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
            System.out.println(adj_countries);
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
        parents.put(start, "dummy"); // Say -1 is invalid
        boolean connected = false;
        while (!q.isEmpty()) {
            String nextVertex = q.poll();
            if (nextVertex.equals(end)) {
                connected = true;
                break;
            }
            for (String edge : adjList.get(nextVertex)) {
                if (!parents.containsKey(edge)) {
                    q.add(edge);
                    parents.put(edge, nextVertex);
                }
            }
        }
        return connected;
    }

    /**
     * Loads the RiskMap form the File.
     *
     * @throws IOException
     */
    public void loapMap(String filename) throws IOException {
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

        String line = bir.readLine();
        while (line != null) {
            String temp[] = line.split(",");
            if (temp.length > 1) {
                ArrayList<String> temp_al = new ArrayList<String>();
                for (int i = 4; i < temp.length; i++) {
                    temp_al.add(temp[i]);
                }
                adj_countries.put(temp[0], temp_al);
            }
            line = bir.readLine();
        }

    }


}
