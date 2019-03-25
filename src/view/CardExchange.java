package view;

import model.Card;
import model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class CardExchange implements Observer {

    /**
     * A Scanner instance to read and parse various primitive values.
     */
    private static Scanner scanner = new Scanner(System.in);

    public void update(Observable obs, Object x) {
        System.out.println("\n**Card Exchange**\n");
        Player player=((Player) obs).getCurrentPlayer();
        setArmiesForCards(player,player.getCards(),player.getArmies());

    }

    private void setArmiesForCards(Player player, ArrayList<Card> playerCards, int playerArmies) {
        (new Player()).displayPlayerCards(playerCards);
        int numberOfPlayerCards = playerCards.size();
        if (numberOfPlayerCards >= 5) {
            while(numberOfPlayerCards>=5){
                System.out.println("You have more than 5 cards. You have to exchange 3 cards for 5 armies!");
                for(int i=0;i<3;i++){
                    System.out.println("Removing card- "+playerCards.get(numberOfPlayerCards-1));
                    playerCards.remove(numberOfPlayerCards-1);
                    numberOfPlayerCards--;
                }
                System.out.println("For the 3 cards exchanged you receive 5 armies! :)");
                playerArmies+=5;
            }
        } else if (numberOfPlayerCards >= 3) {
            System.out.println("Do you want to exchange 3 armies for 5 armies?Yes/No");
            boolean exchangeCards=false;
            while(!exchangeCards) {
                String exchangeCardsChoice = scanner.next();
                if (exchangeCardsChoice.equals("Yes")) {
                    exchangeCards=true;
                    for(int i=0;i<3;i++){
                        System.out.println("Removing card- "+playerCards.get(numberOfPlayerCards-1).getName());
                        playerCards.remove(numberOfPlayerCards-1);
                        numberOfPlayerCards--;
                    }
                    System.out.println("For the 3 cards exchanged you receive 5 armies! :)");
                    playerArmies+=5;
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
        player.setCards(playerCards);
        player.setArmies(playerArmies);

    }

}

