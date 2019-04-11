package model;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    private Player player1;

    private Player player2;

    private Player player3;

    private RiskMap map;


    @Before
    public void setUp() {
        player = new Player();
        map = new RiskMap();
    }


    /*
     * Reinforcement armies calculation
     */

    @Test
    public void testReinfoArmiesForContinentsControlled() {
        LinkedHashMap<String, ArrayList<String>> continentsWithCountries = new LinkedHashMap<>();
        ArrayList<String> countries = new ArrayList<>();
        countries.add("a");
        countries.add("b");
        continentsWithCountries.put("C1", countries);
        countries = new ArrayList<>();
        countries.add("c");
        countries.add("d");
        continentsWithCountries.put("C2", countries);
        countries = new ArrayList<>();
        countries.add("e");
        countries.add("f");
        continentsWithCountries.put("C3", countries);
        player.setArmies(13);
        map.setContinentsWithCountries(continentsWithCountries);

        LinkedHashMap<String, Integer> continentsControlValue = new LinkedHashMap<>();
        continentsControlValue.put("C1", 4);
        continentsControlValue.put("C2", 12);
        continentsControlValue.put("C3", 9);
        map.setContinents(continentsControlValue);

        ArrayList<String> playerCountries = new ArrayList<>();
        playerCountries.add("a");
        playerCountries.add("b");
        playerCountries.add("e");
        playerCountries.add("f");


        player.setArmiesForContinentsControlled(player, playerCountries, 0);

        assertEquals(13, player.getArmies());
    }

    @Test
    public void validInitialArmies() {
        int[] initial_armies = {40, 35, 30, 25, 20};
        for (int i = 2; i <= 6; i++) {
            int armies = player.calculateInitialArmies(i);
            int initial_army = initial_armies[i - 2];

            assertEquals(armies, initial_army);
        }

    }

    @Test
    public void invalidInitialArmies() {
        int[] initial_armies = {-1, 8, 1000, -100, 0};
        for (int i = 2; i <= 6; i++) {
            int armies = player.calculateInitialArmies(i);
            int initial_army = initial_armies[i - 2];

            assertNotEquals(armies, initial_army);
        }
    }

    @Test
    public void validReinforcementArmies() {

        int[] total_no_of_countries = {99, 98, 55, 66, 43};
        int[] reinforcement_armies = {33, 32, 18, 22, 14};
        for (int i = 0; i < total_no_of_countries.length; i++) {
            int armies = Player.calculateReinforcementArmies(total_no_of_countries[i]);
            assertEquals(armies, reinforcement_armies[i]);
        }

    }

    @Test
    public void invalidReinforcementArmies() {

        int[] total_no_of_countries = {99, 98, 55, 66, 43};
        int[] reinforcement_armies = {34, 33, 19, 23, 15};
        for (int i = 0; i < total_no_of_countries.length; i++) {
            int armies = Player.calculateReinforcementArmies(total_no_of_countries[i]);
            assertNotEquals(armies, reinforcement_armies[i]);
        }

    }

    /*
     * Startup phase
     */

    @Test
    public void testSetInitialArmies() {

        //3 players
        ArrayList<String> playerCountries = new ArrayList<>();
        playerCountries.add("a");
        playerCountries.add("b");
        ArrayList<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card("Infantry", 1));
        player1 = new Player("prash", 0, playerCountries, playerCards, 1, 0, 0);
        playerCountries = new ArrayList<>();
        playerCountries.add("c");
        playerCountries.add("d");
        player2 = new Player("gal", 0, playerCountries, playerCards, 1, 0, 0);
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("a", "prash", 0));
        countries.add(new Country("b", "prash", 0));
        countries.add(new Country("c", "gal", 0));
        countries.add(new Country("d", "gal", 0));
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players = player.setInitialArmies(players, countries);
        assertEquals(40, players.get(0).getArmies());
        assertEquals(40, players.get(1).getArmies());

    }

    /*
     * Attack phase
     */

    @Test
    public void testAttacker() {
        assertEquals(1, player.getAttackerDice(1, false));
        assertNotEquals(10, player.getAttackerDice(1, false));

    }

    @Test
    public void testDefender() {
        assertEquals(1, player.getDefenderDice(0));
        assertEquals(2, player.getDefenderDice(10));
        assertNotEquals(1, player.getDefenderDice(1));
    }

    @Test
    public void testMoveArmiesAfterConquering() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("a", "prash", 0));
        countries.add(new Country("b", "prash", 0));
        countries.add(new Country("c", "gal", 0));
        countries.add(new Country("d", "gal", 0));
        ArrayList<String> playerCountries = new ArrayList<>();
        playerCountries.add("a");
        playerCountries.add("b");
        map.setCountries(countries);
        assertTrue(player.moveAfterConquering(player, map, countries, playerCountries, 10, "d","a",5 ));
    }

    @Test
    public void testEndOfGame() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("a", "prash", 0));
        countries.add(new Country("b", "prash", 0));
        countries.add(new Country("c", "gal", 0));
        countries.add(new Country("d", "gal", 0));
        int armiesLeft=player.getArmiesLeftForPlayer(player,countries,0);
        assertEquals(0,armiesLeft);
    }

    /*
     * Fortification phase
     */

    @Test
    public void fortification() {
        ArrayList<String> playerCountries = new ArrayList<>();
        playerCountries.add("a");
        playerCountries.add("b");
        player.setCountries(playerCountries);
        assertTrue(player.removePlayerIfZeroCountriesOwned(player));

    }

}