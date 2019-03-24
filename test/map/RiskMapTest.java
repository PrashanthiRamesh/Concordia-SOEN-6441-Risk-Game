package map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.Player;
import model.RiskMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Country;
import util.Util;

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
    public void connectedGraph() {
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
    public void inputFileConnection() throws IOException {
        assertEquals("AB", map.loadMap("input.txt"));
    }

    @Test
    public void randomInteger() {
        int[] arr = {0, 1, 2, 3};

        int x = Arrays.binarySearch(arr, Util.randInt(0, 4));

        //assertEquals( 1, x);

    }


    @Test
    public void testWritingfile() throws IOException {
        assertEquals("[Continents]", map.writeTheMapToTheTextFile());

    }


    @Test
    public void TestBFS() {
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
    public void TestArmies() {
        ArrayList<Country> al = new ArrayList<Country>();
        Country c = new Country("India", "Maqsood", 5);
        al.add(c);
        gamePlay.map.setCountries(al);

        assertEquals(5, gamePlay.noOfArmiesInCountry("India"));


    }


}