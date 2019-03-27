package view;

import model.Player;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class CardExchange implements Observer {

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    private Player player;

    public void update(Observable obs, Object x) {
        System.out.println("\n**Card Exchange**\n");
        Player player=((Player) obs).getCurrentPlayer();
        setArmiesForCards(player);

    }

    private void setArmiesForCards(Player player) {

        this.player=player;

        (new Player()).displayPlayerCards(this.player);

        int numberOfPlayerCards = player.getInfantryCount()+player.getCavalryCount()+player.getCannonCount();

        if (numberOfPlayerCards >= 5) {
            boolean validCards=true;
            while(numberOfPlayerCards>=5 && validCards){
                validCards=exchangeCards();
                numberOfPlayerCards= player.getInfantryCount()+player.getCavalryCount()+player.getCannonCount();
            }
        } else if (numberOfPlayerCards >= 3) {
            System.out.println("Do you want to exchange 3 armies for 5 armies?Yes/No");
            boolean exchangeCards=false;
            while(!exchangeCards) {
                String exchangeCardsChoice = scanner.next();
                if (exchangeCardsChoice.equals("Yes")) {
                    exchangeCards=true;
                    exchangeCards();
                } else if (exchangeCardsChoice.equals("No")) {
                    exchangeCards=true;
                    System.out.println("Exiting Card Exchange view...");
                } else {
                    System.out.println("Invalid! Enter either Yes or No: ");
                }
            }
        } else {
            System.out.println("You don't have enough cards to exchange! :(");
        }


    }

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
            System.out.println("3 Cavalry Cards are exchanged for 5 reinforcement armies!");
            cavalryCount-=3;
            playerArmies+=5;
        }else if(cavalryCount>=3){
            System.out.println("3 Cannon Cards are exchanged for 5 reinforcement armies!");
            cannonCount-=3;
            playerArmies+=5;
        }else if(infantryCount>=1 && cannonCount>=1 && cavalryCount>=1){
            System.out.println("3 Cards- one each of Infantry, Cavalry, Cannon Cards are exchanged for 5 reinforcement armies!");
            infantryCount-=1;
            cavalryCount-=1;
            cannonCount-=1;
            playerArmies+=5;
        }
        this.player.setInfantryCount(infantryCount);
        this.player.setCavalryCount(cavalryCount);
        this.player.setCannonCount(cannonCount);
        this.player.setArmies(playerArmies);
        return true;
    }

}

