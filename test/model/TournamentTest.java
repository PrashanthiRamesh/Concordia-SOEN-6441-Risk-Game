package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TournamentTest {
	
	ArrayList<Tournament> tournaments;
	
    @Before
    public void setUp() throws Exception {
    	tournaments=new ArrayList<Tournament>();
    }

    @After
    public void tearDown() throws Exception {
    	tournaments=null;

    }
    @Test
    public void tournamentDrawTest(){
    	
    	tournaments.add(new Tournament(1, true, null, "1.map"));
    	assertTrue(tournaments.get(0).isDraw );
    }
    
    @Test
    public void tournamentWinnerTest(){
    	tournaments=new ArrayList<Tournament>();
    	tournaments.add(new Tournament(1, false, new Player("Benevolent", 100, null, null, 10, 2, 4), "1.map"));
    	assertFalse(tournaments.get(0).isDraw );
    	assertEquals(tournaments.get(0).getWinner().getPlayerName(), "Benevolent");
    }
    
    @Test
    public void tournamentNotDrawTest(){

    	tournaments.add(new Tournament(1, false, null, "1.map"));
    	assertFalse(tournaments.get(0).isDraw );}
    
    @Test
    public void tournamentInvalidWinnerTest(){
    	tournaments=new ArrayList<Tournament>();
    	tournaments.add(new Tournament(1, false, new Player("Benevolent", 100, null, null, 10, 2, 4), "1.map"));
    	assertFalse(tournaments.get(0).isDraw );
    	assertNotEquals(tournaments.get(0).getWinner().getPlayerName(), "Cheater");
    }
}
