package MapClassPackage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Prashanthi
 * Loads the Map From The File.
 */
public class LoadMapFromFile {
    
	/**
	 * Stores the Continents along with their Control Values.
	 */
	private HashMap<String, Integer> continents;
	/**
	 * Stores The Countries along with their neighboring Countries.
	 */
	private HashMap<String, ArrayList<String>> adj_countries;

	/**
	 * Constructor to Initialize the the class variables.
	 */
	private String filename;
	
	public LoadMapFromFile(String filename) {
		continents = new HashMap<String, Integer>();
		adj_countries = new HashMap<String, ArrayList<String>>();
		this.filename=filename;
	}

	/**
	 * Loads the Map form the File.
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
    
	/**
	 * Writes the Map to the File.
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

}
