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

    private Player player;

    public void update(Observable obs, Object x) {
        System.out.println("\n**Card Exchange**\n");
        Player player=((Player) obs).getCurrentPlayer();
        setArmiesForCards(player);

    }

    private void setArmiesForCards(Player player) {

        this.player=player;

        (new Player()).displayPlayerCards(this.player.getCards(), this.player.getPlayerName());

        int numberOfPlayerCards = this.player.getCards().size();

        if (numberOfPlayerCards >= 5) {
            boolean validCards=true;
            while(numberOfPlayerCards>=5 && validCards){
                validCards=exchangeCards();
                numberOfPlayerCards=this.player.getCards().size();
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
        ArrayList<Card> playerCards=this.player.getCards();
        int playerArmies=this.player.getArmies();

        ArrayList<Integer> sameCardIndices=new ArrayList<>();
        ArrayList<Integer> differentCardIndices=new ArrayList<>();

        int sameCardCount=0, differentCardCount=0;

        for(int i=0;i<playerCards.size()-1;i++){
            sameCardCount=0;
            differentCardCount=0;
            sameCardIndices.clear();
            sameCardIndices.add(i);
            differentCardIndices.clear();
            differentCardIndices.add(i);
            for(int j=i;j<playerCards.size();j++){
                if(playerCards.get(i).getTypeNumber()==playerCards.get(j).getTypeNumber()){
                    sameCardCount++;
                    sameCardIndices.add(j);
                }
                if(!differentCardIndices.contains(playerCards.get(j).getTypeNumber())){
                    differentCardCount++;
                    differentCardIndices.add(j);
                }
                if(sameCardCount==2 || differentCardCount==2){
                    break;
                }
            }
        }
        if(sameCardCount==2){
            System.out.print("You have 3 cards of same type! Removing the following cards for 5 reinforcement armies..");
            for(int sameCardIndex:sameCardIndices){
                System.out.print(playerCards.get(sameCardIndex)+"|");
            }
            for(int x = sameCardIndices.size() - 1; x > 0; x--)
            {
                playerCards.remove(x);
            }
            System.out.println("For the 3 cards exchanged you receive 5 armies! :)");
            playerArmies+=5;
        }else if(differentCardCount==2){
            System.out.print("You have 1 card each of different type! Removing the following cards for 5 reinforcement armies..");
            for(int differentCardIndex:differentCardIndices){
                System.out.print(playerCards.get(differentCardIndex)+"|");
            }
            for(int x = differentCardIndices.size() - 1; x > 0; x--)
            {
                playerCards.remove(x);
            }
            System.out.println("For the 3 cards exchanged you receive 5 armies! :)");
            playerArmies+=5;
        }else{
            System.out.println("Sorry! You don't have 3 cards of same type or 1 card each of different type.");
            return false;
        }
        this.player.setCards(playerCards);
        this.player.setArmies(playerArmies);
        return true;
    }

}

