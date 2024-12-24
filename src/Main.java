public class Main {
    public static void main(String[] args) {
        BlackJack blackJack = new BlackJack();
    }

/*
* import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class BlackJack{
    private class Card{
        String value;
        String type;

        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }
        public int getValue() {
            if ("AJQK".contains(value)){
                if(value.equals("A")){
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value); //for number 2 to 10
        }

        public boolean isAce(){
            return value.equals("A");
        }
    }

    ArrayList<Card> deck;
    Random random = new Random(); //for the shuffling

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAces;

    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAces;


    //The Board
    int boardWidth = 600;
    int boardHeight = boardWidth;

    JFrame frame = new JFrame("Black Jack");
    BlackJack() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public void startGame(){
        //deck
        buildDeck();
        shuffleDeck();
        //dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAces = 0;

        hiddenCard = deck.remove(deck.size()-1); //removes the last card
        dealerSum = hiddenCard.getValue();
        dealerAces += hiddenCard.isAce() ? 1 : 0; //give 1, otherwise 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum = card.getValue();
        dealerAces += card.isAce() ? 1 : 0;

        System.out.println("DEALER: ");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAces);

        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAces = 0;

        for(int i = 0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAces += card.isAce() ? 1 : 0;
            playerHand.add(card);

            System.out.println("PLAYER: ");
            System.out.println(playerHand);
            System.out.println(playerSum);
            System.out.println(playerAces);
        }
    }
    public void buildDeck(){
        new ArrayList<Card>();
        String[] values = {"A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};
        for (String type : types) {
            for (String value : values) {
                Card card = new Card(value, type);
                deck.add(card);
            }
        }

    }

    public void shuffleDeck(){
        for(int i=0; i<deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card currentCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currentCard);
        }

    }
}*/
}