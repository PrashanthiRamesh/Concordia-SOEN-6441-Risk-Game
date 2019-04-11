package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.GamePlay;
import model.Card;
import model.Player;
import model.RiskMap;

public class DriverTest {
	
	String mapFileName;
	
	RiskMap map;
	
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {


    }
    @Test
    public void saveGame() throws IOException{
    		mapFileName="1.map";
    		this.map = new RiskMap();
			this.map.loadMap(mapFileName.trim());
			this.map.writeTheMapToTheTextFile();
			ArrayList<Player> players=new ArrayList<Player>();
			ArrayList<String> countries=new ArrayList<String>();
			countries.add("c1");countries.add("c2");
			ArrayList<Card> cards= new ArrayList<Card>();
			cards.add(new Card("Infantry",1));
			players.add(new Player("Arya", 10, countries , cards, 1, 0, 0));
			countries=new ArrayList<String>();
			countries.add("c3");countries.add("c4");
			cards= new ArrayList<Card>();
			cards.add(new Card("Cavalry",2));
			players.add(new Player("Jon", 20, countries , cards, 0, 1, 0));
			countries=new ArrayList<String>();
			countries.add("c5");countries.add("c6");
			cards= new ArrayList<Card>();
			cards.add(new Card("Cannon",3));
			players.add(new Player("Sansa", 50, countries , cards, 0, 0, 1));
			assertTrue((new GamePlay(players,this.map)).saveMap());
    		
    }
    
    @Test
    public void loadSavedGame() throws IOException {
    	mapFileName="savedMap.txt";
    	mapFileName="1.map";
		this.map = new RiskMap();
		this.map.loadMap(mapFileName.trim());
		this.map.writeTheMapToTheTextFile();
    	ArrayList<Player> players=new ArrayList<Player>();
		ArrayList<String> countries=new ArrayList<String>();
		countries.add("c1");countries.add("c2");
		ArrayList<Card> cards= new ArrayList<Card>();
		cards.add(new Card("Infantry",1));
		players.add(new Player("Arya", 10, countries , cards, 1, 0, 0));
		countries=new ArrayList<String>();
		countries.add("c3");countries.add("c4");
		cards= new ArrayList<Card>();
		cards.add(new Card("Cavalry",2));
		players.add(new Player("Jon", 20, countries , cards, 0, 1, 0));
		countries=new ArrayList<String>();
		countries.add("c5");countries.add("c6");
		cards= new ArrayList<Card>();
		cards.add(new Card("Cannon",3));
		players.add(new Player("Sansa", 50, countries , cards, 0, 0, 1));
		GamePlay play=(new GamePlay(players,this.map));
		play.map=this.map;
    	Player player=(play).retrieveMap();
    	
    		assertEquals(player.players.get(0).getPlayerName(),"Arya");
    	
    }
    
    @Test
    public void saveAndLoadSavedGame() throws IOException {
    	
    	mapFileName="savedMap.txt";
    	mapFileName="1.map";
		this.map = new RiskMap();
		this.map.loadMap(mapFileName.trim());
		this.map.writeTheMapToTheTextFile();
    	ArrayList<Player> players=new ArrayList<Player>();
		ArrayList<String> countries=new ArrayList<String>();
		countries.add("c1");countries.add("c2");
		ArrayList<Card> cards= new ArrayList<Card>();
		cards.add(new Card("Infantry",1));
		players.add(new Player("Arya", 10, countries , cards, 1, 0, 0));
		countries=new ArrayList<String>();
		countries.add("c3");countries.add("c4");
		cards= new ArrayList<Card>();
		cards.add(new Card("Cavalry",2));
		players.add(new Player("Jon", 20, countries , cards, 0, 1, 0));
		countries=new ArrayList<String>();
		countries.add("c5");countries.add("c6");
		cards= new ArrayList<Card>();
		cards.add(new Card("Cannon",3));
		players.add(new Player("Sansa", 50, countries , cards, 0, 0, 1));
		GamePlay play=(new GamePlay(players,this.map));
		play.map=this.map;
    	Player player=(play).retrieveMap();
    	assertTrue((new GamePlay(players,this.map)).saveMap());
    		assertEquals(player.players.get(0).getPlayerName(),"Arya");
    }
}
