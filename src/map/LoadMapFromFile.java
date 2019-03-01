package map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Loads the RiskMap From The File
 *
 * @author Prashanthi
 */
public class LoadMapFromFile {

    /**
     * Stores the Continents along with their Control Values.
     */
    private LinkedHashMap<String, Integer> continents;

    /**
     * Stores The Countries along with their neighboring Countries.
     */
    private LinkedHashMap<String, ArrayList<String>> adj_countries;

    public LinkedHashMap<String, Integer> getContinents() {
        return continents;
    }

    public void setContinents(LinkedHashMap<String, Integer> continents) {
        this.continents = continents;
    }

    public LinkedHashMap<String, ArrayList<String>> getAdj_countries() {
        return adj_countries;
    }

    public void setAdj_countries(LinkedHashMap<String, ArrayList<String>> adj_countries) {
        this.adj_countries = adj_countries;
    }


    private String filename;

    /**
     * Constructor to Initialize the the class variables.
     */
    public LoadMapFromFile(String filename) {
        continents = new LinkedHashMap<String, Integer>();
        adj_countries = new LinkedHashMap<String, ArrayList<String>>();
        this.filename = filename;
    }

    /**
     * Loads the RiskMap form the File.
     *
     * @throws IOException
     */
    public void loapMap() throws IOException {
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
