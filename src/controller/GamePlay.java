package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Scanner;

import model.Card;
import model.Country;
import model.Player;
import model.RiskMap;
import view.CardExchange;
import view.Phase;


/**
 * GamePlay Class represents a game
 *
 * @author Prashanthi
 * @author Suthakhar
 * @version 1.1
 * @since 1.0
 */
public class GamePlay extends Observable {

    /**
     * Stores the value of the each phase in the game
     */
    private int phase;

    /**
     * Stores the name of the current player who is playing
     */
    private String currentPlayer;

    /**
     * Stores the value of the % of map the player controls at any particular time in the game
     */
    private float percentageMap;

    /**
     * Stores a list of continents the player controls at any particular time in the game
     */
    private ArrayList continentsControlled;

    /**
     * Stores the total number of armies that the player has to be placed in countries
     */
    private int totalArmies;

    /**
     * getter method to return the percentage of map controlled by player
     *
     * @return percentage of map controlled by player
     */
    public float getPercentageMap() {
        return percentageMap;
    }

    /**
     * setter method to set the value of percentage of map controlled by the player
     *
     * @param percentageMap decimal value of percentage of map controlled by player
     */
    public void setPercentageMap(float percentageMap) {
        this.percentageMap = percentageMap;
    }

    /**
     * getter method to get the list of continents controlled by the player
     *
     * @return list of continents controlled by the player
     */
    public ArrayList getContinentsControlled() {
        return continentsControlled;
    }

    /**
     * setter method to set the list of continents controlled by the player
     *
     * @param continentsControlled list of continents controlled by the player
     */
    public void setContinentsControlled(ArrayList continentsControlled) {
        this.continentsControlled = continentsControlled;
    }

    /**
     * getter method to return the total number of armies owned by the player
     *
     * @return total number of armies a player owns
     */
    public int getTotalArmies() {
        return totalArmies;
    }

    /**
     * setter method to assign the total number of armies owned by a player
     *
     * @param totalArmies Integer value of armies owned by a player
     */
    public void setTotalArmies(int totalArmies) {
        this.totalArmies = totalArmies;
    }

    /**
     * getter method to return the value of the phase of the game
     *
     * @return phase value
     */
    public int getPhase() {
        return phase;
    }

    /**
     * setter method to set the value of the phase
     *
     * @param phase value of phase of game
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * getter method to get the name of the current player
     *
     * @return name of the player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * setter method to set the name of the current player
     *
     * @param currentPlayer name of the player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Array list that holds instances of all players
     */
    private ArrayList<Player> players;

    /**
     * Instance of player
     */
    private Player play=new Player();

    /**
     * Instance of RiskMap
     */
    public RiskMap map = new RiskMap();

    /**
     * Create a game play
     */
    public GamePlay() {

    }

    /**
     * Creates a game play with specified value of players and map
     *
     * @param players list of all players in the game
     * @param map     instance of RiskMap
     */
    public GamePlay(ArrayList<Player> players, RiskMap map) {

        play = new Player(players, map);
    }

    /**
     * Driver method to initiate the game phases in a round robin fashion for every player
     * @param gamePlay instance of game
     * @throws IOException 
     */
    public void start(GamePlay gamePlay) throws IOException {
        System.out.println("\n**** Game has started ****");
        
        currentPlayer = null;
        Phase phaseView = new Phase();
        boolean initialGameFlag = true;
        gamePlay.deleteObserver(phaseView);
        gamePlay.addObserver(phaseView);
        for (Player player : play.players) {
            phase = 1;
            setPlayerDetailsForPhase(player);
            play.deployArmies(player, play.map);
        }
        boolean gameOver = false;
        while (!gameOver) {

            for (Player player : play.players) {
            	
                System.out.println("\n**** New Round Begins ****");
                
                System.out.println("Enter Yes if u Want to save the Map At this Moment");
                
                String temp1= scanner.next();
                if(temp1.equals("Yes"))
                {
                saveMap();
                }
//                
//                System.out.println("Select Yes if u want to Load the Map from Last Saved Map");
//                String temp2= scanner.next();
//                if(temp2.equals("Yes"))
//                {
//                 retrieveMap();
//                }
                
                /*
                 * Reinforcement Phase
                 */
                if (!initialGameFlag) {
                    gamePlay.deleteObserver(phaseView);
                    gamePlay.addObserver(phaseView);

                    phase = 2;
                    CardExchange cardExchange = new CardExchange();
                    play.addObserver(cardExchange);
                    setPlayerDetailsForPhase(player);
                    play.reinforcement(player, play.map);        //send play.map for map object
                    play.deleteObserver(cardExchange);


                }
                initialGameFlag = false;
                /*
                 * Attack Phase
                 */
                gamePlay.deleteObserver(phaseView);
                gamePlay.addObserver(phaseView);

                phase = 3;

                setPlayerDetailsForPhase(player);
                play.attack(player, play.map); //after leaving behind, country should belong to winner
                System.out.println("Enter Yes if u Want to save the Map At this Moment");
                
                String temp2= scanner.next();
                if(temp2.equals("Yes"))
                {
                saveMap();
                }

                /*
                 * Fortification Phase
                 */
                gamePlay.deleteObserver(phaseView);
                gamePlay.addObserver(phaseView);

                phase = 4;
                setPlayerDetailsForPhase(player);
                play.fortification(player, play.map);
                 
                System.out.println("Enter Yes if u Want to save the Map At this Moment");
                
                String temp3= scanner.next();
                if(temp3.equals("Yes"))
                {
                saveMap();
                }
                
            }
            System.out.println("\nContinue the game?\nYes\nNo");
            boolean continueGameFlag = false;
            while (!continueGameFlag) {
                String choice = scanner.next();
                if (choice.equals("Yes")) {
                    continueGameFlag = true;
                } else if (choice.equals("No")) {
                    continueGameFlag = true;
                    gameOver = true;
                } else {
                    System.out.println("Invalid choice! Enter either Yes or No: ");
                    scanner.next();
                }
            }
        }
        System.out.println("\n***Game Over***\n");
    }
    
    public void retrieveMap() throws IOException
    {
//    	 play.map.continents.clear();
//    	 play.map.continentsWithCountries.clear();
//    	 play.map.adjCountries.clear();
//    	 play.map. continentsWithCountriesAndNeighbours.clear();
//    	 play.map.countries.clear();
//    	 play.players.clear();
    	 
    	 
    	 
    	 FileReader fir = new FileReader("savedMap.txt");
         BufferedReader bir = new BufferedReader(fir);
         while (!bir.readLine().trim().equals("[Continents]")) {

         }
         while (true) {
             String temp = bir.readLine();
             if (temp.trim().length() == 0) {
                 break;
             } else {
                 String[] tempArr = temp.split("=");
                 play.map.continents.put(tempArr[0], Integer.parseInt(tempArr[1]));
             }
         }
         while (!bir.readLine().trim().equals("[Territories]")) {

         }
         
         
         while (true) {
        	 
        	 String line = bir.readLine();
        	 
        	 if(line.trim().length() == 0)
        		 break;
        	 else {
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
                 play.map.adjCountries.put(country, tempAl);
                 countriesWithNeighbours.put(country,tempAl);
                 saveContinentWithCountriesAndNeighbours(continent,country,tempAl);
             }
             
        	 }
         }
         
         System.out.println("before countires");
         
         while (!bir.readLine().trim().equals("[Countries]")) {
        	 
         }
         
         while(true)
         {
             String line = bir.readLine();
        	 
        	 if(line.trim().length() == 0)
        		 break;
        	 
        	 else
        	 {
        		 String[] temp = line.split(",");
        		 String pName=temp[0];
        		 String belongs=temp[1];
        		 int armies=Integer.parseInt(temp[2]);
        		 play.map.countries.add(new Country(pName,belongs,armies));
        	 }
         }
         
         
         
         while (!bir.readLine().trim().equals("[Player]")) {
        	 
         }
         
         while(true)
         {
             String line = bir.readLine();
        	 
        	 if(line.trim().length() == 0)
        		 break;
        	 
        	 else
        	 {
        		 String[] temp = line.split(",");
        		 String pName=temp[0];
        		 int armies=Integer.parseInt(temp[1]);
        		 int CannonCount=Integer.parseInt(temp[2]);
        		 int CavalryCount=Integer.parseInt(temp[3]);
        		 int InfantryCount=Integer.parseInt(temp[4]);
        		 play.players.add(new Player(pName,armies,null, null,CannonCount,CavalryCount,InfantryCount ));
        	 }
         }
         
         System.out.println("before player countries");
        while (!bir.readLine().trim().equals("[PlayerCountries]")) {
        	 
         }
         
        while(true)
        {
            String line = bir.readLine();
       	 
       	 if(line.trim().length() == 0)
       		 break;
       	 
       	 else
       	 {
       		 
       		 ArrayList<String> al=new  ArrayList<String>();
       		 String[] temp = line.split(",");
       		 
       		 String playerName=temp[0];
       		 for(int i=1;i<temp.length;i++)
       		 {
       			 al.add(temp[i]);
       		 }
       		 
       		 for(int i=0;i<play.players.size();i++)
       		 {
       			 if(play.players.get(i).getPlayerName().equals(playerName))
       			 {
       				play.players.get(i).setCountries(al);
       			 }
       		 }
       	 }
        }
        
        System.out.println("before player cards");
        while (!bir.readLine().trim().equals("[PlayerCards]")) {
       	 
        }
        
        String line = bir.readLine();
        
        System.out.println(line);
        while(line!=null)
        {
        	System.out.println("inside while");
            
       	 
             String[] temp = line.split(","); 
              
             
             
             if(temp.length>1)
             {
            	 String cardName=temp[1];
            	 int typeNumber=Integer.parseInt(temp[2]);
            	 
            	 Card card=new Card(cardName,typeNumber);
            	 for(int i=0;i<play.players.size();i++)
           		 {
           			 if(play.players.get(i).getPlayerName().equals(temp[0]))
           			 {
           				if(play.players.get(i).getCards()==null)
           				{
           					ArrayList<Card> cd=new ArrayList<Card>();
           					cd.add(card);
           					play.players.get(i).setCards(cd);
           				}
           				else
           				{
           					ArrayList<Card> cd=new ArrayList<Card>();
           					cd=play.players.get(i).getCards();
           					cd.add(card);
           					play.players.get(i).setCards(cd);
           				}
           			 }
           		 }
             }
             line = bir.readLine();  
        }
        
        
    	//System.out.println(play.map.countries.size());
    }
    
    private void saveContinentWithCountries(String continent, String country) {
        ArrayList<String> countries = new ArrayList<>();
        if (play.map.continentsWithCountriesAndNeighbours.containsKey(continent)) {
            countries = play.map.continentsWithCountries.get(continent);
            countries.add(country);
            play.map.continentsWithCountries.put(continent, countries);
        } else {
            countries.add(country);
            play.map.continentsWithCountries.put(continent, countries);
        }
    }

    private void saveContinentWithCountriesAndNeighbours(String continent, String country, ArrayList<String> countryNeighbours) {
        LinkedHashMap<String, ArrayList<String>> countriesAndNeighbours = new LinkedHashMap<>();
        if (play.map.continentsWithCountriesAndNeighbours.containsKey(continent)) {
            countriesAndNeighbours = play.map.continentsWithCountriesAndNeighbours.get(continent);
            countriesAndNeighbours.put(country,countryNeighbours);
            play.map. continentsWithCountriesAndNeighbours.put(continent, countriesAndNeighbours);
        } else {
            countriesAndNeighbours.put(country,countryNeighbours);
            play.map.continentsWithCountriesAndNeighbours.put(continent, countriesAndNeighbours);
        }
    }
    
    public void  saveMap() throws IOException
    {
    	FileWriter fw = new FileWriter("savedMap.txt");
        PrintWriter pw = new PrintWriter(fw);

        pw.println("[Continents]");
        for (String continent : play.map.getContinents().keySet()) {
            pw.write(continent + "=" + play.map.getContinents().get(continent));
            pw.println();
        }
        pw.println();
        pw.println("[Territories]");
        for (String country : play.map.getAdjCountries().keySet()) {
            pw.write(country + "," + "0,0,");
            for (int i = 0; i < play.map.getAdjCountries().get(country).size(); i++) {
                pw.write(play.map.getAdjCountries().get(country).get(i) + ",");
            }
            pw.println();
        }
       
        pw.println();
        pw.println("[Countries]");
        
        for(int i=0;i<play.map.getCountries().size();i++)
        {
        	pw.write(play.map.getCountries().get(i).getCountryName()+","+
        			play.map.getCountries().get(i).getBelongsTo()+","+
        			play.map.getCountries().get(i).getArmies());
        	pw.println();
        }
        pw.println();
        pw.println("[Player]");
        
        for(int i=0;i<play.players.size();i++)
        {
        	pw.write(play.players.get(i).getPlayerName()+","+
        			play.players.get(i).getArmies()+","+
        			play.players.get(i).getCannonCount()+","+
        			play.players.get(i).getCavalryCount()+","+
        			play.players.get(i).getInfantryCount()+","
        			);
        	pw.println();	
        }
        pw.println();
        pw.println("[PlayerCountries]");
        for(int i=0;i<play.players.size();i++)
        {
        	pw.write(play.players.get(i).getPlayerName()+",");
        	for(int j=0;j<play.players.get(i).getCountries().size();j++)
        		{
        		pw.write(
        			play.players.get(i).getCountries().get(j)+","
        			);
        		}
        	pw.println();	
        }
        
      
        pw.println();  
        pw.println("[PlayerCards]");
        
        for(int i=0;i<play.players.size();i++)
        {
        
        for(int j=0;j<play.players.get(i).getCards().size();j++)
        {
        	pw.write(play.players.get(i).getPlayerName()+",");
        	pw.write(play.players.get(i).getCards().get(j).getName()+","+
        			play.players.get(i).getCards().get(j).getTypeNumber()
        			);
        	pw.println();
        }
        	
        }
        
        
        fw.close();
        pw.close();
    	
    }

    /**
     * Refactored method- to set the player details in every phase
     * @param player instance of the current player
     */
    private void setPlayerDetailsForPhase(Player player) {
        currentPlayer = player.getPlayerName();
        percentageMap = (float) play.map.noOfCountriesPlayerOwns(player.getPlayerName()) / play.map.adjCountries.size();
        totalArmies = player.getArmies();
        continentsControlled = play.map.continentsControlledByPlayer(player.getCountries());
        setChanged();
        notifyObservers(this);
    }


}
