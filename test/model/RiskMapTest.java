package model;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RiskMapTest {

    private RiskMap map;
    private Country country;
    private Player gamePlay;

    @Before
    public void setUp() throws Exception {

        map = new RiskMap();
        country = new Country();
        gamePlay = new Player();
    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testNotConnectedContinent() {
        ArrayList<String> c1 = new ArrayList<String>();
        ArrayList<String> c2 = new ArrayList<String>();
        ArrayList<String> c3 = new ArrayList<String>();
        LinkedHashMap<String, ArrayList<String>> continentsWithCountries=new LinkedHashMap<>();
        c1.add("a");c1.add("b");c1.add("c");
        c2.add("d"); c2.add("e");
        c3.add("f"); c3.add("g");
        continentsWithCountries.put("C1",c1);
        continentsWithCountries.put("C2",c2);
        continentsWithCountries.put("C3",c3);
        map.setContinentsWithCountries(continentsWithCountries);

        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> continentsandcountriesWithNeighbours=new LinkedHashMap<>();
        LinkedHashMap<String, ArrayList<String>> countriesWithNeighbours=new LinkedHashMap<>();
        ArrayList<String> neighbours=new ArrayList<>();
        neighbours.add("b"); neighbours.add("d");
        countriesWithNeighbours.put("a",neighbours);
        neighbours=new ArrayList<>();
        neighbours.add("a");
        countriesWithNeighbours.put("b",neighbours);
        neighbours=new ArrayList<>();
        countriesWithNeighbours.put("c",neighbours);
        continentsandcountriesWithNeighbours.put("C1", countriesWithNeighbours);

        countriesWithNeighbours=new LinkedHashMap<>();
        neighbours=new ArrayList<>();
        neighbours.add("e"); neighbours.add("f");
        countriesWithNeighbours.put("d",neighbours);
        neighbours=new ArrayList<>();
        neighbours.add("d");
        countriesWithNeighbours.put("e",neighbours);
        continentsandcountriesWithNeighbours.put("C2", countriesWithNeighbours);

        countriesWithNeighbours=new LinkedHashMap<>();
        neighbours=new ArrayList<>();
        neighbours.add("g"); neighbours.add("a");
        countriesWithNeighbours.put("f",neighbours);
        neighbours=new ArrayList<>();
        neighbours.add("f");
        countriesWithNeighbours.put("g",neighbours);
        continentsandcountriesWithNeighbours.put("C3", countriesWithNeighbours);


        map.setContinentsWithCountriesAndNeighbours(continentsandcountriesWithNeighbours);
        assertFalse(map.areContinentsConnected());
    }

    @Test
    public void testConnectedMap() {
        ArrayList<String> al1 = new ArrayList<String>();
        ArrayList<String> al2 = new ArrayList<String>();
        ArrayList<String> al3 = new ArrayList<String>();
        al1.add("b");
        al1.add("c");
        map.adjCountries.put("a", al1);
        al2.add("c");
        al2.add("a");
        map.adjCountries.put("b", al2);
        al3.add("b");
        al3.add("a");
        map.adjCountries.put("c", al3);
        assertTrue(map.isMapConnected());
    }

    @Test
    public void testInputFileConnection() throws IOException {
        assertEquals("AB", map.loadMap("input.txt"));
    }


    @Test
    public void testWritingfile() throws IOException {
        assertEquals("[Continents]", map.writeTheMapToTheTextFile());

    }


    @Test
    public void testBFS() {
        ArrayList<String> al1 = new ArrayList<String>();
        ArrayList<String> al2 = new ArrayList<String>();
        ArrayList<String> al3 = new ArrayList<String>();
        al1.add("b");
        al1.add("c");
        map.adjCountries.put("a", al1);
        al2.add("c");
        al2.add("a");

        map.adjCountries.put("b", al2);
        al3.add("b");
        al3.add("a");

        map.adjCountries.put("c", al3);

        assertTrue(map.breadthFirstSearch(map.adjCountries, "a", "c"));
    }

    @Test
    public void testArmies() {
        ArrayList<Country> al = new ArrayList<Country>();
        Country c = new Country("India", "Maqsood", 5);
        al.add(c);
        gamePlay.map.setCountries(al);

        assertEquals(5, gamePlay.noOfArmiesInCountry("India", gamePlay.map));

    }



}