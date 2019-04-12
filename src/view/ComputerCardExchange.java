package view;

import model.Player;
import player.Human;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


/**
 * TODO automate everything
 * CardExchange represents the observer pattern of card exchange during reinforcement phase 
 * computer players
 *
 * @author Prashanthi
 * @author Mutesham
 * @version 1.2
 * @since 1.1
 */
public class ComputerCardExchange implements Observer {


    /**
     * Stores an instance of the player
     */
    private Player player;

    
    
    public ComputerCardExchange(Player player) {
		super();
		this.player = player;
	}

	/**
     * This method is called every time the observed instance player changes in reinforcement phase
     * @param observable An instance of Observable class
     * @param object An instance of Object class used in this method
     */
    public void update(Observable observable, Object object) {
        System.out.println("\n**Card Exchange**\n"); 
        setArmiesForCards();

    }

    /**
     * This method assigns armies to player depending on the number and type of cards the player owns
     * @param player The instance of the current player
     */
    private void setArmiesForCards() {
        (new Player()).displayPlayerCards(this.player);
        int numberOfPlayerCards = player.getInfantryCount()+player.getCavalryCount()+player.getCannonCount();
        if (numberOfPlayerCards >= 5) {
            boolean validCards=true;
            while(numberOfPlayerCards>=5 && validCards){
                validCards=exchangeCards();
                numberOfPlayerCards= player.getInfantryCount()+player.getCavalryCount()+player.getCannonCount();
            }
        } 
        if (numberOfPlayerCards >= 3) {
            exchangeCards();
        } else {
            System.out.println("You don't have enough cards to exchange! :(");
        }
    }

    /**
     * The method is used to exchange the cards owned by player for reinforcement armies
     * @return true if the player owns 3 cards of same type or 1 card of each type to be exchanged for 5 armies, else false
     */
    private boolean exchangeCards(){
        int infantryCount=player.getInfantryCount();
        int cavalryCount=player.getCavalryCount();
        int cannonCount=player.getCannonCount();
        int playerArmies=player.getArmies();
        if(infantryCount>=3){
           System.out.println("3 Infantry Cards are exchanged for 5 reinforcement armies!");
           infantryCount-=3;
           playerArmies+=5;
        }else if(cannonCount>=3){
            System.out.println("3 Cannon Cards are exchanged for 5 reinforcement armies!");
            cavalryCount-=3;
            playerArmies+=5;
        }else if(cavalryCount>=3){
            System.out.println("3 Cavalry Cards are exchanged for 5 reinforcement armies!");
            cannonCount-=3;
            playerArmies+=5;
        }else if(infantryCount>=1 && cannonCount>=1 && cavalryCount>=1){
            System.out.println("3 Cards- one each of Infantry, Cavalry, Cannon Cards are exchanged for 5 reinforcement armies!");
            infantryCount-=1;
            cavalryCount-=1;
            cannonCount-=1;
            playerArmies+=5;
        }else{
            System.out.println("Player does not have valid cards for exchange!");
            return false;
        }
        this.player.setInfantryCount(infantryCount);
        this.player.setCavalryCount(cavalryCount);
        this.player.setCannonCount(cannonCount);
        this.player.setArmies(playerArmies);
        return true;
    }

}

